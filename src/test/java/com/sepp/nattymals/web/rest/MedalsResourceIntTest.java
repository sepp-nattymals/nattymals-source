package com.sepp.nattymals.web.rest;

import com.sepp.nattymals.Application;
import com.sepp.nattymals.domain.Medals;
import com.sepp.nattymals.repository.MedalsRepository;
import com.sepp.nattymals.service.MedalsService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the MedalsResource REST controller.
 *
 * @see MedalsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MedalsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Integer DEFAULT_POINTS = 0;
    private static final Integer UPDATED_POINTS = 1;

    private static final byte[] DEFAULT_ICON = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ICON = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ICON_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ICON_CONTENT_TYPE = "image/png";

    @Inject
    private MedalsRepository medalsRepository;

    @Inject
    private MedalsService medalsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMedalsMockMvc;

    private Medals medals;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MedalsResource medalsResource = new MedalsResource();
        ReflectionTestUtils.setField(medalsResource, "medalsService", medalsService);
        this.restMedalsMockMvc = MockMvcBuilders.standaloneSetup(medalsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        medals = new Medals();
        medals.setName(DEFAULT_NAME);
        medals.setCode(DEFAULT_CODE);
        medals.setDescription(DEFAULT_DESCRIPTION);
        medals.setPoints(DEFAULT_POINTS);
        medals.setIcon(DEFAULT_ICON);
        medals.setIconContentType(DEFAULT_ICON_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createMedals() throws Exception {
        int databaseSizeBeforeCreate = medalsRepository.findAll().size();

        // Create the Medals

        restMedalsMockMvc.perform(post("/api/medalss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(medals)))
                .andExpect(status().isCreated());

        // Validate the Medals in the database
        List<Medals> medalss = medalsRepository.findAll();
        assertThat(medalss).hasSize(databaseSizeBeforeCreate + 1);
        Medals testMedals = medalss.get(medalss.size() - 1);
        assertThat(testMedals.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMedals.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMedals.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMedals.getPoints()).isEqualTo(DEFAULT_POINTS);
        assertThat(testMedals.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testMedals.getIconContentType()).isEqualTo(DEFAULT_ICON_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = medalsRepository.findAll().size();
        // set the field null
        medals.setName(null);

        // Create the Medals, which fails.

        restMedalsMockMvc.perform(post("/api/medalss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(medals)))
                .andExpect(status().isBadRequest());

        List<Medals> medalss = medalsRepository.findAll();
        assertThat(medalss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = medalsRepository.findAll().size();
        // set the field null
        medals.setCode(null);

        // Create the Medals, which fails.

        restMedalsMockMvc.perform(post("/api/medalss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(medals)))
                .andExpect(status().isBadRequest());

        List<Medals> medalss = medalsRepository.findAll();
        assertThat(medalss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = medalsRepository.findAll().size();
        // set the field null
        medals.setDescription(null);

        // Create the Medals, which fails.

        restMedalsMockMvc.perform(post("/api/medalss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(medals)))
                .andExpect(status().isBadRequest());

        List<Medals> medalss = medalsRepository.findAll();
        assertThat(medalss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPointsIsRequired() throws Exception {
        int databaseSizeBeforeTest = medalsRepository.findAll().size();
        // set the field null
        medals.setPoints(null);

        // Create the Medals, which fails.

        restMedalsMockMvc.perform(post("/api/medalss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(medals)))
                .andExpect(status().isBadRequest());

        List<Medals> medalss = medalsRepository.findAll();
        assertThat(medalss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIconIsRequired() throws Exception {
        int databaseSizeBeforeTest = medalsRepository.findAll().size();
        // set the field null
        medals.setIcon(null);

        // Create the Medals, which fails.

        restMedalsMockMvc.perform(post("/api/medalss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(medals)))
                .andExpect(status().isBadRequest());

        List<Medals> medalss = medalsRepository.findAll();
        assertThat(medalss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMedalss() throws Exception {
        // Initialize the database
        medalsRepository.saveAndFlush(medals);

        // Get all the medalss
        restMedalsMockMvc.perform(get("/api/medalss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(medals.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS)))
                .andExpect(jsonPath("$.[*].iconContentType").value(hasItem(DEFAULT_ICON_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].icon").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICON))));
    }

    @Test
    @Transactional
    public void getMedals() throws Exception {
        // Initialize the database
        medalsRepository.saveAndFlush(medals);

        // Get the medals
        restMedalsMockMvc.perform(get("/api/medalss/{id}", medals.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(medals.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.points").value(DEFAULT_POINTS))
            .andExpect(jsonPath("$.iconContentType").value(DEFAULT_ICON_CONTENT_TYPE))
            .andExpect(jsonPath("$.icon").value(Base64Utils.encodeToString(DEFAULT_ICON)));
    }

    @Test
    @Transactional
    public void getNonExistingMedals() throws Exception {
        // Get the medals
        restMedalsMockMvc.perform(get("/api/medalss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedals() throws Exception {
        // Initialize the database
        medalsRepository.saveAndFlush(medals);

		int databaseSizeBeforeUpdate = medalsRepository.findAll().size();

        // Update the medals
        medals.setName(UPDATED_NAME);
        medals.setCode(UPDATED_CODE);
        medals.setDescription(UPDATED_DESCRIPTION);
        medals.setPoints(UPDATED_POINTS);
        medals.setIcon(UPDATED_ICON);
        medals.setIconContentType(UPDATED_ICON_CONTENT_TYPE);

        restMedalsMockMvc.perform(put("/api/medalss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(medals)))
                .andExpect(status().isOk());

        // Validate the Medals in the database
        List<Medals> medalss = medalsRepository.findAll();
        assertThat(medalss).hasSize(databaseSizeBeforeUpdate);
        Medals testMedals = medalss.get(medalss.size() - 1);
        assertThat(testMedals.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMedals.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMedals.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMedals.getPoints()).isEqualTo(UPDATED_POINTS);
        assertThat(testMedals.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testMedals.getIconContentType()).isEqualTo(UPDATED_ICON_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteMedals() throws Exception {
        // Initialize the database
        medalsRepository.saveAndFlush(medals);

		int databaseSizeBeforeDelete = medalsRepository.findAll().size();

        // Get the medals
        restMedalsMockMvc.perform(delete("/api/medalss/{id}", medals.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Medals> medalss = medalsRepository.findAll();
        assertThat(medalss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
