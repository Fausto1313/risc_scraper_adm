package mx.com.risc.scraper.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.com.risc.scraper.domain.Scheduler;
import mx.com.risc.scraper.service.SchedulerService;
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
 * REST controller for managing Scheduler.
 */
@RestController
@RequestMapping("/api")
public class SchedulerResource {

    private final Logger log = LoggerFactory.getLogger(SchedulerResource.class);

    private static final String ENTITY_NAME = "scheduler";

    private SchedulerService schedulerService;

    public SchedulerResource(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    /**
     * POST  /schedulers : Create a new scheduler.
     *
     * @param scheduler the scheduler to create
     * @return the ResponseEntity with status 201 (Created) and with body the new scheduler, or with status 400 (Bad Request) if the scheduler has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/schedulers")
    @Timed
    public ResponseEntity<Scheduler> createScheduler(@Valid @RequestBody Scheduler scheduler) throws URISyntaxException {
        log.debug("REST request to save Scheduler : {}", scheduler);
        if (scheduler.getId() != null) {
            throw new BadRequestAlertException("A new scheduler cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Scheduler result = schedulerService.save(scheduler);
        return ResponseEntity.created(new URI("/api/schedulers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /schedulers : Updates an existing scheduler.
     *
     * @param scheduler the scheduler to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated scheduler,
     * or with status 400 (Bad Request) if the scheduler is not valid,
     * or with status 500 (Internal Server Error) if the scheduler couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/schedulers")
    @Timed
    public ResponseEntity<Scheduler> updateScheduler(@Valid @RequestBody Scheduler scheduler) throws URISyntaxException {
        log.debug("REST request to update Scheduler : {}", scheduler);
        if (scheduler.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Scheduler result = schedulerService.save(scheduler);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, scheduler.getId().toString()))
            .body(result);
    }

    /**
     * GET  /schedulers : get all the schedulers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of schedulers in body
     */
    @GetMapping("/schedulers")
    @Timed
    public ResponseEntity<List<Scheduler>> getAllSchedulers(Pageable pageable) {
        log.debug("REST request to get a page of Schedulers");
        Page<Scheduler> page = schedulerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/schedulers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /schedulers/:id : get the "id" scheduler.
     *
     * @param id the id of the scheduler to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the scheduler, or with status 404 (Not Found)
     */
    @GetMapping("/schedulers/{id}")
    @Timed
    public ResponseEntity<Scheduler> getScheduler(@PathVariable Long id) {
        log.debug("REST request to get Scheduler : {}", id);
        Optional<Scheduler> scheduler = schedulerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scheduler);
    }

    /**
     * DELETE  /schedulers/:id : delete the "id" scheduler.
     *
     * @param id the id of the scheduler to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/schedulers/{id}")
    @Timed
    public ResponseEntity<Void> deleteScheduler(@PathVariable Long id) {
        log.debug("REST request to delete Scheduler : {}", id);
        schedulerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
