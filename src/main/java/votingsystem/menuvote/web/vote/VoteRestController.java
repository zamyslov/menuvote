package votingsystem.menuvote.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import votingsystem.menuvote.model.Menu;
import votingsystem.menuvote.model.Vote;
import votingsystem.menuvote.service.VoteService;
import votingsystem.menuvote.web.AuthorizedUser;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping(VoteRestController.REST_URL)
public class VoteRestController {
    static final String REST_URL = "/rest/vote";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoteService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@Validated @RequestBody Menu menu) {
        Vote vote = new Vote(null, AuthorizedUser.get().getUser(), menu, LocalDate.now());
        Vote created = service.create(vote);
        log.info("create {}", created);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@Validated @RequestBody Menu menu) {
        log.info("delete {} for menu", menu);
        service.delete(menu.getDate(), AuthorizedUser.id());
    }
}
