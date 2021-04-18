package mx.com.risc.scraper.web.rest;

import mx.com.risc.scraper.RiscScraperAdmApp;

import mx.com.risc.scraper.domain.Pages;
import mx.com.risc.scraper.repository.PagesRepository;
import mx.com.risc.scraper.service.PagesService;
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
import org.springframework.util.Base64Utils;

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
 * Test class for the PagesResource REST controller.
 *
 * @see PagesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RiscScraperAdmApp.class)
public class PagesResourceIntTest {

    private static final Integer DEFAULT_IDSCRAPY_DATA = 1;
    private static final Integer UPDATED_IDSCRAPY_DATA = 2;

    private static final String DEFAULT_HEADER_DATA = "AAAAAAAAAA";
    private static final String UPDATED_HEADER_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_BODY_DATA = "AAAAAAAAAA";
    private static final String UPDATED_BODY_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_AUTOR_DATA = "AAAAAAAAAA";
    private static final String UPDATED_AUTOR_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATE_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_HEADER_CLEAN = "AAAAAAAAAA";
    private static final String UPDATED_HEADER_CLEAN = "BBBBBBBBBB";

    private static final String DEFAULT_BODY_CLEAN = "AAAAAAAAAA";
    private static final String UPDATED_BODY_CLEAN = "BBBBBBBBBB";

    private static final String DEFAULT_AUTOR_CLEAN = "AAAAAAAAAA";
    private static final String UPDATED_AUTOR_CLEAN = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_CLEAN = "AAAAAAAAAA";
    private static final String UPDATED_DATE_CLEAN = "BBBBBBBBBB";

    private static final String DEFAULT_RESUME_CLEAN = "AAAAAAAAAA";
    private static final String UPDATED_RESUME_CLEAN = "BBBBBBBBBB";

    private static final String DEFAULT_RESUME_DATA = "AAAAAAAAAA";
    private static final String UPDATED_RESUME_DATA = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SCREENSHOT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SCREENSHOT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SCREENSHOT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SCREENSHOT_CONTENT_TYPE = "image/png";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_FULL_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FULL_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private PagesRepository pagesRepository;
    
    @Autowired
    private PagesService pagesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPagesMockMvc;

    private Pages pages;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PagesResource pagesResource = new PagesResource(pagesService);
        this.restPagesMockMvc = MockMvcBuilders.standaloneSetup(pagesResource)
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
    public static Pages createEntity(EntityManager em) {
        Pages pages = new Pages()
            .idscrapyData(DEFAULT_IDSCRAPY_DATA)
            .headerData(DEFAULT_HEADER_DATA)
            .bodyData(DEFAULT_BODY_DATA)
            .autorData(DEFAULT_AUTOR_DATA)
            .dateData(DEFAULT_DATE_DATA)
            .headerClean(DEFAULT_HEADER_CLEAN)
            .bodyClean(DEFAULT_BODY_CLEAN)
            .autorClean(DEFAULT_AUTOR_CLEAN)
            .dateClean(DEFAULT_DATE_CLEAN)
            .resumeClean(DEFAULT_RESUME_CLEAN)
            .resumeData(DEFAULT_RESUME_DATA)
            .screenshot(DEFAULT_SCREENSHOT)
            .screenshotContentType(DEFAULT_SCREENSHOT_CONTENT_TYPE)
            .date(DEFAULT_DATE)
            .fullPath(DEFAULT_FULL_PATH)
            .url(DEFAULT_URL);
        return pages;
    }

    @Before
    public void initTest() {
        pages = createEntity(em);
    }

