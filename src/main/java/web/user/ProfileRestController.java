package web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import votingsystem.menuvote.model.User;
import web.AuthorizedUser;

import javax.validation.Valid;

@RestController
@RequestMapping(ProfileRestController.REST_URL)
public class ProfileRestController extends AbstractUserController {
    static final String REST_URL = "/rest/profile";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(AuthorizedUser authorizedUser) {
        return super.get(authorizedUser.getId());
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(AuthorizedUser authorizedUser) {
        super.delete(authorizedUser.getId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody User user, AuthorizedUser authorizedUser) {
        super.update(user, authorizedUser.getId());
    }

}