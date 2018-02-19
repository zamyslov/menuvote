package votingsystem.menuvote.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import votingsystem.menuvote.model.MenuDishes;
import votingsystem.menuvote.repository.MenuDishesRepository;
import votingsystem.menuvote.service.MenuDishesService;
import votingsystem.menuvote.util.exception.NotFoundException;

import java.util.List;

import static votingsystem.menuvote.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuDishesServiceImpl implements MenuDishesService {

    private final MenuDishesRepository repository;

    @Autowired
    public MenuDishesServiceImpl(MenuDishesRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "menus_dishes", allEntries = true)
    @Override
    public MenuDishes create(MenuDishes menuDishes) {
        Assert.notNull(menuDishes, "menu dishes must not be null");
        return repository.save(menuDishes);
    }

    @CacheEvict(value = "menus_dishes", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public MenuDishes get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable("menus_dishes")
    @Override
    public List<MenuDishes> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "menus_dishes", allEntries = true)
    @Override
    public void update(MenuDishes menuDishes) {
        Assert.notNull(menuDishes, "menu dishes must not be null");
        repository.save(menuDishes);
    }

}