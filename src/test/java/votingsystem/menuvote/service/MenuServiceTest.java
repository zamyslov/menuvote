package votingsystem.menuvote.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import votingsystem.menuvote.model.Menu;
import votingsystem.menuvote.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

import static votingsystem.menuvote.testdata.DishTestData.DISH2;
import static votingsystem.menuvote.testdata.DishTestData.DISH3;
import static votingsystem.menuvote.testdata.MenuTestData.*;
import static votingsystem.menuvote.testdata.RestaurantTestData.RES1;

public class MenuServiceTest extends AbstractServiceTest {

    @Autowired
    protected MenuService service;

    @Test
    public void create() {
        LocalDate currentDate = LocalDate.of(2017, 12, 31);
        Menu newMenu = new Menu(null, RES1, currentDate);
        Menu created = service.create(newMenu);
        newMenu.setId(created.getId());
        assertMatch(service.getByDate(currentDate), newMenu);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateMenuCreate() {
        service.create(new Menu(null, RES1, LocalDate.of(2017, 12, 20)));
    }

    @Test
    public void delete() {
        service.delete(MENU1_ID);
        LocalDate currentDate = LocalDate.of(2017, 12, 20);
        assertMatch(service.getByDate(currentDate), MENU2);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() {
        service.delete(1);
    }

    @Test
    public void get() {
        Menu menu = service.get(MENU1_ID);
        assertMatch(menu, MENU1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(1);
    }

    @Test
    public void update() {
        Menu updated = new Menu(MENU1_ID, MENU1.getRestaurant(), MENU1.getDate());
        updated.setDate(LocalDate.of(2017, 12, 31));
        service.update(updated);
        assertMatch(service.get(MENU1_ID), updated);
    }

    @Test
    public void getByDate() {
        List<Menu> all = service.getByDate(LocalDate.of(2017, 12, 20));
        assertMatch(all, MENU1, MENU2);
    }

    @Test
    public void getBetween() {
        LocalDate currentDate = LocalDate.of(2017, 12, 20);
        List<Menu> all = service.getBetween(currentDate, currentDate);
        assertMatch(all, MENU1, MENU2);
    }

    @Test
    public void addMenuDish() {
        service.addMenuDish(MENU1.getId(), DISH3.getId(), 100);
        List<Menu> all = service.getByDate(LocalDate.of(2017, 12, 20));
        assertMatch(all, MENU1, MENU2);
    }

    @Test
    public void deleteMenuDish() {
        service.deleteMenuDish(MENU1.getId(), DISH2.getId());
        List<Menu> all = service.getByDate(LocalDate.of(2017, 12, 20));
        assertMatch(all, MENU1, MENU2);
    }

    @Test
    public void getBetweenWithVotes() {
        LocalDate currentDate = LocalDate.of(2017, 12, 20);
        List<Menu> all = service.getBetween(currentDate, currentDate);
        assertMatch(all, MENU1, MENU2);
    }

    @Test
    public void testValidation() {
        validateRootCause(() -> service.create(new Menu(null, RES1, null)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Menu(null, null, LocalDate.of(2017, 12, 20))), ConstraintViolationException.class);
    }
}