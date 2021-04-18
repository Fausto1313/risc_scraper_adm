package mx.com.risc.scraper.service;

import mx.com.risc.scraper.domain.Scheduler;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Scheduler.
 */
public interface SchedulerService {

    /**
     * Save a scheduler.
     *
     * @param scheduler the entity to save
     * @return the persisted entity
     */
    Scheduler save(Scheduler scheduler);

    /**
     * Get all the schedulers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Scheduler> findAll(Pageable pageable);


    /**
     * Get the "id" scheduler.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Scheduler> findOne(Long id);

    /**
     * Delete the "id" scheduler.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
