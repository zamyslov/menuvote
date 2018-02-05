package votingsystem.menuvote.service;


import votingsystem.menuvote.model.MenuDishes;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static votingsystem.menuvote.service.DishTestData.*;
import static votingsystem.menuvote.service.MenuTestData.*;

public class MenuDishesTestData {

    public static final MenuDishes MENUDISH1 = new MenuDishes(MENU1, DISH1, 20.0);
    public static final MenuDishes MENUDISH2 = new MenuDishes(MENU1, DISH2, 15.5);
    public static final MenuDishes MENUDISH3 = new MenuDishes(MENU2, DISH3, 21.6);
    public static final MenuDishes MENUDISH4 = new MenuDishes(MENU2, DISH2, 10.4);

    public static void assertMatch(MenuDishes actual, MenuDishes expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<MenuDishes> actual, MenuDishes... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<MenuDishes> actual, Iterable<MenuDishes> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
