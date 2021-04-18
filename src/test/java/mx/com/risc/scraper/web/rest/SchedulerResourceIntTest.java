package mx.com.risc.scraper.web.rest;

import mx.com.risc.scraper.RiscScraperAdmApp;

import mx.com.risc.scraper.domain.Scheduler;
import mx.com.risc.scraper.repository.SchedulerRepository;
import mx.com.risc.scraper.service.SchedulerService;
import mx.com.risc.scraper.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static mx.com.risc.scraper.web.rest.TestUtil.sameInstant;
import static mx.com.risc.scraper.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SchedulerResource REST controller.
 *
 * @see SchedulerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RiscScraperAdmApp.class)
public class SchedulerResourceIntTest {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Integer DEFAULT_ACTOR = 1;
    private static final Integer UPDATED_ACTOR = 2;

    private static final String DEFAULT_SCHEDULE = "AAAAAAAAAA";
    private static final String UPDATED_SCHEDULE = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT = "BBBBBBBBBB";

    private static final String DEFAULT_SPIDER = "AAAAAAAAAA";
    private static final String UPDATED_SPIDER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final String DEFAULT_JOBIDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_JOBIDENTIFIER = "BBBBBBBBBB";

    @Autowired
    private SchedulerRepository schedulerRepository;
    
    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSchedulerMockMvc;

