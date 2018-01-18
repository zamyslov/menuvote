package votingsystem.menuvote.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import votingsystem.menuvote.model.User;
import votingsystem.menuvote.model.Vote;
import votingsystem.menuvote.repository.UserRepository;
import votingsystem.menuvote.repository.VoteRepository;
import votingsystem.menuvote.service.UserService;
import votingsystem.menuvote.service.VoteService;
import votingsystem.menuvote.util.exception.ClosedPeriodException;
import votingsystem.menuvote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static votingsystem.menuvote.util.ValidationUtil.checkNotFound;
import static votingsystem.menuvote.util.ValidationUtil.checkNotFoundWithId;

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
        return repository.save(vote);
    }

    @Override
    public List<Vote> getAll() {
        return repository.getAll();
    }

    @Override
    public void delete(LocalDate date, int user_id) throws NotFoundException {
        checkNotFound(repository.delete(date, user_id),"date:"+date+"user:"+user_id);
    }

    @Override
    public void update(Vote vote, int user_id) throws NotFoundException {
        update(vote, user_id, LocalDateTime.now());
    }

    @Override
    public void update(Vote vote, int user_id, LocalDateTime date) throws NotFoundException {
        if (date.isAfter(LocalDateTime.of(LocalDate.now(), LocalTime.of(11,00)))) {
            throw new ClosedPeriodException("This period is closed for votes");
        }else {
            repository.save(vote);
        }
    }
}