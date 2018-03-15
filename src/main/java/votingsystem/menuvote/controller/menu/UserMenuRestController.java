package votingsystem.menuvote.controller.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import votingsystem.menuvote.model.Menu;
import votingsystem.menuvote.service.MenuService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(UserMenuRestController.REST_URL)
public class UserMenuRestController {
    static final String REST_URL = "/rest/menu";
    private final Logger log = LoggerFactory.getLogger(getClass());
    public static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    public static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    @Autowired
    private MenuService service;

    @GetMapping()
    public List<Menu> getToday() {
        log.info("get today menu");
        return service.getByDate(LocalDate.now());
    }

    @GetMapping(value = "/filter")
    public List<Menu> getBetween(
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        LocalDate start = startDate != null ? startDate : MIN_DATE;
        LocalDate end = endDate != null ? endDate : MAX_DATE;
        log.info("get between {} and {}", start.toString(), end.toString());
        return service.getBetween(start, end);
    }
}
