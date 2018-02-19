package votingsystem.menuvote.repository.datajpa.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import votingsystem.menuvote.model.User;
import votingsystem.menuvote.model.Vote;
import votingsystem.menuvote.repository.datajpa.CrudVoteRepository;
import votingsystem.menuvote.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaVoteRepositoryImpl implements VoteRepository {

    @Autowired
    private CrudVoteRepository crudRepository;

    @Override
    public Vote save(Vote vote) {
        return crudRepository.save(vote);
    }

    @Override
    public List<Vote> getAll() {
        return crudRepository.getAll();
    }

    @Override
    public boolean delete(LocalDate date, int user_id) {
        return crudRepository.delete(date, user_id) != 0;
    }

    @Override
    public Vote getByDateAndUser(LocalDate date, User user) {
        return crudRepository.getByDateAndUser(date, user);
    }
}
