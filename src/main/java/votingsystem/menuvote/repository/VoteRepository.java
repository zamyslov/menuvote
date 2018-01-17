package votingsystem.menuvote.repository;

import votingsystem.menuvote.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {
    Vote save(Vote vote);

    List<Vote> getAll();

    // false if not found
    boolean delete(LocalDate date, int user_id);
}