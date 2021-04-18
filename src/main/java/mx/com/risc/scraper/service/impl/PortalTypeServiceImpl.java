package mx.com.risc.scraper.service.impl;

import mx.com.risc.scraper.service.PortalTypeService;
import mx.com.risc.scraper.domain.PortalType;
import mx.com.risc.scraper.repository.PortalTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing PortalType.
 */
@Service
@Transactional
public class PortalTypeServiceImpl implements PortalTypeService {

    private final Logger log = LoggerFactory.getLogger(PortalTypeServiceImpl.class);

    private PortalTypeRepository portalTypeRepository;

    public PortalTypeServiceImpl(PortalTypeRepository portalTypeRepository) {
        this.portalTypeRepository = portalTypeRepository;
    }

    /**
     * Save a portalType.
     *
     * @param portalType the entity to save
     * @return the persisted entity
     */
    @Override
    public PortalType save(PortalType portalType) {
        log.debug("Request to save PortalType : {}", portalType);
        return portalTypeRepository.save(portalType);
    }

    /**
     * Get all the portalTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PortalType> findAll(Pageable pageable) {
        log.debug("Request to get all PortalTypes");
        return portalTypeRepository.findAll(pageable);
    }


    /**
     * Get one portalType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PortalType> findOne(Long id) {
        log.debug("Request to get PortalType : {}", id);
        return portalTypeRepository.findById(id);
    }

    /**
     * Delete the portalType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PortalType : {}", id);
        portalTypeRepository.deleteById(id);
    }
}
