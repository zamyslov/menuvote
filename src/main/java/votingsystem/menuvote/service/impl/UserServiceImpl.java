package votingsystem.menuvote.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import votingsystem.menuvote.model.User;
import votingsystem.menuvote.repository.UserRepository;
import votingsystem.menuvote.service.UserService;
import votingsystem.menuvote.util.exception.NotFoundException;
import votingsystem.menuvote.controller.AuthorizedUser;

import java.util.List;

import static votingsystem.menuvote.util.ValidationUtil.checkNotFound;
import static votingsystem.menuvote.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public User getWithVotes(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.getWithVotes(id), id);
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @Cacheable("users")
    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        checkNotFoundWithId(repository.save(user), user.getId());
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }
}