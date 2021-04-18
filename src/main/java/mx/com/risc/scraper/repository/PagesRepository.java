package mx.com.risc.scraper.repository;

import mx.com.risc.scraper.domain.Pages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PagesRepository extends JpaRepository<Pages, Long> {

}
