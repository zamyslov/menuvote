package votingsystem.menuvote.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import votingsystem.menuvote.model.Menu;
import votingsystem.menuvote.repository.CrudMenuRepository;
import votingsystem.menuvote.repository.MenuRepository;

import java.util.List;


@Repository
public class DataJpaMenuRepositoryImpl implements MenuRepository {

    @Autowired
    private CrudMenuRepository crudRepository;

    @Override
    public Menu save(Menu dish) {
        return crudRepository.save(dish);
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    @Override
    public Menu get(int id) {
        return crudRepository.getById(id).orElse(null);
    }

    @Override
    public List<Menu> getAll() {
        return crudRepository.findAll();
    }
}