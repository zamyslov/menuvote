package votingsystem.menuvote.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import votingsystem.menuvote.model.Dish;
import votingsystem.menuvote.model.Menu;
import votingsystem.menuvote.repository.DishRepository;
import votingsystem.menuvote.repository.MenuRepository;
import votingsystem.menuvote.service.MenuService;
import votingsystem.menuvote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static votingsystem.menuvote.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository repository;

    @Autowired
    public MenuServiceImpl(MenuRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "menus", allEntries = true)
    @Override
    public Menu create(Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        return repository.save(menu);
    }

    @CacheEvict(value = "menus", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Menu get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @CacheEvict(value = "menus", allEntries = true)
    @Override
    public void update(Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        checkNotFoundWithId(repository.save(menu), menu.getId());
    }

    @Override
    public List<Menu> getBetween(LocalDate startDate, LocalDate endDate) {
        Assert.notNull(startDate, "start date must not be null");
        Assert.notNull(endDate, "end date must not be null");
        return repository.getBetween(startDate, endDate);
    }

    @Override
    public List<Menu> getBetweenWithVotes(LocalDate startDate, LocalDate endDate) {
        Assert.notNull(startDate, "start date must not be null");
        Assert.notNull(endDate, "end date must not be null");
        return repository.getBetweenWithVotes(startDate, endDate);
    }
}