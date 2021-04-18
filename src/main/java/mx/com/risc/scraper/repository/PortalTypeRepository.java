package mx.com.risc.scraper.repository;

import mx.com.risc.scraper.domain.PortalType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PortalType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PortalTypeRepository extends JpaRepository<PortalType, Long> {

}
