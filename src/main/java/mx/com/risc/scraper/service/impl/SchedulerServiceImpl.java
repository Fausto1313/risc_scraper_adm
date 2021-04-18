package mx.com.risc.scraper.service.impl;

import mx.com.risc.scraper.service.SchedulerService;
import mx.com.risc.scraper.domain.Scheduler;
import mx.com.risc.scraper.repository.SchedulerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Scheduler.
 */
@Service
@Transactional
public class SchedulerServiceImpl implements SchedulerService {

    private final Logger log = LoggerFactory.getLogger(SchedulerServiceImpl.class);

    private SchedulerRepository schedulerRepository;

    public SchedulerServiceImpl(SchedulerRepository schedulerRepository) {
        this.schedulerRepository = schedulerRepository;
    }

    /**
     * Save a scheduler.
     *
     * @param scheduler the entity to save
     * @return the persisted entity
     */
    @Override
    public Scheduler save(Scheduler scheduler) {
        log.debug("Request to save Scheduler : {}", scheduler);
        return schedulerRepository.save(scheduler);
    }

    /**
     * Get all the schedulers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Scheduler> findAll(Pageable pageable) {
        log.debug("Request to get all Schedulers");
        return schedulerRepository.findAllByJobidentifier("1", pageable);
    }


    /**
     * Get one scheduler by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Scheduler> findOne(Long id) {
        log.debug("Request to get Scheduler : {}", id);
        return schedulerRepository.findById(id);
    }

    /**
     * Delete the scheduler by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Scheduler : {}", id);
        schedulerRepository.deleteById(id);
    }
}
