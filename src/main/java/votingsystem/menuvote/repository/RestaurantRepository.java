package votingsystem.menuvote.repository;

import votingsystem.menuvote.model.Restaurant;
import votingsystem.menuvote.model.User;

import java.util.List;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);

    // false if not found
    boolean delete(int id);

    // null if not found
    Restaurant get(int id);

    List<Restaurant> getAll();
}