package votingsystem.menuvote.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import votingsystem.menuvote.model.Restaurant;
import votingsystem.menuvote.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static votingsystem.menuvote.testdata.RestaurantTestData.*;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    protected RestaurantService service;

    @Before
    public void setUpCacheUsers() {
        cacheManager.getCache("restaurants").clear();
    }

    @Test
    public void create() {
        Restaurant newRestaurant = new Restaurant(null, "New", "address");
        Restaurant created = service.create(newRestaurant);
        newRestaurant.setId(created.getId());
        assertMatch(service.getAll(), RES1, RES2, newRestaurant);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateRestaurantCreate() {
        service.create(new Restaurant(null, "Restaurant1", "address"));
    }

    @Test
    public void delete() {
        service.delete(RES1_ID);
        assertMatch(service.getAll(), RES2);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() {
        service.delete(1);
    }

    @Test
    public void get() {
        Restaurant restaurant = service.get(RES1_ID);
        assertMatch(restaurant, RES1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(1);
    }

    @Test
    public void update() {
        Restaurant updated = new Restaurant(RES1);
        updated.setName("UpdatedName");
        service.update(updated);
        assertMatch(service.get(RES1_ID), updated);
    }

    @Test
    public void getAll() {
        List<Restaurant> all = service.getAll();
        assertMatch(all, RES1, RES2);
    }

    @Test
    public void testValidation() {
        validateRootCause(() -> service.create(new Restaurant(null, "  ", "address")), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Restaurant(null, "name", "  ")), ConstraintViolationException.class);
    }
}