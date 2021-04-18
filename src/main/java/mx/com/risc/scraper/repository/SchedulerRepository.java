package mx.com.risc.scraper.repository;

import mx.com.risc.scraper.domain.Scheduler;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Scheduler entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchedulerRepository extends JpaRepository<Scheduler, Long> {
	
	Page<Scheduler> findAllByJobidentifier(String jobidentifier, Pageable pageable);

}
