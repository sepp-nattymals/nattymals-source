package com.sepp.nattymals.web.rest;

import com.sepp.nattymals.Application;
import com.sepp.nattymals.domain.HistoricalPoints;
import com.sepp.nattymals.repository.HistoricalPointsRepository;
import com.sepp.nattymals.service.HistoricalPointsService;

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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the HistoricalPointsResource REST controller.
 *
 * @see HistoricalPointsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HistoricalPointsResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Integer DEFAULT_OPERATION_POINTS = 0;
    private static final Integer UPDATED_OPERATION_POINTS = 1;

    @Inject
    private HistoricalPointsRepository historicalPointsRepository;

    @Inject
    private HistoricalPointsService historicalPointsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHistoricalPointsMockMvc;

    private HistoricalPoints historicalPoints;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HistoricalPointsResource historicalPointsResource = new HistoricalPointsResource();
        ReflectionTestUtils.setField(historicalPointsResource, "historicalPointsService", historicalPointsService);
        this.restHistoricalPointsMockMvc = MockMvcBuilders.standaloneSetup(historicalPointsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        historicalPoints = new HistoricalPoints();
        historicalPoints.setDescription(DEFAULT_DESCRIPTION);
        historicalPoints.setOperationPoints(DEFAULT_OPERATION_POINTS);
    }

    @Test
    @Transactional
    public void createHistoricalPoints() throws Exception {
        int databaseSizeBeforeCreate = historicalPointsRepository.findAll().size();

        // Create the HistoricalPoints

        restHistoricalPointsMockMvc.perform(post("/api/historicalPointss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(historicalPoints)))
                .andExpect(status().isCreated());

        // Validate the HistoricalPoints in the database
        List<HistoricalPoints> historicalPointss = historicalPointsRepository.findAll();
        assertThat(historicalPointss).hasSize(databaseSizeBeforeCreate + 1);
        HistoricalPoints testHistoricalPoints = historicalPointss.get(historicalPointss.size() - 1);
        assertThat(testHistoricalPoints.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testHistoricalPoints.getOperationPoints()).isEqualTo(DEFAULT_OPERATION_POINTS);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = historicalPointsRepository.findAll().size();
        // set the field null
        historicalPoints.setDescription(null);

        // Create the HistoricalPoints, which fails.

        restHistoricalPointsMockMvc.perform(post("/api/historicalPointss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(historicalPoints)))
                .andExpect(status().isBadRequest());

        List<HistoricalPoints> historicalPointss = historicalPointsRepository.findAll();
        assertThat(historicalPointss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOperationPointsIsRequired() throws Exception {
        int databaseSizeBeforeTest = historicalPointsRepository.findAll().size();
        // set the field null
        historicalPoints.setOperationPoints(null);

        // Create the HistoricalPoints, which fails.

        restHistoricalPointsMockMvc.perform(post("/api/historicalPointss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(historicalPoints)))
                .andExpect(status().isBadRequest());

        List<HistoricalPoints> historicalPointss = historicalPointsRepository.findAll();
        assertThat(historicalPointss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHistoricalPointss() throws Exception {
        // Initialize the database
        historicalPointsRepository.saveAndFlush(historicalPoints);

        // Get all the historicalPointss
        restHistoricalPointsMockMvc.perform(get("/api/historicalPointss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(historicalPoints.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].operationPoints").value(hasItem(DEFAULT_OPERATION_POINTS)));
    }

    @Test
    @Transactional
    public void getHistoricalPoints() throws Exception {
        // Initialize the database
        historicalPointsRepository.saveAndFlush(historicalPoints);

        // Get the historicalPoints
        restHistoricalPointsMockMvc.perform(get("/api/historicalPointss/{id}", historicalPoints.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(historicalPoints.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.operationPoints").value(DEFAULT_OPERATION_POINTS));
    }

    @Test
    @Transactional
    public void getNonExistingHistoricalPoints() throws Exception {
        // Get the historicalPoints
        restHistoricalPointsMockMvc.perform(get("/api/historicalPointss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHistoricalPoints() throws Exception {
        // Initialize the database
        historicalPointsRepository.saveAndFlush(historicalPoints);

		int databaseSizeBeforeUpdate = historicalPointsRepository.findAll().size();

        // Update the historicalPoints
        historicalPoints.setDescription(UPDATED_DESCRIPTION);
        historicalPoints.setOperationPoints(UPDATED_OPERATION_POINTS);

        restHistoricalPointsMockMvc.perform(put("/api/historicalPointss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(historicalPoints)))
                .andExpect(status().isOk());

        // Validate the HistoricalPoints in the database
        List<HistoricalPoints> historicalPointss = historicalPointsRepository.findAll();
        assertThat(historicalPointss).hasSize(databaseSizeBeforeUpdate);
        HistoricalPoints testHistoricalPoints = historicalPointss.get(historicalPointss.size() - 1);
        assertThat(testHistoricalPoints.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHistoricalPoints.getOperationPoints()).isEqualTo(UPDATED_OPERATION_POINTS);
    }

    @Test
    @Transactional
    public void deleteHistoricalPoints() throws Exception {
        // Initialize the database
        historicalPointsRepository.saveAndFlush(historicalPoints);

		int databaseSizeBeforeDelete = historicalPointsRepository.findAll().size();

        // Get the historicalPoints
        restHistoricalPointsMockMvc.perform(delete("/api/historicalPointss/{id}", historicalPoints.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HistoricalPoints> historicalPointss = historicalPointsRepository.findAll();
        assertThat(historicalPointss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
