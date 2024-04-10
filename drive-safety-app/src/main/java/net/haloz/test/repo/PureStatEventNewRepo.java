package net.haloz.test.repo;

import net.haloz.test.entity.PureStatEventNew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PureStatEventNewRepo extends JpaRepository<PureStatEventNew, Long> {
    PureStatEventNew getPureStatEventNewByDatetime(Long datetime);
    List<PureStatEventNew> getPureStatEventNewsByDatetimeBetweenAndUseridOrderByDatetimeAsc(Long start, Long end, Long userid);
    List<PureStatEventNew> getPureStatEventNewsByDatetimeBetweenAndUserid(Long start, Long end, Long userid);
    PureStatEventNew getPureStatEventNewByIdStatEvent(Long idStatEvent);

}
