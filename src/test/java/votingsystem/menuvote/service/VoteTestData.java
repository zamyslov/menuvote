package votingsystem.menuvote.service;


import votingsystem.menuvote.model.Vote;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static votingsystem.menuvote.model.AbstractBaseEntity.START_SEQ;
import static votingsystem.menuvote.service.MenuTestData.MENU1;
import static votingsystem.menuvote.service.MenuTestData.MENU2;
import static votingsystem.menuvote.service.UserTestData.*;

public class VoteTestData {
    private static final int VOTE1_ID = Integer.valueOf(START_SEQ) + 16;
    private static final int VOTE2_ID = VOTE1_ID + 1;
    private static final int VOTE3_ID = VOTE1_ID + 2;
    private static final int VOTE4_ID = VOTE1_ID + 3;

    public static final Vote VOTE1 = new Vote(VOTE1_ID, USER, MENU1, LocalDate.of(2017, 12, 20));
    public static final Vote VOTE2 = new Vote(VOTE2_ID, USER2, MENU1, LocalDate.of(2017, 12, 20));
    public static final Vote VOTE3 = new Vote(VOTE3_ID, ADMIN, MENU1, LocalDate.of(2017, 12, 20));
    public static final Vote VOTE4 = new Vote(VOTE4_ID, USER1, MENU2, LocalDate.of(2017, 12, 20));

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
