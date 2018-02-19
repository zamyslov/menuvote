package votingsystem.menuvote.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import votingsystem.menuvote.model.Vote;
import votingsystem.menuvote.service.VoteService;
import votingsystem.menuvote.util.exception.ClosedPeriodException;
import votingsystem.menuvote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static votingsystem.menuvote.service.MenuTestData.MENU1;
import static votingsystem.menuvote.service.MenuTestData.MENU2;
import static votingsystem.menuvote.service.UserTestData.USER;
import static votingsystem.menuvote.service.UserTestData.USER3;
import static votingsystem.menuvote.service.VoteTestData.*;
import static votingsystem.menuvote.util.VoteUtil.checkVoteForTime;
import static votingsystem.menuvote.util.VoteUtil.setMaxTimeForVote;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    protected VoteService service;

    @Test
    public void create() {
        Vote newVote = new Vote(null, USER3, MENU1, LocalDate.of(2017, 12, 20));
        setMaxTimeForVote(LocalTime.now().plusMinutes(1));
        Vote created = service.create(newVote);
        newVote.setId(created.getId());
        assertMatch(service.getAll(), VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, VOTE6, newVote);
    }

    @Test(expected = ClosedPeriodException.class)
    public void createAfterMaxTime() {
        setMaxTimeForVote(LocalTime.now().minusMinutes(1));
        Vote newVote = new Vote(null, USER3, MENU1, LocalDate.of(2017, 12, 20));
        service.create(newVote);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateVoteCreate() {
        setMaxTimeForVote(LocalTime.now().plusMinutes(1));
        service.create(new Vote(null, USER, MENU1, LocalDate.of(2017, 12, 20)));
    }

    @Test
    public void delete() {
        setMaxTimeForVote(LocalTime.now().plusMinutes(1));
        service.delete(VOTE1.getMenu().getDate(), VOTE1.getUser().getId());
        assertMatch(service.getAll(), VOTE2, VOTE3, VOTE4, VOTE5, VOTE6);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() {
        setMaxTimeForVote(LocalTime.now().plusMinutes(1));
        service.delete(VOTE1.getMenu().getDate(), 1);
    }

    @Test(expected = ClosedPeriodException.class)
    public void deleteAfterMaxTime() {
        setMaxTimeForVote(LocalTime.now().minusMinutes(1));
        service.delete(VOTE1.getMenu().getDate(), 1);
    }

    @Test
    public void update() {
        Vote updated = VOTE1;
        updated.setMenu(MENU2);
        setMaxTimeForVote(LocalTime.now().plusMinutes(1));
        service.update(updated);
        assertMatch(service.getAll(), VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, VOTE6);
    }

    @Test(expected = ClosedPeriodException.class)
    public void updateAfterMaxTime() {
        setMaxTimeForVote(LocalTime.now().minusMinutes(1));
        Vote updated = VOTE1;
        service.update(updated);
    }

    @Test(expected = ClosedPeriodException.class)
    public void checkVoteForTimeWithException() {
        setMaxTimeForVote(LocalTime.now().minusMinutes(1));
        checkVoteForTime(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
    }

    public void checkVoteForTimeCorrect() {
        setMaxTimeForVote(LocalTime.now().plusMinutes(1));
        assertTrue(checkVoteForTime(LocalDateTime.of(LocalDate.now(), LocalTime.now())));
    }

    @Test
    public void getAll() {
        List<Vote> all = service.getAll();
        assertMatch(all, VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, VOTE6);
    }

}