package votingsystem.menuvote.service;


import votingsystem.menuvote.model.Menu;
import votingsystem.menuvote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface MenuService {

    Menu create(Menu menu);

    void delete(int id) throws NotFoundException;

    Menu get(int id) throws NotFoundException;

    void update(Menu menu);

    List<Menu> getBetween(LocalDate startDate, LocalDate endDate);

    List<Menu> getBetweenWithVotes(LocalDate startDate, LocalDate endDate);


}