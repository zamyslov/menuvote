package votingsystem.menuvote.service;


import votingsystem.menuvote.model.Menu;
import votingsystem.menuvote.model.MenuDishes;
import votingsystem.menuvote.model.Vote;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static votingsystem.menuvote.model.AbstractBaseEntity.START_SEQ;
import static votingsystem.menuvote.service.MenuDishesTestData.*;
import static votingsystem.menuvote.service.RestaurantTestData.RES1;
import static votingsystem.menuvote.service.RestaurantTestData.RES2;
import static votingsystem.menuvote.service.VoteTestData.*;

public class MenuTestData {
    public static final int MENU1_ID = Integer.valueOf(START_SEQ) + 10;
    private static final int MENU2_ID = MENU1_ID + 1;
    private static final int MENU3_ID = MENU1_ID + 2;
    private static final int MENU4_ID = MENU1_ID + 3;

    public static final Menu MENU1 = new Menu(MENU1_ID, RES1, LocalDate.of(2017, 12, 20));
    public static final Menu MENU2 = new Menu(MENU2_ID, RES2, LocalDate.of(2017, 12, 20));
    public static final Menu MENU3 = new Menu(MENU3_ID, RES1, LocalDate.of(2017, 12, 21));
    public static final Menu MENU4 = new Menu(MENU4_ID, RES2, LocalDate.of(2017, 12, 21));

    static {
        HashSet<MenuDishes> dishesForMenu1 = new HashSet<>();
        dishesForMenu1.add(MENUDISH1);
        dishesForMenu1.add(MENUDISH2);
        MENU1.setMenuDishes(dishesForMenu1);
        HashSet<Vote> votesForMenu1 = new HashSet<>();
        votesForMenu1.add(VOTE1);
        votesForMenu1.add(VOTE2);
        votesForMenu1.add(VOTE3);
        MENU1.setVotes(votesForMenu1);
        HashSet<MenuDishes> dishesForMenu2 = new HashSet<>();
        dishesForMenu2.add(MENUDISH3);
        dishesForMenu2.add(MENUDISH4);
        MENU2.setMenuDishes(dishesForMenu2);
        HashSet<Vote> votesForMenu2 = new HashSet<>();
        votesForMenu2.add(VOTE4);
        MENU2.setVotes(votesForMenu2);
        HashSet<MenuDishes> dishesForMenu3 = new HashSet<>();
        dishesForMenu3.add(MENUDISH5);
        MENU3.setMenuDishes(dishesForMenu3);
        HashSet<Vote> votesForMenu3 = new HashSet<>();
        votesForMenu3.add(VOTE5);
        votesForMenu3.add(VOTE6);
        MENU3.setVotes(votesForMenu3);
        HashSet<MenuDishes> dishesForMenu4 = new HashSet<>();
        dishesForMenu4.add(MENUDISH6);
        MENU4.setMenuDishes(dishesForMenu4);
        HashSet<Vote> votesForMenu4= new HashSet<>();
        MENU4.setVotes(votesForMenu4);
    }

    public static void assertMatch(Menu actual, Menu expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Menu> actual, Menu... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Menu> actual, Iterable<Menu> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
