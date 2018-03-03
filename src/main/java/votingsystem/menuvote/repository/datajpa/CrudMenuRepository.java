package votingsystem.menuvote.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import votingsystem.menuvote.model.Menu;
import votingsystem.menuvote.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMenuRepository extends JpaRepository<Menu, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id=:id")
    int delete(@Param("id") int id);

    @Override
    @Transactional
    Menu save(Menu menu);

    Optional<Menu> getById(Integer id);

    @Query("SELECT m FROM Menu m WHERE m.date BETWEEN :startDate AND :endDate ORDER BY m.date")
    List<Menu> getBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Override
    List<Menu> findAll(Sort sort);
}
