package votingsystem.menuvote.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import votingsystem.menuvote.model.User;
import votingsystem.menuvote.model.Vote;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Vote u WHERE u.id=:id")
    int delete(@Param("id") int id);

    @Override
    @Transactional
    Vote save(Vote vote);

    Optional<Vote> getById(Integer id);

}
