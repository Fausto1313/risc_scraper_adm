package mx.com.risc.scraper.service;

import mx.com.risc.scraper.domain.Pages;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Pages.
 */
public interface PagesService {

    /**
     * Save a pages.
     *
     * @param pages the entity to save
     * @return the persisted entity
     */
    Pages save(Pages pages);

    /**
     * Get all the pages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Pages> findAll(Pageable pageable);


    /**
     * Get the "id" pages.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Pages> findOne(Long id);

    /**
     * Delete the "id" pages.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
