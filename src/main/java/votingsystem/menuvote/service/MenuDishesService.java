package votingsystem.menuvote.service;


import votingsystem.menuvote.model.MenuDishes;
import votingsystem.menuvote.util.exception.NotFoundException;

import java.util.List;

public interface MenuDishesService {

    MenuDishes create(MenuDishes menu);

    void delete(int id) throws NotFoundException;

    MenuDishes get(int id) throws NotFoundException;

    void update(MenuDishes menu);

    List<MenuDishes> getAll();

}