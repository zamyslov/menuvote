package votingsystem.menuvote.web.menu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import votingsystem.menuvote.TestUtil;
import votingsystem.menuvote.model.Menu;
import votingsystem.menuvote.model.MenuDishes;
import votingsystem.menuvote.web.AbstractControllerTest;
import votingsystem.menuvote.web.json.JsonUtil;

import java.time.LocalDate;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static votingsystem.menuvote.TestUtil.contentJsonArray;
import static votingsystem.menuvote.TestUtil.userHttpBasic;
import static votingsystem.menuvote.service.MenuDishesTestData.*;
import static votingsystem.menuvote.service.MenuTestData.*;
import static votingsystem.menuvote.service.MenuTestData.assertMatch;
import static votingsystem.menuvote.service.RestaurantTestData.RES1;
import static votingsystem.menuvote.service.RestaurantTestData.RES2;
import static votingsystem.menuvote.service.UserTestData.ADMIN_AUTH;
import static votingsystem.menuvote.service.UserTestData.USER_AUTH;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminMenuRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = AdminMenuRestController.REST_URL + '/';

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN_AUTH))
                .content(JsonUtil.writeValue(MENU1_ID)))
                .andExpect(status().isNoContent());
        assertMatch(menuService.getAll(), MENU2, MENU3, MENU4);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN_AUTH))
                .content(JsonUtil.writeValue(MENU1_ID + 10)))
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
        Menu updated = new Menu(MENU1);
        updated.setDate(LocalDate.of(2017, 2, 20));
        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN_AUTH))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(menuService.get(MENU1_ID), updated);
    }

    @Test
    public void testCreate() throws Exception {
        Menu expected = new Menu(null, RES1, LocalDate.of(2017, 2, 20));
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN_AUTH))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Menu returned = TestUtil.readFromJson(action, Menu.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(menuService.getAll(), MENU1, MENU2, MENU3, MENU4, expected);
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN_AUTH)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonArray(MENU1, MENU2, MENU3, MENU4)));
    }

    @Test
    public void testCreateInvalid() throws Exception {
        Menu expected = new Menu(null, null, null);
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN_AUTH))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        Menu updated = new Menu(MENU1);
        updated.setRestaurant(null);
        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN_AUTH))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateDuplicate() throws Exception {
        Menu updated = new Menu(MENU1);
        updated.setRestaurant(RES2);
        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN_AUTH))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testCreateDuplicate() throws Exception {
        Menu expected = new Menu(MENU1);
        expected.setRestaurant(RES2);
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN_AUTH))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testAddDish() throws Exception {
        Menu expected = new Menu(MENU1);
        HashSet<MenuDishes> dishesForMenu1 = new HashSet<>();
        dishesForMenu1.add(MENUDISH1);
        dishesForMenu1.add(MENUDISH2);
        dishesForMenu1.add(MENUDISH3);
        expected.setMenuDishes(dishesForMenu1);

        ResultActions action = mockMvc.perform(post(REST_URL + MENU1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN_AUTH))
                .content(JsonUtil.writeValue(MENUDISH3)))
                .andExpect(status().isCreated());

        Menu returned = TestUtil.readFromJson(action, Menu.class);
        assertMatch(returned, expected);
    }

    @Test
    public void testDeleteDish() throws Exception {
        Menu expected = new Menu(MENU1);
        HashSet<MenuDishes> dishesForMenu1 = new HashSet<>();
        dishesForMenu1.add(MENUDISH1);
        expected.setMenuDishes(dishesForMenu1);
        mockMvc.perform(delete(REST_URL + MENU1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN_AUTH))
                .content(JsonUtil.writeValue(MENUDISH2)))
                .andExpect(status().isNoContent());

        assertMatch(menuService.get(MENU1_ID), expected);
    }
}
