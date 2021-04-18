package mx.com.risc.scraper.service.impl;

import mx.com.risc.scraper.service.PortalService;
import mx.com.risc.scraper.domain.Portal;
import mx.com.risc.scraper.repository.PortalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Portal.
 */
@Service
@Transactional
public class PortalServiceImpl implements PortalService {

    private final Logger log = LoggerFactory.getLogger(PortalServiceImpl.class);

    private PortalRepository portalRepository;

    public PortalServiceImpl(PortalRepository portalRepository) {
        this.portalRepository = portalRepository;
    }

    /**
     * Save a portal.
     *
     * @param portal the entity to save
     * @return the persisted entity
     */
    @Override
    public Portal save(Portal portal) {
        log.debug("Request to save Portal : {}", portal);
        return portalRepository.save(portal);
    }

    /**
     * Get all the portals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Portal> findAll(Pageable pageable) {
        log.debug("Request to get all Portals");
        return portalRepository.findAll(pageable);
    }


    /**
     * Get one portal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Portal> findOne(Long id) {
        log.debug("Request to get Portal : {}", id);
        return portalRepository.findById(id);
    }

    /**
     * Delete the portal by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Portal : {}", id);
        portalRepository.deleteById(id);
    }
}
