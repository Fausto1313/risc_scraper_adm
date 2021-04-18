package mx.com.risc.scraper.web.rest;

import mx.com.risc.scraper.RiscScraperAdmApp;

import mx.com.risc.scraper.domain.Portal;
import mx.com.risc.scraper.repository.PortalRepository;
import mx.com.risc.scraper.service.PortalService;
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
 * Test class for the PortalResource REST controller.
 *
 * @see PortalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RiscScraperAdmApp.class)
public class PortalResourceIntTest {

    private static final Integer DEFAULT_IDSCRAPY_DATA = 1;
    private static final Integer UPDATED_IDSCRAPY_DATA = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAIN = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_HEADER_PATH = "AAAAAAAAAA";
    private static final String UPDATED_HEADER_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_BODY_PATH = "AAAAAAAAAA";
    private static final String UPDATED_BODY_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_AUTOR_PATH = "AAAAAAAAAA";
    private static final String UPDATED_AUTOR_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_DATE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_RESUME_PATH = "AAAAAAAAAA";
    private static final String UPDATED_RESUME_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final Boolean DEFAULT_XPATH = false;
    private static final Boolean UPDATED_XPATH = true;

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FECHA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PortalRepository portalRepository;
    
    @Autowired
    private PortalService portalService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPortalMockMvc;

    private Portal portal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PortalResource portalResource = new PortalResource(portalService);
        this.restPortalMockMvc = MockMvcBuilders.standaloneSetup(portalResource)
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
    public static Portal createEntity(EntityManager em) {
        Portal portal = new Portal()
            .idscrapyData(DEFAULT_IDSCRAPY_DATA)
            .name(DEFAULT_NAME)
            .domain(DEFAULT_DOMAIN)
            .url(DEFAULT_URL)
            .headerPath(DEFAULT_HEADER_PATH)
            .bodyPath(DEFAULT_BODY_PATH)
            .autorPath(DEFAULT_AUTOR_PATH)
            .datePath(DEFAULT_DATE_PATH)
            .resumePath(DEFAULT_RESUME_PATH)
            .path(DEFAULT_PATH)
            .xpath(DEFAULT_XPATH)
            .identifier(DEFAULT_IDENTIFIER)
            .usuario(DEFAULT_USUARIO)
            .fecha(DEFAULT_FECHA);
        return portal;
    }

    @Before
    public void initTest() {
        portal = createEntity(em);
    }

