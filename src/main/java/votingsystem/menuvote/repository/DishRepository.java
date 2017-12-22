package votingsystem.menuvote.repository;

import votingsystem.menuvote.model.Dish;

import java.util.List;

public interface DishRepository {
    Dish save(Dish restaurant);

    // false if not found
    boolean delete(int id);

    // null if not found
    Dish get(int id);

    List<Dish> getAll();
}