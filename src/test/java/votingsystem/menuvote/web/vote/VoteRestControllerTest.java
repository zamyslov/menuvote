package votingsystem.menuvote.web.vote;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import votingsystem.menuvote.model.Vote;
import votingsystem.menuvote.util.VoteUtil;
import votingsystem.menuvote.web.AbstractControllerTest;
import votingsystem.menuvote.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static votingsystem.menuvote.TestUtil.readFromJson;
import static votingsystem.menuvote.TestUtil.userHttpBasic;
import static votingsystem.menuvote.service.MenuTestData.MENU1;
import static votingsystem.menuvote.service.MenuTestData.MENU2;
import static votingsystem.menuvote.service.UserTestData.USER;
import static votingsystem.menuvote.service.UserTestData.USER_AUTH;
import static votingsystem.menuvote.service.VoteTestData.*;
import static votingsystem.menuvote.web.vote.VoteRestController.REST_URL;

@Transactional
public class VoteRestControllerTest extends AbstractControllerTest {

    @Test
    public void testCreate() throws Exception {
        Vote created = new Vote(null, USER, MENU2, LocalDate.now());
        VoteUtil.setMaxTimeForVote(LocalTime.of(23, 59));
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MENU2))
                .with(userHttpBasic(USER_AUTH)));

        Vote returned = readFromJson(action, Vote.class);
        returned.setUser(USER);
        returned.setMenu(MENU2);
        created.setId(returned.getId());
        VOTE1.setDate(LocalDate.of(2017, 12, 20));

        assertMatch(returned, created);
        assertMatch(voteService.getAll(), VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, VOTE6, created);
    }

    @Test
    public void testCreateInvalidPeriod() throws Exception {
        VoteUtil.setMaxTimeForVote(LocalTime.of(0, 0));
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MENU2))
                .with(userHttpBasic(USER_AUTH)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testDelete() throws Exception {
        VOTE1.setDate(LocalDate.now());
        VoteUtil.setMaxTimeForVote(LocalTime.of(23, 59));
        mockMvc.perform(delete(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MENU1))
                .with(userHttpBasic(USER_AUTH)))
                .andExpect(status().isNoContent());
        assertMatch(voteService.getAll(), VOTE2, VOTE3, VOTE4, VOTE5, VOTE6);
    }

    @Test
    public void testDeleteInvalidPeriod() throws Exception {
        VOTE1.setDate(LocalDate.now());
        VoteUtil.setMaxTimeForVote(LocalTime.of(0, 0));
        mockMvc.perform(delete(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MENU1))
                .with(userHttpBasic(USER_AUTH)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testCreateUnauth() throws Exception {
        VoteUtil.setMaxTimeForVote(LocalTime.of(0, 0));
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MENU2)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDeleteUnauth() throws Exception {
        VOTE1.setDate(LocalDate.now());
        VoteUtil.setMaxTimeForVote(LocalTime.of(0, 0));
        mockMvc.perform(delete(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MENU1)))
                .andExpect(status().isUnauthorized());
    }
}