package votingsystem.menuvote.service;


import votingsystem.menuvote.model.User;
import votingsystem.menuvote.model.Vote;
import votingsystem.menuvote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface VoteService {

    Vote create(Vote vote);

    List<Vote> getAll();

    void delete(LocalDate date, int user_id) throws NotFoundException;

    Vote getByDateAndUser(LocalDate date, User user);

}