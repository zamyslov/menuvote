package votingsystem.menuvote.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import votingsystem.menuvote.model.MenuDishes;
import votingsystem.menuvote.repository.CrudMenuDishesRepository;
import votingsystem.menuvote.repository.MenuDishesRepository;

import java.util.List;


@Repository
public class DataJpaMenuDishesRepositoryImpl implements MenuDishesRepository {

    @Autowired
    private CrudMenuDishesRepository crudRepository;

    @Override
    public MenuDishes save(MenuDishes dish) {
        return crudRepository.save(dish);
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    @Override
    public MenuDishes get(int id) {
        return crudRepository.getById(id).orElse(null);
    }

    @Override
    public List<MenuDishes> getAll() {
        return crudRepository.findAll();
    }
}
