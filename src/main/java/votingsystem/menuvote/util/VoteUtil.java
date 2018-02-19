package votingsystem.menuvote.util;

import votingsystem.menuvote.util.exception.ClosedPeriodException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class VoteUtil {
    private static LocalTime maxTimeForVote = LocalTime.of(11, 0);
    private static LocalTime minTimeForVote = LocalTime.of(0, 0);

    public static boolean checkVoteForTime(LocalDateTime date) throws ClosedPeriodException {
        if (date.isAfter(LocalDateTime.of(LocalDate.now(), maxTimeForVote))
                || (date.isBefore(LocalDateTime.of(LocalDate.now(), minTimeForVote)))) {
            throw new ClosedPeriodException("This period is closed for votes");
        } else {
            return true;
        }
    }
}
