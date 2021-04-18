package mx.com.risc.scraper.web.rest;

import mx.com.risc.scraper.RiscScraperAdmApp;

import mx.com.risc.scraper.domain.PortalType;
import mx.com.risc.scraper.repository.PortalTypeRepository;
import mx.com.risc.scraper.service.PortalTypeService;
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
import java.util.List;


import static mx.com.risc.scraper.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PortalTypeResource REST controller.
 *
 * @see PortalTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RiscScraperAdmApp.class)
public class PortalTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PortalTypeRepository portalTypeRepository;
    
    @Autowired
    private PortalTypeService portalTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPortalTypeMockMvc;

    private PortalType portalType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PortalTypeResource portalTypeResource = new PortalTypeResource(portalTypeService);
        this.restPortalTypeMockMvc = MockMvcBuilders.standaloneSetup(portalTypeResource)
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
    public static PortalType createEntity(EntityManager em) {
        PortalType portalType = new PortalType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return portalType;
    }

    @Before
    public void initTest() {
        portalType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPortalType() throws Exception {
        int databaseSizeBeforeCreate = portalTypeRepository.findAll().size();

        // Create the PortalType
        restPortalTypeMockMvc.perform(post("/api/portal-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portalType)))
            .andExpect(status().isCreated());

        // Validate the PortalType in the database
        List<PortalType> portalTypeList = portalTypeRepository.findAll();
        assertThat(portalTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PortalType testPortalType = portalTypeList.get(portalTypeList.size() - 1);
        assertThat(testPortalType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPortalType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPortalTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = portalTypeRepository.findAll().size();

        // Create the PortalType with an existing ID
        portalType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPortalTypeMockMvc.perform(post("/api/portal-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portalType)))
            .andExpect(status().isBadRequest());

        // Validate the PortalType in the database
        List<PortalType> portalTypeList = portalTypeRepository.findAll();
        assertThat(portalTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = portalTypeRepository.findAll().size();
        // set the field null
        portalType.setName(null);

        // Create the PortalType, which fails.

        restPortalTypeMockMvc.perform(post("/api/portal-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portalType)))
            .andExpect(status().isBadRequest());

        List<PortalType> portalTypeList = portalTypeRepository.findAll();
        assertThat(portalTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = portalTypeRepository.findAll().size();
        // set the field null
        portalType.setDescription(null);

        // Create the PortalType, which fails.

        restPortalTypeMockMvc.perform(post("/api/portal-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portalType)))
            .andExpect(status().isBadRequest());

        List<PortalType> portalTypeList = portalTypeRepository.findAll();
        assertThat(portalTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPortalTypes() throws Exception {
        // Initialize the database
        portalTypeRepository.saveAndFlush(portalType);

        // Get all the portalTypeList
        restPortalTypeMockMvc.perform(get("/api/portal-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(portalType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getPortalType() throws Exception {
        // Initialize the database
        portalTypeRepository.saveAndFlush(portalType);

        // Get the portalType
        restPortalTypeMockMvc.perform(get("/api/portal-types/{id}", portalType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(portalType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPortalType() throws Exception {
        // Get the portalType
        restPortalTypeMockMvc.perform(get("/api/portal-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePortalType() throws Exception {
        // Initialize the database
        portalTypeService.save(portalType);

        int databaseSizeBeforeUpdate = portalTypeRepository.findAll().size();

        // Update the portalType
        PortalType updatedPortalType = portalTypeRepository.findById(portalType.getId()).get();
        // Disconnect from session so that the updates on updatedPortalType are not directly saved in db
        em.detach(updatedPortalType);
        updatedPortalType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restPortalTypeMockMvc.perform(put("/api/portal-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPortalType)))
            .andExpect(status().isOk());

        // Validate the PortalType in the database
        List<PortalType> portalTypeList = portalTypeRepository.findAll();
        assertThat(portalTypeList).hasSize(databaseSizeBeforeUpdate);
        PortalType testPortalType = portalTypeList.get(portalTypeList.size() - 1);
        assertThat(testPortalType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPortalType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPortalType() throws Exception {
        int databaseSizeBeforeUpdate = portalTypeRepository.findAll().size();

        // Create the PortalType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPortalTypeMockMvc.perform(put("/api/portal-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portalType)))
            .andExpect(status().isBadRequest());

        // Validate the PortalType in the database
        List<PortalType> portalTypeList = portalTypeRepository.findAll();
        assertThat(portalTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePortalType() throws Exception {
        // Initialize the database
        portalTypeService.save(portalType);

        int databaseSizeBeforeDelete = portalTypeRepository.findAll().size();

        // Get the portalType
        restPortalTypeMockMvc.perform(delete("/api/portal-types/{id}", portalType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PortalType> portalTypeList = portalTypeRepository.findAll();
        assertThat(portalTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PortalType.class);
        PortalType portalType1 = new PortalType();
        portalType1.setId(1L);
        PortalType portalType2 = new PortalType();
        portalType2.setId(portalType1.getId());
        assertThat(portalType1).isEqualTo(portalType2);
        portalType2.setId(2L);
        assertThat(portalType1).isNotEqualTo(portalType2);
        portalType1.setId(null);
        assertThat(portalType1).isNotEqualTo(portalType2);
    }
}
