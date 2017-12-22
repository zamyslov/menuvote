package votingsystem.menuvote.repository;

import votingsystem.menuvote.model.Menu;

import java.util.List;

public interface MenuRepository {
    Menu save(Menu menu);

    // false if not found
    boolean delete(int id);

    // null if not found
    Menu get(int id);

    List<Menu> getAll();
}