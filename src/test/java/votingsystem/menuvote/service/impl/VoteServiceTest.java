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

import static votingsystem.menuvote.service.MenuTestData.MENU1;
import static votingsystem.menuvote.service.MenuTestData.MENU2;
import static votingsystem.menuvote.service.UserTestData.*;
import static votingsystem.menuvote.service.VoteTestData.*;
import static votingsystem.menuvote.service.VoteTestData.assertMatch;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    protected VoteService service;

    @Test
    public void create() {
        Vote newVote = new Vote(null, USER3, MENU1, LocalDate.of(2017, 12, 20));
        Vote created = service.create(newVote);
        newVote.setId(created.getId());
        assertMatch(service.getAll(), VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, VOTE6, newVote);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateVoteCreate() {
        service.create(new Vote(null, USER, MENU1, LocalDate.of(2017, 12, 20)));
    }

    @Test
    public void delete() {
        service.delete(VOTE1.getMenu().getDate(), VOTE1.getUser().getId());
        assertMatch(service.getAll(), VOTE2, VOTE3, VOTE4, VOTE5, VOTE6);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() {
        service.delete(VOTE1.getMenu().getDate(), 1);
    }

    @Test
    public void update() {
        Vote updated = VOTE1;
        updated.setMenu(MENU2);
        service.update(updated, USER_ID, LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)));
        assertMatch(service.getAll(), VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, VOTE6);
    }

    @Test(expected = ClosedPeriodException.class)
    public void updateAfterDeadline() {
        Vote updated = VOTE1;
        updated.setMenu(MENU2);
        service.update(updated, USER_ID, LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 0)));
    }

    @Test
    public void getAll() {
        List<Vote> all = service.getAll();
        assertMatch(all, VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, VOTE6);
    }

}