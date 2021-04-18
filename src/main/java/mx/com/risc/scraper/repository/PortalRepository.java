package mx.com.risc.scraper.repository;

import mx.com.risc.scraper.domain.Portal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Portal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PortalRepository extends JpaRepository<Portal, Long> {

}
