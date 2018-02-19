package votingsystem.menuvote.service;


import votingsystem.menuvote.model.Vote;
import votingsystem.menuvote.util.exception.ClosedPeriodException;
import votingsystem.menuvote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface VoteService {

    Vote create(Vote vote);

    List<Vote> getAll();

    void delete(LocalDate date, int user_id) throws NotFoundException;

    void update(Vote vote);

}