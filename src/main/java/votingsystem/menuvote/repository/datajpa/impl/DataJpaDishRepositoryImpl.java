package votingsystem.menuvote.repository.datajpa.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import votingsystem.menuvote.model.Dish;
import votingsystem.menuvote.repository.datajpa.CrudDishRepository;
import votingsystem.menuvote.repository.DishRepository;

import java.util.List;


@Repository
public class DataJpaDishRepositoryImpl implements DishRepository {

    @Autowired
    private CrudDishRepository crudRepository;

    @Override
    public Dish save(Dish dish) {
        return crudRepository.save(dish);
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    @Override
    public Dish get(int id) {
        return crudRepository.getById(id).orElse(null);
    }

    @Override
    public List<Dish> getAll() {
        return crudRepository.findAll();
    }
}
