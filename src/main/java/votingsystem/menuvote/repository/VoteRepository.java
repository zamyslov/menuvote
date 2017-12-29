package votingsystem.menuvote.repository;

import votingsystem.menuvote.model.Vote;

import java.time.LocalDate;

public interface VoteRepository {
    Vote save(Vote vote);

    // false if not found
    boolean delete(LocalDate date, int user_id);
}