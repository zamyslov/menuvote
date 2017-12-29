package votingsystem.menuvote.service;



import votingsystem.menuvote.model.User;
import votingsystem.menuvote.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    User create(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    void update(User user);

    List<User> getAll();

    User getWithVotes(int id);

}