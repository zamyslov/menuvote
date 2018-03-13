package votingsystem.menuvote.testdata;


import org.springframework.test.web.servlet.ResultMatcher;
import votingsystem.menuvote.model.Role;
import votingsystem.menuvote.model.User;
import votingsystem.menuvote.util.json.JsonUtil;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static votingsystem.menuvote.model.AbstractBaseEntity.START_SEQ;
import static votingsystem.menuvote.util.json.JsonUtil.writeIgnoreProps;

public class UserTestData {

    public static final int USER_ID = Integer.valueOf(START_SEQ);
    private static final int USER1_ID = USER_ID + 1;
    private static final int USER2_ID = USER_ID + 2;
    private static final int USER3_ID = USER_ID + 3;
    public static final int ADMIN_ID = USER_ID + 4;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "$2a$10$DLVDKA7WrLvKwdcQy5HdjepOy74gIGQlkh159EqEys3bOtUE3aOHK", Role.ROLE_USER);
    public static final User USER1 = new User(USER1_ID, "User1", "user1@yandex.ru", "$2a$10$DLVDKA7WrLvKwdcQy5HdjepOy74gIGQlkh159EqEys3bOtUE3aOHK", Role.ROLE_USER);
    public static final User USER2 = new User(USER2_ID, "User2", "user2@yandex.ru", "$2a$10$DLVDKA7WrLvKwdcQy5HdjepOy74gIGQlkh159EqEys3bOtUE3aOHK", Role.ROLE_USER);
    public static final User USER3 = new User(USER3_ID, "User3", "user3@yandex.ru", "$2a$10$DLVDKA7WrLvKwdcQy5HdjepOy74gIGQlkh159EqEys3bOtUE3aOHK", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "$2a$10$TjHDJyLWPR5umEHZs4Vame7V98jz4FJvvk63uOXm6YF0s4PeJMCjy", Role.ROLE_ADMIN);
    public static final User USER_AUTH = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN_AUTH = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "votes", "password");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "votes", "password").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(User... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "registered", "password"));
    }

    public static ResultMatcher contentJson(User expected) {
        return content().json(writeIgnoreProps(expected, "registered", "password"));
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }

}
