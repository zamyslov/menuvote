package votingsystem.menuvote.web.dish;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import votingsystem.menuvote.TestUtil;
import votingsystem.menuvote.model.Dish;
import votingsystem.menuvote.web.AbstractControllerTest;
import votingsystem.menuvote.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static votingsystem.menuvote.TestUtil.contentJsonArray;
import static votingsystem.menuvote.TestUtil.userHttpBasic;
import static votingsystem.menuvote.service.DishTestData.*;
import static votingsystem.menuvote.service.UserTestData.ADMIN_AUTH;
import static votingsystem.menuvote.service.UserTestData.USER_AUTH;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminDishRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = AdminDishRestController.REST_URL + '/';

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + DISH1_ID)
                .with(userHttpBasic(ADMIN_AUTH)))
                .andExpect(status().isNoContent());
        assertMatch(dishService.getAll(), DISH2, DISH3);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1)
                .with(userHttpBasic(ADMIN_AUTH)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetForbidden() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER_AUTH)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUpdate() throws Exception {
        Dish updated = new Dish(DISH1);
        updated.setName("UpdatedName");
        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN_AUTH))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(dishService.get(DISH1_ID), updated);
    }

    @Test
    public void testCreate() throws Exception {
        Dish expected = new Dish(null, "New dish");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN_AUTH))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Dish returned = TestUtil.readFromJson(action, Dish.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(dishService.getAll(), DISH1, DISH2, DISH3, expected);
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN_AUTH)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonArray(DISH1, DISH2, DISH3));
    }

    @Test
    public void testCreateInvalid() throws Exception {
        Dish expected = new Dish(null, null);
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN_AUTH))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        Dish updated = new Dish(DISH1);
        updated.setName("");
        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN_AUTH))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateDuplicate() throws Exception {
        Dish updated = new Dish(DISH1);
        updated.setName("Juice");
        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN_AUTH))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testCreateDuplicate() throws Exception {
        Dish expected = new Dish(null, "Juice");
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN_AUTH))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isConflict());

    }
}
