package votingsystem.menuvote.service;


import votingsystem.menuvote.model.Dish;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static votingsystem.menuvote.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static final int DISH1_ID = Integer.valueOf(START_SEQ) + 7;
    public static final int DISH2_ID = DISH1_ID + 1;
    public static final int DISH3_ID = DISH2_ID + 1;

    public static final Dish DISH1 = new Dish(DISH1_ID, "Meat");
    public static final Dish DISH2 = new Dish(DISH2_ID, "Juice");
    public static final Dish DISH3 = new Dish(DISH3_ID, "Fish");

    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
