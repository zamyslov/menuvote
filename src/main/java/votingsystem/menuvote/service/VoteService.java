package votingsystem.menuvote.service;


import votingsystem.menuvote.model.Vote;
import votingsystem.menuvote.util.exception.NotFoundException;

import java.time.LocalDate;

public interface VoteService {

    Vote create(Vote vote);

    void delete(LocalDate date, int user_id) throws NotFoundException;

}