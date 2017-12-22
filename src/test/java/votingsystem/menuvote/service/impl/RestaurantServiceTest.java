package votingsystem.menuvote.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import votingsystem.menuvote.model.Restaurant;
import votingsystem.menuvote.service.RestaurantService;
import votingsystem.menuvote.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static votingsystem.menuvote.service.RestaurantTestData.*;

public class RestaurantServiceTest extends AbstractServiceTest {

//    @Autowired
//    private CacheManager cacheManager;

    @Autowired
    protected RestaurantService service;

//    @Before
//    public void setUpCacheUsers() throws Exception {
//        cacheManager.getCache("users").clear();
//    }

    @Test
    public void create() throws Exception {
        Restaurant newRestaurant = new Restaurant(null, "New", "adress");
        Restaurant created = service.create(newRestaurant);
        newRestaurant.setId(created.getId());
        assertMatch(service.getAll(), RES1, RES2, newRestaurant);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateRestaurantCreate() throws Exception {
        service.create(new Restaurant(null, "Restaurant1", "address"));
    }

    @Test
    public void delete() throws Exception {
        service.delete(RES1_ID);
        assertMatch(service.getAll(), RES2);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void get() throws Exception {
        Restaurant restaurant = service.get(RES1_ID);
        assertMatch(restaurant, RES1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1);
    }


    @Test
    public void update() throws Exception {
        Restaurant updated = new Restaurant(RES1);
        updated.setName("UpdatedName");
        service.update(updated);
        assertMatch(service.get(RES1_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<Restaurant> all = service.getAll();
        assertMatch(all, RES1, RES2);
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.create(new Restaurant(null, "  ", "address")), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Restaurant(null, "name", "  ")), ConstraintViolationException.class);
    }
}