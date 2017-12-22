package votingsystem.menuvote.service;


import votingsystem.menuvote.model.Dish;
import votingsystem.menuvote.util.exception.NotFoundException;

import java.util.List;

public interface DishService {

    Dish create(Dish dish);

    void delete(int id) throws NotFoundException;

    Dish get(int id) throws NotFoundException;

    void update(Dish dish);

    List<Dish> getAll();

}