package mx.com.risc.scraper.service.impl;

import mx.com.risc.scraper.service.PagesService;
import mx.com.risc.scraper.domain.Pages;
import mx.com.risc.scraper.repository.PagesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Pages.
 */
@Service
@Transactional
public class PagesServiceImpl implements PagesService {

    private final Logger log = LoggerFactory.getLogger(PagesServiceImpl.class);

    private PagesRepository pagesRepository;

    public PagesServiceImpl(PagesRepository pagesRepository) {
        this.pagesRepository = pagesRepository;
    }

    /**
     * Save a pages.
     *
     * @param pages the entity to save
     * @return the persisted entity
     */
    @Override
    public Pages save(Pages pages) {
        log.debug("Request to save Pages : {}", pages);
        return pagesRepository.save(pages);
    }

    /**
     * Get all the pages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Pages> findAll(Pageable pageable) {
        log.debug("Request to get all Pages");
        return pagesRepository.findAll(pageable);
    }


    /**
     * Get one pages by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Pages> findOne(Long id) {
        log.debug("Request to get Pages : {}", id);
        return pagesRepository.findById(id);
    }

    /**
     * Delete the pages by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pages : {}", id);
        pagesRepository.deleteById(id);
    }
}
