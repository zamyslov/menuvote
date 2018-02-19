package web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import votingsystem.menuvote.model.Menu;
import votingsystem.menuvote.model.User;
import votingsystem.menuvote.model.Vote;
import votingsystem.menuvote.service.VoteService;
import web.AuthorizedUser;

import java.time.LocalDate;

@RestController
@RequestMapping(VoteRestController.REST_URL)
public class VoteRestController {
    static final String REST_URL = "/rest/vote";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoteService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void create(@Validated @RequestBody Menu menu) {
        LocalDate currentDate = LocalDate.now();
        User currentUser = AuthorizedUser.get().getUser();
        Vote currentVote = service.getByDateAndUser(currentDate, currentUser);
        if (currentVote != null) {
            currentVote.setMenu(menu);
            currentVote.setDate(currentDate);
            log.info("update {}", currentVote);
            service.update(currentVote);
        } else {
            Vote vote = new Vote(null, currentUser, menu, currentDate);
            log.info("create {}", vote);
            service.create(vote);
        }
    }

}
