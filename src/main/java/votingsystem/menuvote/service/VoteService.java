package votingsystem.menuvote.service;


import votingsystem.menuvote.model.Vote;
import votingsystem.menuvote.util.exception.ClosedPeriodException;
import votingsystem.menuvote.util.exception.NotFoundException;

import java.time.LocalDate;

public interface VoteService {

    Vote create(Vote vote);

    void delete(LocalDate date, int user_id) throws NotFoundException;

    void update(Vote vote, int user_id) throws ClosedPeriodException;

    void update(Vote vote, int user_id, LocalDate date) throws ClosedPeriodException;

}