    @Test
    @Transactional
    public void createPages() throws Exception {
        int databaseSizeBeforeCreate = pagesRepository.findAll().size();

        // Create the Pages
        restPagesMockMvc.perform(post("/api/pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pages)))
            .andExpect(status().isCreated());

        // Validate the Pages in the database
        List<Pages> pagesList = pagesRepository.findAll();
        assertThat(pagesList).hasSize(databaseSizeBeforeCreate + 1);
        Pages testPages = pagesList.get(pagesList.size() - 1);
        assertThat(testPages.getIdscrapyData()).isEqualTo(DEFAULT_IDSCRAPY_DATA);
        assertThat(testPages.getHeaderData()).isEqualTo(DEFAULT_HEADER_DATA);
        assertThat(testPages.getBodyData()).isEqualTo(DEFAULT_BODY_DATA);
        assertThat(testPages.getAutorData()).isEqualTo(DEFAULT_AUTOR_DATA);
        assertThat(testPages.getDateData()).isEqualTo(DEFAULT_DATE_DATA);
        assertThat(testPages.getHeaderClean()).isEqualTo(DEFAULT_HEADER_CLEAN);
        assertThat(testPages.getBodyClean()).isEqualTo(DEFAULT_BODY_CLEAN);
        assertThat(testPages.getAutorClean()).isEqualTo(DEFAULT_AUTOR_CLEAN);
        assertThat(testPages.getDateClean()).isEqualTo(DEFAULT_DATE_CLEAN);
        assertThat(testPages.getResumeClean()).isEqualTo(DEFAULT_RESUME_CLEAN);
        assertThat(testPages.getResumeData()).isEqualTo(DEFAULT_RESUME_DATA);
        assertThat(testPages.getScreenshot()).isEqualTo(DEFAULT_SCREENSHOT);
        assertThat(testPages.getScreenshotContentType()).isEqualTo(DEFAULT_SCREENSHOT_CONTENT_TYPE);
        assertThat(testPages.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPages.getFullPath()).isEqualTo(DEFAULT_FULL_PATH);
        assertThat(testPages.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createPagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pagesRepository.findAll().size();

        // Create the Pages with an existing ID
        pages.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPagesMockMvc.perform(post("/api/pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pages)))
            .andExpect(status().isBadRequest());

        // Validate the Pages in the database
        List<Pages> pagesList = pagesRepository.findAll();
        assertThat(pagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdscrapyDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagesRepository.findAll().size();
        // set the field null
        pages.setIdscrapyData(null);

        // Create the Pages, which fails.

        restPagesMockMvc.perform(post("/api/pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pages)))
            .andExpect(status().isBadRequest());

        List<Pages> pagesList = pagesRepository.findAll();
        assertThat(pagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPages() throws Exception {
        // Initialize the database
        pagesRepository.saveAndFlush(pages);

        // Get all the pagesList
        restPagesMockMvc.perform(get("/api/pages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pages.getId().intValue())))
            .andExpect(jsonPath("$.[*].idscrapyData").value(hasItem(DEFAULT_IDSCRAPY_DATA)))
            .andExpect(jsonPath("$.[*].headerData").value(hasItem(DEFAULT_HEADER_DATA.toString())))
            .andExpect(jsonPath("$.[*].bodyData").value(hasItem(DEFAULT_BODY_DATA.toString())))
            .andExpect(jsonPath("$.[*].autorData").value(hasItem(DEFAULT_AUTOR_DATA.toString())))
            .andExpect(jsonPath("$.[*].dateData").value(hasItem(DEFAULT_DATE_DATA.toString())))
            .andExpect(jsonPath("$.[*].headerClean").value(hasItem(DEFAULT_HEADER_CLEAN.toString())))
            .andExpect(jsonPath("$.[*].bodyClean").value(hasItem(DEFAULT_BODY_CLEAN.toString())))
            .andExpect(jsonPath("$.[*].autorClean").value(hasItem(DEFAULT_AUTOR_CLEAN.toString())))
            .andExpect(jsonPath("$.[*].dateClean").value(hasItem(DEFAULT_DATE_CLEAN.toString())))
            .andExpect(jsonPath("$.[*].resumeClean").value(hasItem(DEFAULT_RESUME_CLEAN.toString())))
            .andExpect(jsonPath("$.[*].resumeData").value(hasItem(DEFAULT_RESUME_DATA.toString())))
            .andExpect(jsonPath("$.[*].screenshotContentType").value(hasItem(DEFAULT_SCREENSHOT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].screenshot").value(hasItem(Base64Utils.encodeToString(DEFAULT_SCREENSHOT))))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].fullPath").value(hasItem(DEFAULT_FULL_PATH.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())));
    }
    
    @Test
    @Transactional
    public void getPages() throws Exception {
        // Initialize the database
        pagesRepository.saveAndFlush(pages);

        // Get the pages
        restPagesMockMvc.perform(get("/api/pages/{id}", pages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pages.getId().intValue()))
            .andExpect(jsonPath("$.idscrapyData").value(DEFAULT_IDSCRAPY_DATA))
            .andExpect(jsonPath("$.headerData").value(DEFAULT_HEADER_DATA.toString()))
            .andExpect(jsonPath("$.bodyData").value(DEFAULT_BODY_DATA.toString()))
            .andExpect(jsonPath("$.autorData").value(DEFAULT_AUTOR_DATA.toString()))
            .andExpect(jsonPath("$.dateData").value(DEFAULT_DATE_DATA.toString()))
            .andExpect(jsonPath("$.headerClean").value(DEFAULT_HEADER_CLEAN.toString()))
            .andExpect(jsonPath("$.bodyClean").value(DEFAULT_BODY_CLEAN.toString()))
            .andExpect(jsonPath("$.autorClean").value(DEFAULT_AUTOR_CLEAN.toString()))
            .andExpect(jsonPath("$.dateClean").value(DEFAULT_DATE_CLEAN.toString()))
            .andExpect(jsonPath("$.resumeClean").value(DEFAULT_RESUME_CLEAN.toString()))
            .andExpect(jsonPath("$.resumeData").value(DEFAULT_RESUME_DATA.toString()))
            .andExpect(jsonPath("$.screenshotContentType").value(DEFAULT_SCREENSHOT_CONTENT_TYPE))
            .andExpect(jsonPath("$.screenshot").value(Base64Utils.encodeToString(DEFAULT_SCREENSHOT)))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.fullPath").value(DEFAULT_FULL_PATH.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPages() throws Exception {
        // Get the pages
        restPagesMockMvc.perform(get("/api/pages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePages() throws Exception {
        // Initialize the database
        pagesService.save(pages);

        int databaseSizeBeforeUpdate = pagesRepository.findAll().size();

        // Update the pages
        Pages updatedPages = pagesRepository.findById(pages.getId()).get();
        // Disconnect from session so that the updates on updatedPages are not directly saved in db
        em.detach(updatedPages);
        updatedPages
            .idscrapyData(UPDATED_IDSCRAPY_DATA)
            .headerData(UPDATED_HEADER_DATA)
            .bodyData(UPDATED_BODY_DATA)
            .autorData(UPDATED_AUTOR_DATA)
            .dateData(UPDATED_DATE_DATA)
            .headerClean(UPDATED_HEADER_CLEAN)
            .bodyClean(UPDATED_BODY_CLEAN)
            .autorClean(UPDATED_AUTOR_CLEAN)
            .dateClean(UPDATED_DATE_CLEAN)
            .resumeClean(UPDATED_RESUME_CLEAN)
            .resumeData(UPDATED_RESUME_DATA)
            .screenshot(UPDATED_SCREENSHOT)
            .screenshotContentType(UPDATED_SCREENSHOT_CONTENT_TYPE)
            .date(UPDATED_DATE)
            .fullPath(UPDATED_FULL_PATH)
            .url(UPDATED_URL);

        restPagesMockMvc.perform(put("/api/pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPages)))
            .andExpect(status().isOk());

        // Validate the Pages in the database
        List<Pages> pagesList = pagesRepository.findAll();
        assertThat(pagesList).hasSize(databaseSizeBeforeUpdate);
        Pages testPages = pagesList.get(pagesList.size() - 1);
        assertThat(testPages.getIdscrapyData()).isEqualTo(UPDATED_IDSCRAPY_DATA);
        assertThat(testPages.getHeaderData()).isEqualTo(UPDATED_HEADER_DATA);
        assertThat(testPages.getBodyData()).isEqualTo(UPDATED_BODY_DATA);
        assertThat(testPages.getAutorData()).isEqualTo(UPDATED_AUTOR_DATA);
        assertThat(testPages.getDateData()).isEqualTo(UPDATED_DATE_DATA);
        assertThat(testPages.getHeaderClean()).isEqualTo(UPDATED_HEADER_CLEAN);
        assertThat(testPages.getBodyClean()).isEqualTo(UPDATED_BODY_CLEAN);
        assertThat(testPages.getAutorClean()).isEqualTo(UPDATED_AUTOR_CLEAN);
        assertThat(testPages.getDateClean()).isEqualTo(UPDATED_DATE_CLEAN);
        assertThat(testPages.getResumeClean()).isEqualTo(UPDATED_RESUME_CLEAN);
        assertThat(testPages.getResumeData()).isEqualTo(UPDATED_RESUME_DATA);
        assertThat(testPages.getScreenshot()).isEqualTo(UPDATED_SCREENSHOT);
        assertThat(testPages.getScreenshotContentType()).isEqualTo(UPDATED_SCREENSHOT_CONTENT_TYPE);
        assertThat(testPages.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPages.getFullPath()).isEqualTo(UPDATED_FULL_PATH);
        assertThat(testPages.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingPages() throws Exception {
        int databaseSizeBeforeUpdate = pagesRepository.findAll().size();

        // Create the Pages

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPagesMockMvc.perform(put("/api/pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pages)))
            .andExpect(status().isBadRequest());

        // Validate the Pages in the database
        List<Pages> pagesList = pagesRepository.findAll();
        assertThat(pagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePages() throws Exception {
        // Initialize the database
        pagesService.save(pages);

        int databaseSizeBeforeDelete = pagesRepository.findAll().size();

        // Get the pages
        restPagesMockMvc.perform(delete("/api/pages/{id}", pages.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pages> pagesList = pagesRepository.findAll();
        assertThat(pagesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pages.class);
        Pages pages1 = new Pages();
        pages1.setId(1L);
        Pages pages2 = new Pages();
        pages2.setId(pages1.getId());
        assertThat(pages1).isEqualTo(pages2);
        pages2.setId(2L);
        assertThat(pages1).isNotEqualTo(pages2);
        pages1.setId(null);
        assertThat(pages1).isNotEqualTo(pages2);
    }
}
