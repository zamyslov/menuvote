package votingsystem.menuvote.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import votingsystem.menuvote.model.Vote;
import votingsystem.menuvote.service.VoteService;
import votingsystem.menuvote.util.exception.ClosedPeriodException;
import votingsystem.menuvote.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
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

//    @Autowired
//    private CacheManager cacheManager;

    @Autowired
    protected VoteService service;

//    @Before
//    public void setUpCacheUsers() throws Exception {
//        cacheManager.getCache("users").clear();
//    }

    @Test
    public void create() throws Exception {
        Vote newVote = new Vote(null, USER3, MENU1);
        Vote created = service.create(newVote);
        newVote.setId(created.getId());
        assertMatch(service.getAll(), newVote, VOTE1, VOTE2, VOTE3, VOTE4);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateVoteCreate() throws Exception {
        service.create(new Vote(null, USER, MENU1));
    }

    @Test
    public void delete() throws Exception {
        service.delete(VOTE1.getMenu().getDate(), VOTE1.getUser().getId());
        assertMatch(service.getAll(), VOTE2, VOTE3, VOTE4);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(VOTE1.getMenu().getDate(), 1);
    }


    @Test
    public void update() throws Exception {
        Vote updated = VOTE1;
        updated.setMenu(MENU2);
        service.update(updated, USER_ID, LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 00)));
        assertMatch(service.getAll(), VOTE1, VOTE2, VOTE3, VOTE4);
    }

    @Test(expected = ClosedPeriodException.class)
    public void updateAfterDeadline() throws Exception {
        Vote updated = VOTE1;
        updated.setMenu(MENU2);
        service.update(updated, USER_ID, LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 00)));
    }

    @Test
    public void getAll() throws Exception {
        List<Vote> all = service.getAll();
        assertMatch(all, VOTE1, VOTE2, VOTE3, VOTE4);
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.create(new Vote(null, null, MENU1)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Vote(null, USER3, null)), ConstraintViolationException.class);
    }
}