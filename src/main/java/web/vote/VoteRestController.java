package web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import votingsystem.menuvote.model.Vote;
import votingsystem.menuvote.service.VoteService;

@RestController
@RequestMapping(VoteRestController.REST_URL)
public class VoteRestController {
    static final String REST_URL = "/rest/vote";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private VoteService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void create(@Validated @RequestBody Vote vote) {
        Vote currentVote = service.getByDateAndUser(vote.getDate(), vote.getUser());
        if (currentVote != null) {
            log.info("update {}", vote);
            service.update(vote);
        } else {
            log.info("create {}", vote);
            service.create(vote);
        }
    }

}
