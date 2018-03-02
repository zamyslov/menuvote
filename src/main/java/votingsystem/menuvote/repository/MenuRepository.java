package votingsystem.menuvote.repository;

import votingsystem.menuvote.model.Menu;

import java.time.LocalDate;
import java.util.List;

public interface MenuRepository {
    Menu save(Menu menu);

    // false if not found
    boolean delete(int id);

    // null if not found
    Menu get(int id);

    List<Menu> getBetween(LocalDate startDate, LocalDate endDate);

    List<Menu> getAll();

}