package votingsystem.menuvote.testdata;


import votingsystem.menuvote.model.MenuDishes;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static votingsystem.menuvote.testdata.DishTestData.*;
import static votingsystem.menuvote.testdata.MenuTestData.*;

public class MenuDishesTestData {

    public static final MenuDishes MENUDISH1 = new MenuDishes(MENU1, DISH1, 20.0);
    public static final MenuDishes MENUDISH2 = new MenuDishes(MENU1, DISH2, 5.5);
    public static final MenuDishes MENUDISH3 = new MenuDishes(MENU2, DISH2, 5.5);
    public static final MenuDishes MENUDISH4 = new MenuDishes(MENU2, DISH3, 40.5);
    public static final MenuDishes MENUDISH5 = new MenuDishes(MENU3, DISH1, 15.5);
    public static final MenuDishes MENUDISH6 = new MenuDishes(MENU4, DISH3, 25.5);

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