    @Test
    @Transactional
    public void createPortal() throws Exception {
        int databaseSizeBeforeCreate = portalRepository.findAll().size();

        // Create the Portal
        restPortalMockMvc.perform(post("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portal)))
            .andExpect(status().isCreated());

        // Validate the Portal in the database
        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeCreate + 1);
        Portal testPortal = portalList.get(portalList.size() - 1);
        assertThat(testPortal.getIdscrapyData()).isEqualTo(DEFAULT_IDSCRAPY_DATA);
        assertThat(testPortal.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPortal.getDomain()).isEqualTo(DEFAULT_DOMAIN);
        assertThat(testPortal.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testPortal.getHeaderPath()).isEqualTo(DEFAULT_HEADER_PATH);
        assertThat(testPortal.getBodyPath()).isEqualTo(DEFAULT_BODY_PATH);
        assertThat(testPortal.getAutorPath()).isEqualTo(DEFAULT_AUTOR_PATH);
        assertThat(testPortal.getDatePath()).isEqualTo(DEFAULT_DATE_PATH);
        assertThat(testPortal.getResumePath()).isEqualTo(DEFAULT_RESUME_PATH);
        assertThat(testPortal.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testPortal.isXpath()).isEqualTo(DEFAULT_XPATH);
        assertThat(testPortal.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testPortal.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testPortal.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createPortalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = portalRepository.findAll().size();

        // Create the Portal with an existing ID
        portal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPortalMockMvc.perform(post("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portal)))
            .andExpect(status().isBadRequest());

        // Validate the Portal in the database
        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = portalRepository.findAll().size();
        // set the field null
        portal.setName(null);

        // Create the Portal, which fails.

        restPortalMockMvc.perform(post("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portal)))
            .andExpect(status().isBadRequest());

        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDomainIsRequired() throws Exception {
        int databaseSizeBeforeTest = portalRepository.findAll().size();
        // set the field null
        portal.setDomain(null);

        // Create the Portal, which fails.

        restPortalMockMvc.perform(post("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portal)))
            .andExpect(status().isBadRequest());

        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = portalRepository.findAll().size();
        // set the field null
        portal.setUrl(null);

        // Create the Portal, which fails.

        restPortalMockMvc.perform(post("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portal)))
            .andExpect(status().isBadRequest());

        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeaderPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = portalRepository.findAll().size();
        // set the field null
        portal.setHeaderPath(null);

        // Create the Portal, which fails.

        restPortalMockMvc.perform(post("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portal)))
            .andExpect(status().isBadRequest());

        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBodyPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = portalRepository.findAll().size();
        // set the field null
        portal.setBodyPath(null);

        // Create the Portal, which fails.

        restPortalMockMvc.perform(post("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portal)))
            .andExpect(status().isBadRequest());

        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAutorPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = portalRepository.findAll().size();
        // set the field null
        portal.setAutorPath(null);

        // Create the Portal, which fails.

        restPortalMockMvc.perform(post("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portal)))
            .andExpect(status().isBadRequest());

        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatePathIsRequired() throws Exception {
        int databaseSizeBeforeTest = portalRepository.findAll().size();
        // set the field null
        portal.setDatePath(null);

        // Create the Portal, which fails.

        restPortalMockMvc.perform(post("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portal)))
            .andExpect(status().isBadRequest());

        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResumePathIsRequired() throws Exception {
        int databaseSizeBeforeTest = portalRepository.findAll().size();
        // set the field null
        portal.setResumePath(null);

        // Create the Portal, which fails.

        restPortalMockMvc.perform(post("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portal)))
            .andExpect(status().isBadRequest());

        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = portalRepository.findAll().size();
        // set the field null
        portal.setPath(null);

        // Create the Portal, which fails.

        restPortalMockMvc.perform(post("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portal)))
            .andExpect(status().isBadRequest());

        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkXpathIsRequired() throws Exception {
        int databaseSizeBeforeTest = portalRepository.findAll().size();
        // set the field null
        portal.setXpath(null);

        // Create the Portal, which fails.

        restPortalMockMvc.perform(post("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portal)))
            .andExpect(status().isBadRequest());

        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPortals() throws Exception {
        // Initialize the database
        portalRepository.saveAndFlush(portal);

        // Get all the portalList
        restPortalMockMvc.perform(get("/api/portals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(portal.getId().intValue())))
            .andExpect(jsonPath("$.[*].idscrapyData").value(hasItem(DEFAULT_IDSCRAPY_DATA)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].headerPath").value(hasItem(DEFAULT_HEADER_PATH.toString())))
            .andExpect(jsonPath("$.[*].bodyPath").value(hasItem(DEFAULT_BODY_PATH.toString())))
            .andExpect(jsonPath("$.[*].autorPath").value(hasItem(DEFAULT_AUTOR_PATH.toString())))
            .andExpect(jsonPath("$.[*].datePath").value(hasItem(DEFAULT_DATE_PATH.toString())))
            .andExpect(jsonPath("$.[*].resumePath").value(hasItem(DEFAULT_RESUME_PATH.toString())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
            .andExpect(jsonPath("$.[*].xpath").value(hasItem(DEFAULT_XPATH.booleanValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(sameInstant(DEFAULT_FECHA))));
    }
    
    @Test
    @Transactional
    public void getPortal() throws Exception {
        // Initialize the database
        portalRepository.saveAndFlush(portal);

        // Get the portal
        restPortalMockMvc.perform(get("/api/portals/{id}", portal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(portal.getId().intValue()))
            .andExpect(jsonPath("$.idscrapyData").value(DEFAULT_IDSCRAPY_DATA))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.domain").value(DEFAULT_DOMAIN.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.headerPath").value(DEFAULT_HEADER_PATH.toString()))
            .andExpect(jsonPath("$.bodyPath").value(DEFAULT_BODY_PATH.toString()))
            .andExpect(jsonPath("$.autorPath").value(DEFAULT_AUTOR_PATH.toString()))
            .andExpect(jsonPath("$.datePath").value(DEFAULT_DATE_PATH.toString()))
            .andExpect(jsonPath("$.resumePath").value(DEFAULT_RESUME_PATH.toString()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()))
            .andExpect(jsonPath("$.xpath").value(DEFAULT_XPATH.booleanValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO.toString()))
            .andExpect(jsonPath("$.fecha").value(sameInstant(DEFAULT_FECHA)));
    }

    @Test
    @Transactional
    public void getNonExistingPortal() throws Exception {
        // Get the portal
        restPortalMockMvc.perform(get("/api/portals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePortal() throws Exception {
        // Initialize the database
        portalService.save(portal);

        int databaseSizeBeforeUpdate = portalRepository.findAll().size();

        // Update the portal
        Portal updatedPortal = portalRepository.findById(portal.getId()).get();
        // Disconnect from session so that the updates on updatedPortal are not directly saved in db
        em.detach(updatedPortal);
        updatedPortal
            .idscrapyData(UPDATED_IDSCRAPY_DATA)
            .name(UPDATED_NAME)
            .domain(UPDATED_DOMAIN)
            .url(UPDATED_URL)
            .headerPath(UPDATED_HEADER_PATH)
            .bodyPath(UPDATED_BODY_PATH)
            .autorPath(UPDATED_AUTOR_PATH)
            .datePath(UPDATED_DATE_PATH)
            .resumePath(UPDATED_RESUME_PATH)
            .path(UPDATED_PATH)
            .xpath(UPDATED_XPATH)
            .identifier(UPDATED_IDENTIFIER)
            .usuario(UPDATED_USUARIO)
            .fecha(UPDATED_FECHA);

        restPortalMockMvc.perform(put("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPortal)))
            .andExpect(status().isOk());

        // Validate the Portal in the database
        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeUpdate);
        Portal testPortal = portalList.get(portalList.size() - 1);
        assertThat(testPortal.getIdscrapyData()).isEqualTo(UPDATED_IDSCRAPY_DATA);
        assertThat(testPortal.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPortal.getDomain()).isEqualTo(UPDATED_DOMAIN);
        assertThat(testPortal.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testPortal.getHeaderPath()).isEqualTo(UPDATED_HEADER_PATH);
        assertThat(testPortal.getBodyPath()).isEqualTo(UPDATED_BODY_PATH);
        assertThat(testPortal.getAutorPath()).isEqualTo(UPDATED_AUTOR_PATH);
        assertThat(testPortal.getDatePath()).isEqualTo(UPDATED_DATE_PATH);
        assertThat(testPortal.getResumePath()).isEqualTo(UPDATED_RESUME_PATH);
        assertThat(testPortal.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testPortal.isXpath()).isEqualTo(UPDATED_XPATH);
        assertThat(testPortal.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testPortal.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testPortal.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingPortal() throws Exception {
        int databaseSizeBeforeUpdate = portalRepository.findAll().size();

        // Create the Portal

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPortalMockMvc.perform(put("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portal)))
            .andExpect(status().isBadRequest());

        // Validate the Portal in the database
        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePortal() throws Exception {
        // Initialize the database
        portalService.save(portal);

        int databaseSizeBeforeDelete = portalRepository.findAll().size();

        // Get the portal
        restPortalMockMvc.perform(delete("/api/portals/{id}", portal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Portal.class);
        Portal portal1 = new Portal();
        portal1.setId(1L);
        Portal portal2 = new Portal();
        portal2.setId(portal1.getId());
        assertThat(portal1).isEqualTo(portal2);
        portal2.setId(2L);
        assertThat(portal1).isNotEqualTo(portal2);
        portal1.setId(null);
        assertThat(portal1).isNotEqualTo(portal2);
    }
}
