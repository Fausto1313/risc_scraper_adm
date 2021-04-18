package mx.com.risc.scraper.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.com.risc.scraper.domain.PortalType;
import mx.com.risc.scraper.service.PortalTypeService;
import mx.com.risc.scraper.web.rest.errors.BadRequestAlertException;
import mx.com.risc.scraper.web.rest.util.HeaderUtil;
import mx.com.risc.scraper.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PortalType.
 */
@RestController
@RequestMapping("/api")
public class PortalTypeResource {

    private final Logger log = LoggerFactory.getLogger(PortalTypeResource.class);

    private static final String ENTITY_NAME = "portalType";

    private PortalTypeService portalTypeService;

    public PortalTypeResource(PortalTypeService portalTypeService) {
        this.portalTypeService = portalTypeService;
    }

    /**
     * POST  /portal-types : Create a new portalType.
     *
     * @param portalType the portalType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new portalType, or with status 400 (Bad Request) if the portalType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/portal-types")
    @Timed
    public ResponseEntity<PortalType> createPortalType(@Valid @RequestBody PortalType portalType) throws URISyntaxException {
        log.debug("REST request to save PortalType : {}", portalType);
        if (portalType.getId() != null) {
            throw new BadRequestAlertException("A new portalType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PortalType result = portalTypeService.save(portalType);
        return ResponseEntity.created(new URI("/api/portal-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /portal-types : Updates an existing portalType.
     *
     * @param portalType the portalType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated portalType,
     * or with status 400 (Bad Request) if the portalType is not valid,
     * or with status 500 (Internal Server Error) if the portalType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/portal-types")
    @Timed
    public ResponseEntity<PortalType> updatePortalType(@Valid @RequestBody PortalType portalType) throws URISyntaxException {
        log.debug("REST request to update PortalType : {}", portalType);
        if (portalType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PortalType result = portalTypeService.save(portalType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, portalType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /portal-types : get all the portalTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of portalTypes in body
     */
    @GetMapping("/portal-types")
    @Timed
    public ResponseEntity<List<PortalType>> getAllPortalTypes(Pageable pageable) {
        log.debug("REST request to get a page of PortalTypes");
        Page<PortalType> page = portalTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/portal-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /portal-types/:id : get the "id" portalType.
     *
     * @param id the id of the portalType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the portalType, or with status 404 (Not Found)
     */
    @GetMapping("/portal-types/{id}")
    @Timed
    public ResponseEntity<PortalType> getPortalType(@PathVariable Long id) {
        log.debug("REST request to get PortalType : {}", id);
        Optional<PortalType> portalType = portalTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(portalType);
    }

    /**
     * DELETE  /portal-types/:id : delete the "id" portalType.
     *
     * @param id the id of the portalType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/portal-types/{id}")
    @Timed
    public ResponseEntity<Void> deletePortalType(@PathVariable Long id) {
        log.debug("REST request to delete PortalType : {}", id);
        portalTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
