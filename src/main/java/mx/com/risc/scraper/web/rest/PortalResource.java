package mx.com.risc.scraper.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.com.risc.scraper.domain.Portal;
import mx.com.risc.scraper.security.SecurityUtils;
import mx.com.risc.scraper.service.PortalService;
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Portal.
 */
@RestController
@RequestMapping("/api")
public class PortalResource {

    private final Logger log = LoggerFactory.getLogger(PortalResource.class);

    private static final String ENTITY_NAME = "portal";

    private PortalService portalService;

    public PortalResource(PortalService portalService) {
        this.portalService = portalService;
    }

    /**
     * POST  /portals : Create a new portal.
     *
     * @param portal the portal to create
     * @return the ResponseEntity with status 201 (Created) and with body the new portal, or with status 400 (Bad Request) if the portal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/portals")
    @Timed
    public ResponseEntity<Portal> createPortal(@Valid @RequestBody Portal portal) throws URISyntaxException {
        log.debug("REST request to save Portal : {}", portal);
        portal.setUsuario(SecurityUtils.getCurrentUserLogin().get());
        ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.of("America/Mexico_City"));        
        portal.setFecha(zonedDateTimeNow);
        if (portal.getId() != null) {
            throw new BadRequestAlertException("A new portal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Portal result = portalService.save(portal);
        return ResponseEntity.created(new URI("/api/portals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /portals : Updates an existing portal.
     *
     * @param portal the portal to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated portal,
     * or with status 400 (Bad Request) if the portal is not valid,
     * or with status 500 (Internal Server Error) if the portal couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/portals")
    @Timed
    public ResponseEntity<Portal> updatePortal(@Valid @RequestBody Portal portal) throws URISyntaxException {
        log.debug("REST request to update Portal : {}", portal);
        if (portal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.of("America/Mexico_City"));        
        portal.setFecha(zonedDateTimeNow);
        portal.setUsuario(SecurityUtils.getCurrentUserLogin().get());
        Portal result = portalService.save(portal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, portal.getId().toString()))
            .body(result);
    }

    /**
     * GET  /portals : get all the portals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of portals in body
     */
    @GetMapping("/portals")
    @Timed
    public ResponseEntity<List<Portal>> getAllPortals(Pageable pageable) {
        log.debug("REST request to get a page of Portals");
        Page<Portal> page = portalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/portals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /portals/:id : get the "id" portal.
     *
     * @param id the id of the portal to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the portal, or with status 404 (Not Found)
     */
    @GetMapping("/portals/{id}")
    @Timed
    public ResponseEntity<Portal> getPortal(@PathVariable Long id) {
        log.debug("REST request to get Portal : {}", id);
        Optional<Portal> portal = portalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(portal);
    }

    /**
     * DELETE  /portals/:id : delete the "id" portal.
     *
     * @param id the id of the portal to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/portals/{id}")
    @Timed
    public ResponseEntity<Void> deletePortal(@PathVariable Long id) {
        log.debug("REST request to delete Portal : {}", id);
        portalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
