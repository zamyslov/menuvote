package web.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import votingsystem.menuvote.model.Menu;
import votingsystem.menuvote.model.MenuDishes;
import votingsystem.menuvote.service.MenuService;

@RestController
@RequestMapping(AdminMenuRestController.REST_URL)
public class AdminMenuRestController {
    static final String REST_URL = "/rest/admin/menu";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MenuService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void create(@Validated @RequestBody Menu menu) {
        log.info("create {}", menu);
        service.create(menu);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Validated @RequestBody Menu menu) {
        log.info("update {}", menu);
        service.update(menu);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void addDish(@Validated @RequestBody MenuDishes menuDish, @PathVariable("id") int id) {
        log.info("add dish {} in menu", menuDish, id);
        service.addMenuDish(id, menuDish.getDish().getId(), menuDish.getPrice());
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteDish(@Validated @RequestBody MenuDishes menuDish, @PathVariable("id") int id) {
        log.info("delete dish {} in menu", menuDish, id);
        service.deleteMenuDish(id, menuDish.getDish().getId());
    }
}
