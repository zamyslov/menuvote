package votingsystem.menuvote.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import votingsystem.menuvote.model.Menu;
import votingsystem.menuvote.model.MenuDishes;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMenuDishesRepository extends JpaRepository<MenuDishes, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Menu u WHERE u.id=:id")
    int delete(@Param("id") int id);

    @Override
    @Transactional
    MenuDishes save(MenuDishes menu);

    Optional<MenuDishes> getById(Integer id);

    @Override
    List<MenuDishes> findAll(Sort sort);

}
