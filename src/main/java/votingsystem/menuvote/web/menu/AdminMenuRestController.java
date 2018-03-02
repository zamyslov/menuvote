package votingsystem.menuvote.web.menu;

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
import votingsystem.menuvote.model.MenuDishes;
import votingsystem.menuvote.service.MenuService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(AdminMenuRestController.REST_URL)
public class AdminMenuRestController {
    static final String REST_URL = "/rest/admin/menu";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MenuService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> create(@Validated @RequestBody Menu menu) {
        log.info("create {}", menu);
        Menu created = service.create(menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Validated @RequestBody Menu menu) {
        log.info("update {}", menu);
        service.update(menu);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@Validated @RequestBody int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> addDish(@Validated @RequestBody MenuDishes menuDish, @PathVariable("id") int id) {
        Menu menu = service.get(id);
        log.info("add dish {} in menu", menuDish, menu);
        service.addMenuDish(id, menuDish.getDish().getId(), menuDish.getPrice());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(menu.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(menu);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteDish(@Validated @RequestBody MenuDishes menuDish, @PathVariable("id") int id) {
        log.info("delete dish {} in menu", menuDish, id);
        service.deleteMenuDish(id, menuDish.getDish().getId());
    }
}
