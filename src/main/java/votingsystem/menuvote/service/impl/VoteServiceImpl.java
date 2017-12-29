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
import votingsystem.menuvote.util.exception.NotFoundException;

import java.time.LocalDate;
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
    public void delete(LocalDate date, int user_id) throws NotFoundException {
        repository.delete(date, user_id);
    }


}