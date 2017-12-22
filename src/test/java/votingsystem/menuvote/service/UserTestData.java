package votingsystem.menuvote.service;


import votingsystem.menuvote.model.Role;
import votingsystem.menuvote.model.User;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static votingsystem.menuvote.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final int USER_ID = Integer.valueOf(START_SEQ);
    public static final int ADMIN_ID = USER_ID+ 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered").isEqualTo(expected);
    }
}
