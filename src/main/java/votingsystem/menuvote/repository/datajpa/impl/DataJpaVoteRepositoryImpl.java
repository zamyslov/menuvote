package votingsystem.menuvote.repository.datajpa.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import votingsystem.menuvote.model.Vote;
import votingsystem.menuvote.repository.datajpa.CrudVoteRepository;
import votingsystem.menuvote.repository.VoteRepository;

import java.time.LocalDate;

@Repository
public class DataJpaVoteRepositoryImpl implements VoteRepository {

    @Autowired
    private CrudVoteRepository crudRepository;

    @Override
    public Vote save(Vote vote) {
        return crudRepository.save(vote);
    }

    @Override
    public boolean delete(LocalDate date, int user_id) {
        return crudRepository.delete(date, user_id) != 0;
    }

}