    private Scheduler scheduler;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SchedulerResource schedulerResource = new SchedulerResource(schedulerService);
        this.restSchedulerMockMvc = MockMvcBuilders.standaloneSetup(schedulerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scheduler createEntity(EntityManager em) {
        Scheduler scheduler = new Scheduler()
            .identifier(DEFAULT_IDENTIFIER)
            .status(DEFAULT_STATUS)
            .actor(DEFAULT_ACTOR)
            .schedule(DEFAULT_SCHEDULE)
            .project(DEFAULT_PROJECT)
            .spider(DEFAULT_SPIDER)
            .timestamp(DEFAULT_TIMESTAMP)
            .duration(DEFAULT_DURATION)
            .jobidentifier(DEFAULT_JOBIDENTIFIER);
        return scheduler;
    }

    @Before
    public void initTest() {
        scheduler = createEntity(em);
    }

    @Test
    @Transactional
    public void createScheduler() throws Exception {
        int databaseSizeBeforeCreate = schedulerRepository.findAll().size();

        // Create the Scheduler
        restSchedulerMockMvc.perform(post("/api/schedulers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduler)))
            .andExpect(status().isCreated());

        // Validate the Scheduler in the database
        List<Scheduler> schedulerList = schedulerRepository.findAll();
        assertThat(schedulerList).hasSize(databaseSizeBeforeCreate + 1);
        Scheduler testScheduler = schedulerList.get(schedulerList.size() - 1);
        assertThat(testScheduler.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testScheduler.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testScheduler.getActor()).isEqualTo(DEFAULT_ACTOR);
        assertThat(testScheduler.getSchedule()).isEqualTo(DEFAULT_SCHEDULE);
        assertThat(testScheduler.getProject()).isEqualTo(DEFAULT_PROJECT);
        assertThat(testScheduler.getSpider()).isEqualTo(DEFAULT_SPIDER);
        assertThat(testScheduler.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testScheduler.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testScheduler.getJobidentifier()).isEqualTo(DEFAULT_JOBIDENTIFIER);
    }

    @Test
    @Transactional
    public void createSchedulerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schedulerRepository.findAll().size();

        // Create the Scheduler with an existing ID
        scheduler.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchedulerMockMvc.perform(post("/api/schedulers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduler)))
            .andExpect(status().isBadRequest());

        // Validate the Scheduler in the database
        List<Scheduler> schedulerList = schedulerRepository.findAll();
        assertThat(schedulerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = schedulerRepository.findAll().size();
        // set the field null
        scheduler.setIdentifier(null);

        // Create the Scheduler, which fails.

        restSchedulerMockMvc.perform(post("/api/schedulers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduler)))
            .andExpect(status().isBadRequest());

        List<Scheduler> schedulerList = schedulerRepository.findAll();
        assertThat(schedulerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = schedulerRepository.findAll().size();
        // set the field null
        scheduler.setStatus(null);

        // Create the Scheduler, which fails.

        restSchedulerMockMvc.perform(post("/api/schedulers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduler)))
            .andExpect(status().isBadRequest());

        List<Scheduler> schedulerList = schedulerRepository.findAll();
        assertThat(schedulerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActorIsRequired() throws Exception {
        int databaseSizeBeforeTest = schedulerRepository.findAll().size();
        // set the field null
        scheduler.setActor(null);

        // Create the Scheduler, which fails.

        restSchedulerMockMvc.perform(post("/api/schedulers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduler)))
            .andExpect(status().isBadRequest());

        List<Scheduler> schedulerList = schedulerRepository.findAll();
        assertThat(schedulerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProjectIsRequired() throws Exception {
        int databaseSizeBeforeTest = schedulerRepository.findAll().size();
        // set the field null
        scheduler.setProject(null);

        // Create the Scheduler, which fails.

        restSchedulerMockMvc.perform(post("/api/schedulers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduler)))
            .andExpect(status().isBadRequest());

        List<Scheduler> schedulerList = schedulerRepository.findAll();
        assertThat(schedulerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSpiderIsRequired() throws Exception {
        int databaseSizeBeforeTest = schedulerRepository.findAll().size();
        // set the field null
        scheduler.setSpider(null);

        // Create the Scheduler, which fails.

        restSchedulerMockMvc.perform(post("/api/schedulers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduler)))
            .andExpect(status().isBadRequest());

        List<Scheduler> schedulerList = schedulerRepository.findAll();
        assertThat(schedulerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = schedulerRepository.findAll().size();
        // set the field null
        scheduler.setTimestamp(null);

        // Create the Scheduler, which fails.

        restSchedulerMockMvc.perform(post("/api/schedulers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduler)))
            .andExpect(status().isBadRequest());

        List<Scheduler> schedulerList = schedulerRepository.findAll();
        assertThat(schedulerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchedulers() throws Exception {
        // Initialize the database
        schedulerRepository.saveAndFlush(scheduler);

        // Get all the schedulerList
        restSchedulerMockMvc.perform(get("/api/schedulers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scheduler.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].actor").value(hasItem(DEFAULT_ACTOR)))
            .andExpect(jsonPath("$.[*].schedule").value(hasItem(DEFAULT_SCHEDULE.toString())))
            .andExpect(jsonPath("$.[*].project").value(hasItem(DEFAULT_PROJECT.toString())))
            .andExpect(jsonPath("$.[*].spider").value(hasItem(DEFAULT_SPIDER.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].jobidentifier").value(hasItem(DEFAULT_JOBIDENTIFIER.toString())));
    }
    
    @Test
    @Transactional
    public void getScheduler() throws Exception {
        // Initialize the database
        schedulerRepository.saveAndFlush(scheduler);

        // Get the scheduler
        restSchedulerMockMvc.perform(get("/api/schedulers/{id}", scheduler.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(scheduler.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.actor").value(DEFAULT_ACTOR))
            .andExpect(jsonPath("$.schedule").value(DEFAULT_SCHEDULE.toString()))
            .andExpect(jsonPath("$.project").value(DEFAULT_PROJECT.toString()))
            .andExpect(jsonPath("$.spider").value(DEFAULT_SPIDER.toString()))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.jobidentifier").value(DEFAULT_JOBIDENTIFIER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingScheduler() throws Exception {
        // Get the scheduler
        restSchedulerMockMvc.perform(get("/api/schedulers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScheduler() throws Exception {
        // Initialize the database
        schedulerService.save(scheduler);

        int databaseSizeBeforeUpdate = schedulerRepository.findAll().size();

        // Update the scheduler
        Scheduler updatedScheduler = schedulerRepository.findById(scheduler.getId()).get();
        // Disconnect from session so that the updates on updatedScheduler are not directly saved in db
        em.detach(updatedScheduler);
        updatedScheduler
            .identifier(UPDATED_IDENTIFIER)
            .status(UPDATED_STATUS)
            .actor(UPDATED_ACTOR)
            .schedule(UPDATED_SCHEDULE)
            .project(UPDATED_PROJECT)
            .spider(UPDATED_SPIDER)
            .timestamp(UPDATED_TIMESTAMP)
            .duration(UPDATED_DURATION)
            .jobidentifier(UPDATED_JOBIDENTIFIER);

        restSchedulerMockMvc.perform(put("/api/schedulers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedScheduler)))
            .andExpect(status().isOk());

        // Validate the Scheduler in the database
        List<Scheduler> schedulerList = schedulerRepository.findAll();
        assertThat(schedulerList).hasSize(databaseSizeBeforeUpdate);
        Scheduler testScheduler = schedulerList.get(schedulerList.size() - 1);
        assertThat(testScheduler.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testScheduler.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testScheduler.getActor()).isEqualTo(UPDATED_ACTOR);
        assertThat(testScheduler.getSchedule()).isEqualTo(UPDATED_SCHEDULE);
        assertThat(testScheduler.getProject()).isEqualTo(UPDATED_PROJECT);
        assertThat(testScheduler.getSpider()).isEqualTo(UPDATED_SPIDER);
        assertThat(testScheduler.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testScheduler.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testScheduler.getJobidentifier()).isEqualTo(UPDATED_JOBIDENTIFIER);
    }

    @Test
    @Transactional
    public void updateNonExistingScheduler() throws Exception {
        int databaseSizeBeforeUpdate = schedulerRepository.findAll().size();

        // Create the Scheduler

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchedulerMockMvc.perform(put("/api/schedulers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduler)))
            .andExpect(status().isBadRequest());

        // Validate the Scheduler in the database
        List<Scheduler> schedulerList = schedulerRepository.findAll();
        assertThat(schedulerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScheduler() throws Exception {
        // Initialize the database
        schedulerService.save(scheduler);

        int databaseSizeBeforeDelete = schedulerRepository.findAll().size();

        // Get the scheduler
        restSchedulerMockMvc.perform(delete("/api/schedulers/{id}", scheduler.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Scheduler> schedulerList = schedulerRepository.findAll();
        assertThat(schedulerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Scheduler.class);
        Scheduler scheduler1 = new Scheduler();
        scheduler1.setId(1L);
        Scheduler scheduler2 = new Scheduler();
        scheduler2.setId(scheduler1.getId());
        assertThat(scheduler1).isEqualTo(scheduler2);
        scheduler2.setId(2L);
        assertThat(scheduler1).isNotEqualTo(scheduler2);
        scheduler1.setId(null);
        assertThat(scheduler1).isNotEqualTo(scheduler2);
    }
}
