package votingsystem.menuvote.service;


import votingsystem.menuvote.model.Restaurant;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static votingsystem.menuvote.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final int RES1_ID = Integer.valueOf(START_SEQ) + 5;
    private static final int RES2_ID = RES1_ID + 1;

    public static final Restaurant RES1 = new Restaurant(RES1_ID, "Restaurant1", "Sumskaya, 20");
    public static final Restaurant RES2 = new Restaurant(RES2_ID, "Restaurant2", "Nauki, 30");

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "menus");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("menus").isEqualTo(expected);
    }
}
