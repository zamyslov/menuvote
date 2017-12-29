package votingsystem.menuvote.repository;

import votingsystem.menuvote.model.Vote;

public interface VoteRepository {
    Vote save(Vote vote);

    // false if not found
    boolean delete(int id);

    // null if not found
    Vote get(int id);
}