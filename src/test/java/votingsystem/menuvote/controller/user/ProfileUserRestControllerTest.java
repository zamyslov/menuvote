package votingsystem.menuvote.controller.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import votingsystem.menuvote.model.Role;
import votingsystem.menuvote.model.User;
import votingsystem.menuvote.util.exception.ErrorType;
import votingsystem.menuvote.controller.AbstractControllerTest;
import votingsystem.menuvote.util.json.JsonUtil;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static votingsystem.menuvote.TestUtil.userHttpBasic;
import static votingsystem.menuvote.testdata.UserTestData.*;
import static votingsystem.menuvote.util.ExceptionInfoHandler.EXCEPTION_DUPLICATE_EMAIL;
import static votingsystem.menuvote.controller.user.ProfileRestController.REST_URL;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProfileUserRestControllerTest extends AbstractControllerTest {

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER_AUTH)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andDo(print());
                .andExpect(contentJson(USER));
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL)
                .with(userHttpBasic(USER_AUTH)))
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), ADMIN, USER1, USER2, USER3);
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER);
        updated.setEmail("newemail@ya.ru");
        updated.setRoles(Collections.emptySet());
        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER_AUTH))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(userService.getByEmail("newemail@ya.ru"), updated);
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        User updated = new User(null, null, "password", null, Role.ROLE_USER);
        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER_AUTH))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testDuplicate() throws Exception {
        User updated = new User(null, "newName", "admin@gmail.com", "newPassword", Role.ROLE_USER);
        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER_AUTH))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isConflict())
                .andExpect(errorType(ErrorType.DATA_ERROR))
                .andExpect(jsonMessage("$.details", EXCEPTION_DUPLICATE_EMAIL));
    }
}