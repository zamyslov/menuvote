package votingsystem.menuvote.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import votingsystem.menuvote.model.Menu;
import votingsystem.menuvote.service.MenuService;
import votingsystem.menuvote.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

import static votingsystem.menuvote.service.MenuTestData.*;
import static votingsystem.menuvote.service.RestaurantTestData.RES1;

public class MenuServiceTest extends AbstractServiceTest {

//    @Autowired
//    private CacheManager cacheManager;

    @Autowired
    protected MenuService service;

//    @Before
//    public void setUpCacheUsers() throws Exception {
//        cacheManager.getCache("users").clear();
//    }

    @Test
    public void create() throws Exception {
        LocalDate currentDate = LocalDate.of(2017, 12, 31);
        Menu newMenu = new Menu(null, RES1, currentDate);
        Menu created = service.create(newMenu);
        newMenu.setId(created.getId());
        assertMatch(service.getByDate(currentDate), newMenu);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateMenuCreate() throws Exception {
        service.create(new Menu(null, RES1, LocalDate.of(2017, 12, 20)));
    }

    @Test
    public void delete() throws Exception {
        service.delete(MENU1_ID);
        LocalDate currentDate = LocalDate.of(2017, 12, 20);
        assertMatch(service.getByDate(currentDate), MENU2);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void get() throws Exception {
        Menu menu = service.get(MENU1_ID);
        assertMatch(menu, MENU1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1);
    }


    @Test
    public void update() throws Exception {
        Menu updated = new Menu(MENU1_ID, MENU1.getRestaurant(), MENU1.getDate());
        updated.setDate(LocalDate.of(2017, 12, 31));
        service.update(updated);
        assertMatch(service.get(MENU1_ID), updated);
    }

    @Test
    public void getByDate() throws Exception {
        List<Menu> all = service.getByDate(LocalDate.of(2017, 12, 20));
        assertMatch(all, MENU1, MENU2);
    }

    @Test
    public void getBetween() throws Exception {
        LocalDate currentDate = LocalDate.of(2017, 12, 20);
        List<Menu> all = service.getBetween(currentDate, currentDate);
        assertMatch(all, MENU1, MENU2);
    }

    @Test
    public void testValidation() throws Exception {
//        validateRootCause(() -> service.create(new Menu(null, null, LocalDate.of(2017, 12, 31))), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Menu(null, RES1, null)), ConstraintViolationException.class);
    }
}