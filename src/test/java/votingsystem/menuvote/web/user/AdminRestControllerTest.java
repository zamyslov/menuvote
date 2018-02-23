package votingsystem.menuvote.web.user;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import votingsystem.menuvote.TestUtil;
import votingsystem.menuvote.model.Role;
import votingsystem.menuvote.model.User;
import votingsystem.menuvote.util.exception.ErrorType;
import votingsystem.menuvote.web.AbstractControllerTest;
import web.json.JsonUtil;
import web.user.AdminRestController;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static votingsystem.menuvote.TestUtil.userHttpBasic;
import static votingsystem.menuvote.service.UserTestData.ADMIN;
import static votingsystem.menuvote.service.UserTestData.ADMIN_ID;
import static votingsystem.menuvote.service.UserTestData.*;
import static web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_EMAIL;


public class AdminRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + ADMIN_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ADMIN));
    }

//    @Test
//    public void testGetNotFound() throws Exception {
//        mockMvc.perform(get(REST_URL + 1)
//                .with(userHttpBasic(ADMIN)))
//                .andExpect(status().isUnprocessableEntity())
//                .andDo(print());
//    }
//
//    @Test
//    public void testGetByEmail() throws Exception {
//        mockMvc.perform(get(REST_URL + "by?email=" + ADMIN.getEmail())
//                .with(userHttpBasic(ADMIN)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(contentJson(ADMIN));
//    }
//
//    @Test
//    public void testDelete() throws Exception {
//        mockMvc.perform(delete(REST_URL + USER_ID)
//                .with(userHttpBasic(ADMIN)))
//                .andDo(print())
//                .andExpect(status().isNoContent());
//        assertMatch(userService.getAll(), ADMIN);
//    }
//
//    @Test
//    public void testDeleteNotFound() throws Exception {
//        mockMvc.perform(delete(REST_URL + 1)
//                .with(userHttpBasic(ADMIN)))
//                .andExpect(status().isUnprocessableEntity())
//                .andDo(print());
//    }
//
//    @Test
//    public void testGetUnauth() throws Exception {
//        mockMvc.perform(get(REST_URL))
//                .andExpect(status().isUnauthorized());
//    }
//
//    @Test
//    public void testGetForbidden() throws Exception {
//        mockMvc.perform(get(REST_URL)
//                .with(userHttpBasic(USER)))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    public void testUpdate() throws Exception {
//        User updated = new User(USER);
//        updated.setName("UpdatedName");
//        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
//        mockMvc.perform(put(REST_URL + USER_ID)
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(userHttpBasic(ADMIN))
//                .content(jsonWithPassword(updated, USER.getPassword())))
//                .andExpect(status().isOk());
//
//        assertMatch(userService.get(USER_ID), updated);
//    }
//
//    @Test
//    public void testCreate() throws Exception {
//        User expected = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
//        ResultActions action = mockMvc.perform(post(REST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(userHttpBasic(ADMIN))
//                .content(jsonWithPassword(expected, "newPass")))
//                .andExpect(status().isCreated());
//
//        User returned = TestUtil.readFromJson(action, User.class);
//        expected.setId(returned.getId());
//
//        assertMatch(returned, expected);
//        assertMatch(userService.getAll(), ADMIN, expected, USER);
//    }
//
//    @Test
//    public void testGetAll() throws Exception {
//        TestUtil.print(mockMvc.perform(get(REST_URL)
//                .with(userHttpBasic(ADMIN)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(contentJson(ADMIN, USER)));
//    }
//
//    @Test
//    public void testCreateInvalid() throws Exception {
//        User expected = new User(null, null, "", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
//        ResultActions action = mockMvc.perform(post(REST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(userHttpBasic(ADMIN))
//                .content(JsonUtil.writeValue(expected)))
//                .andExpect(status().isUnprocessableEntity())
//                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
//                .andDo(print());
//    }
//
//    @Test
//    public void testUpdateInvalid() throws Exception {
//        User updated = new User(USER);
//        updated.setName("");
//        mockMvc.perform(put(REST_URL + USER_ID)
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(userHttpBasic(ADMIN))
//                .content(JsonUtil.writeValue(updated)))
//                .andExpect(status().isUnprocessableEntity())
//                .andDo(print())
//                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
//                .andDo(print());
//    }
//
//    @Test
//    @Transactional(propagation = Propagation.NEVER)
//    public void testUpdateDuplicate() throws Exception {
//        User updated = new User(USER);
//        updated.setEmail("admin@gmail.com");
//        mockMvc.perform(put(REST_URL + USER_ID)
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(userHttpBasic(ADMIN))
//                .content(jsonWithPassword(updated, "password")))
//                .andExpect(status().isConflict())
//                .andExpect(errorType(ErrorType.DATA_ERROR))
//                .andExpect(jsonMessage("$.details", EXCEPTION_DUPLICATE_EMAIL))
//                .andDo(print());
//    }
//
//    @Test
//    @Transactional(propagation = Propagation.NEVER)
//    public void testCreateDuplicate() throws Exception {
//        User expected = new User(null, "New", "user@yandex.ru", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
//        mockMvc.perform(post(REST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(userHttpBasic(ADMIN))
//                .content(jsonWithPassword(expected, "newPass")))
//                .andExpect(status().isConflict())
//                .andExpect(errorType(ErrorType.DATA_ERROR))
//                .andExpect(jsonMessage("$.details", EXCEPTION_DUPLICATE_EMAIL));
//
//    }
}