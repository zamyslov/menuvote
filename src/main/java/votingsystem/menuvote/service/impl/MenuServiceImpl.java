package votingsystem.menuvote.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import votingsystem.menuvote.model.Menu;
import votingsystem.menuvote.model.MenuDishes;
import votingsystem.menuvote.repository.DishRepository;
import votingsystem.menuvote.repository.MenuRepository;
import votingsystem.menuvote.service.MenuService;
import votingsystem.menuvote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import static votingsystem.menuvote.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final DishRepository dishRepository;

    @Autowired
    public MenuServiceImpl(MenuRepository menuRepository, DishRepository dishRepository) {
        this.menuRepository = menuRepository;
        this.dishRepository = dishRepository;
    }

    @CacheEvict(value = "menus", allEntries = true)
    @Override
    public Menu create(Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        return menuRepository.save(menu);
    }

    @CacheEvict(value = "menus", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(menuRepository.delete(id), id);
    }

    @Override
    public Menu get(int id) throws NotFoundException {
        return checkNotFoundWithId(menuRepository.get(id), id);
    }

    @CacheEvict(value = "menus", allEntries = true)
    @Override
    public void update(Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        checkNotFoundWithId(menuRepository.save(menu), menu.getId());
    }

    @Override
    public List<Menu> getByDate(LocalDate date) throws NotFoundException {
        return getBetween(date, date);
    }

    @Override
    public List<Menu> getBetween(LocalDate startDate, LocalDate endDate) {
        Assert.notNull(startDate, "start date must not be null");
        Assert.notNull(endDate, "end date must not be null");
        return menuRepository.getBetween(startDate, endDate);
    }

    @Override
    public void deleteMenuDish(int menu_id, int dish_id) {
        Menu menu = menuRepository.get(menu_id);
        Iterator<MenuDishes> it = menu.getMenuDishes().iterator();
        while (it.hasNext()) {
            if (it.next().getDish().equals(dishRepository.get(dish_id))) {
                it.remove();
                break;
            }
        }
        update(menu);
    }

    @Override
    public void addMenuDish(int menu_id, int dish_id, double price) {
        Menu menu = menuRepository.get(menu_id);
        menu.getMenuDishes().add(new MenuDishes(menu, dishRepository.get(dish_id), price));
        update(menu);
    }

    @Override
    public List<Menu> getAll() {
        return menuRepository.getAll();
    }


}