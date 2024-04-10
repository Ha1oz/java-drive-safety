package net.haloz.test.repo;

import net.haloz.test.entity.MagnetometerTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagnetometerTestRepo extends JpaRepository<MagnetometerTest, Long> {
    MagnetometerTest getMagnetometerExperimentsById(long id);
}
