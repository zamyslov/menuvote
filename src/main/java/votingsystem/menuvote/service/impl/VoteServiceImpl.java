package votingsystem.menuvote.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import votingsystem.menuvote.model.User;
import votingsystem.menuvote.model.Vote;
import votingsystem.menuvote.repository.VoteRepository;
import votingsystem.menuvote.service.VoteService;
import votingsystem.menuvote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static votingsystem.menuvote.util.ValidationUtil.checkNotFound;
import static votingsystem.menuvote.util.VoteUtil.checkVoteForTime;

@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository repository;

    @Autowired
    public VoteServiceImpl(VoteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Vote create(Vote vote) {
        Assert.notNull(vote, "vote must not be null");
        checkVoteForTime(LocalDateTime.now());
        return repository.save(vote);
    }

    @Override
    public List<Vote> getAll() {
        return repository.getAll();
    }

    @Override
    public void delete(LocalDate date, int user_id) throws NotFoundException {
        checkVoteForTime(LocalDateTime.now());
        checkNotFound(repository.delete(date, user_id), "date:" + date + "user:" + user_id);
    }

    @Override
    public void update(Vote vote) {
        checkVoteForTime(LocalDateTime.now());
        repository.save(vote);
    }

    @Override
    public Vote getByDateAndUser(LocalDate date, User user) {
        Assert.notNull(date, "date must not be null");
        Assert.notNull(user, "user must not be null");
        return repository.getByDateAndUser(date, user);
    }
}