package votingsystem.menuvote.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import votingsystem.menuvote.model.Role;
import votingsystem.menuvote.model.User;
import votingsystem.menuvote.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static votingsystem.menuvote.testdata.UserTestData.*;
import static votingsystem.menuvote.testdata.UserTestData.assertMatch;
import static votingsystem.menuvote.testdata.VoteTestData.*;
import static votingsystem.menuvote.testdata.VoteTestData.assertMatch;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    protected UserService service;

    @Before
    public void setUpCacheUsers() {
        cacheManager.getCache("users").clear();
    }

    @Test
    public void create() {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER);
        User created = service.create(newUser);
        newUser.setId(created.getId());
        assertMatch(service.getAll(), ADMIN, newUser, USER, USER1, USER2, USER3);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateMailCreate() {
        service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.ROLE_USER));
    }

    @Test
    public void delete() {
        service.delete(USER_ID);
        assertMatch(service.getAll(), ADMIN, USER1, USER2, USER3);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() {
        service.delete(1);
    }

    @Test
    public void get() {
        User user = service.get(ADMIN_ID);
        assertMatch(user, ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(1);
    }

    @Test
    public void getByEmail() {
        User user = service.getByEmail("user@yandex.ru");
        assertMatch(user, USER);
    }

    @Test
    public void update() {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        service.update(updated);
        assertMatch(service.get(USER_ID), updated);
    }

    @Test
    public void getAll() {
        List<User> all = service.getAll();
        assertMatch(all, ADMIN, USER, USER1, USER2, USER3);
    }

    @Test
    public void getWithVotes() {
        User user = service.getWithVotes(USER2.getId());
        assertMatch(user, USER2);
        assertMatch(user.getVotes(), VOTE5, VOTE2);
    }

    @Test
    public void testValidation() {
        validateRootCause(() -> service.create(new User(null, "  ", "mail@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
//        validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "  ", Role.ROLE_USER)), ConstraintViolationException.class);
    }
}