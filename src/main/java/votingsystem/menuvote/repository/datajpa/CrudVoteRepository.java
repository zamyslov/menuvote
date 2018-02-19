package votingsystem.menuvote.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import votingsystem.menuvote.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.date=:date AND v.user.id=:user_id")
    int delete(@Param("date") LocalDate date, @Param("user_id") int user_id);

    @Query("SELECT v FROM Vote v")
    List<Vote> getAll();

    @Override
    @Transactional
    Vote save(Vote vote);

    Vote getByDateAndUser(LocalDate date, int user_id);

}
