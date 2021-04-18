package mx.com.risc.scraper.service;

import mx.com.risc.scraper.domain.PortalType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing PortalType.
 */
public interface PortalTypeService {

    /**
     * Save a portalType.
     *
     * @param portalType the entity to save
     * @return the persisted entity
     */
    PortalType save(PortalType portalType);

    /**
     * Get all the portalTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PortalType> findAll(Pageable pageable);


    /**
     * Get the "id" portalType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PortalType> findOne(Long id);

    /**
     * Delete the "id" portalType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
