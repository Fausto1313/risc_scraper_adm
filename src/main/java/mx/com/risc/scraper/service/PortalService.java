package mx.com.risc.scraper.service;

import mx.com.risc.scraper.domain.Portal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Portal.
 */
public interface PortalService {

    /**
     * Save a portal.
     *
     * @param portal the entity to save
     * @return the persisted entity
     */
    Portal save(Portal portal);

    /**
     * Get all the portals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Portal> findAll(Pageable pageable);


    /**
     * Get the "id" portal.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Portal> findOne(Long id);

    /**
     * Delete the "id" portal.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
