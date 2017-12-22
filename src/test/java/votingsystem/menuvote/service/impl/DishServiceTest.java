package votingsystem.menuvote.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import votingsystem.menuvote.model.Dish;
import votingsystem.menuvote.service.DishService;
import votingsystem.menuvote.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static votingsystem.menuvote.service.DishTestData.*;

public class DishServiceTest extends AbstractServiceTest {

//    @Autowired
//    private CacheManager cacheManager;

    @Autowired
    protected DishService service;

//    @Before
//    public void setUpCacheUsers() throws Exception {
//        cacheManager.getCache("users").clear();
//    }

    @Test
    public void create() throws Exception {
        Dish newDish = new Dish(null, "Cake", 2.0);
        Dish created = service.create(newDish);
        newDish.setId(created.getId());
        assertMatch(service.getAll(), DISH1, DISH2, DISH3, newDish);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDishCreate() throws Exception {
        service.create(new Dish(null, "Meat", 20.4));
    }

    @Test
    public void delete() throws Exception {
        service.delete(DISH2_ID);
        assertMatch(service.getAll(), DISH1, DISH3);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void get() throws Exception {
        Dish dish = service.get(DISH2_ID);
        assertMatch(dish, DISH2);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void update() throws Exception {
        Dish updated = new Dish(DISH3);
        updated.setPrice(100.0);
        service.update(updated);
        assertMatch(service.get(DISH3_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<Dish> all = service.getAll();
        assertMatch(all, DISH1, DISH2, DISH3);
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.create(new Dish(null, "  ", 20.5)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Dish(null, "name", null)), ConstraintViolationException.class);
    }
}