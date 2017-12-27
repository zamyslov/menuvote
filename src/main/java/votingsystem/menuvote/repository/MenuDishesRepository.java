package votingsystem.menuvote.repository;

import votingsystem.menuvote.model.Menu;
import votingsystem.menuvote.model.MenuDishes;

import java.util.List;

public interface MenuDishesRepository {
    MenuDishes save(MenuDishes menu);

    // false if not found
    boolean delete(int id);

    // null if not found
    MenuDishes get(int id);

    List<MenuDishes> getAll();
}