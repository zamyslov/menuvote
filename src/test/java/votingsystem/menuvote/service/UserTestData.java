package votingsystem.menuvote.service;


import votingsystem.menuvote.model.Role;
import votingsystem.menuvote.model.User;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static votingsystem.menuvote.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final int USER_ID = Integer.valueOf(START_SEQ);
    public static final int USER1_ID = USER_ID + 1;
    public static final int USER2_ID = USER_ID + 2;
    public static final int USER3_ID = USER_ID + 3;
    public static final int ADMIN_ID = USER_ID + 4;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User USER1 = new User(USER1_ID, "User1", "user1@yandex.ru", "password", Role.ROLE_USER);
    public static final User USER2 = new User(USER2_ID, "User2", "user2@yandex.ru", "password", Role.ROLE_USER);
    public static final User USER3 = new User(USER3_ID, "User3", "user3@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "votes");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "votes").isEqualTo(expected);
    }
}
