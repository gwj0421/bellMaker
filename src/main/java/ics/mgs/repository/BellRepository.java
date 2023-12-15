package ics.mgs.repository;

import ics.mgs.dao.Bell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BellRepository extends JpaRepository<Bell, Long> {
}
