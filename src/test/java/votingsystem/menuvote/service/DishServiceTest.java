package votingsystem.menuvote.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import votingsystem.menuvote.model.Dish;
import votingsystem.menuvote.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static votingsystem.menuvote.testdata.DishTestData.*;

public class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    protected DishService service;

    @Before
    public void setUpCacheUsers() {
        cacheManager.getCache("dishes").clear();
    }

    @Test
    public void create() {
        Dish newDish = new Dish(null, "Cake");
        Dish created = service.create(newDish);
        newDish.setId(created.getId());
        assertMatch(service.getAll(), DISH1, DISH2, DISH3, newDish);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDishCreate() {
        service.create(new Dish(null, "Meat"));
    }

    @Test
    public void delete() {
        service.delete(DISH2_ID);
        assertMatch(service.getAll(), DISH1, DISH3);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() {
        service.delete(1);
    }

    @Test
    public void get() {
        Dish dish = service.get(DISH2_ID);
        assertMatch(dish, DISH2);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(1);
    }

    @Test
    public void update() {
        Dish updated = new Dish(DISH3);
        updated.setName("new Dish");
        service.update(updated);
        assertMatch(service.get(DISH3_ID), updated);
    }

    @Test
    public void getAll() {
        List<Dish> all = service.getAll();
        assertMatch(all, DISH1, DISH2, DISH3);
    }

    @Test
    public void testValidation() {
        validateRootCause(() -> service.create(new Dish(null, "  ")), ConstraintViolationException.class);
    }
}