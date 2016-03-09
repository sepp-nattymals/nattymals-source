package com.sepp.nattymals.web.rest;

import com.sepp.nattymals.Application;
import com.sepp.nattymals.domain.VeterinarianRating;
import com.sepp.nattymals.repository.VeterinarianRatingRepository;
import com.sepp.nattymals.service.VeterinarianRatingService;

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
 * Test class for the VeterinarianRatingResource REST controller.
 *
 * @see VeterinarianRatingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class VeterinarianRatingResourceIntTest {


    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;

    @Inject
    private VeterinarianRatingRepository veterinarianRatingRepository;

    @Inject
    private VeterinarianRatingService veterinarianRatingService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVeterinarianRatingMockMvc;

    private VeterinarianRating veterinarianRating;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VeterinarianRatingResource veterinarianRatingResource = new VeterinarianRatingResource();
        ReflectionTestUtils.setField(veterinarianRatingResource, "veterinarianRatingService", veterinarianRatingService);
        this.restVeterinarianRatingMockMvc = MockMvcBuilders.standaloneSetup(veterinarianRatingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        veterinarianRating = new VeterinarianRating();
        veterinarianRating.setRating(DEFAULT_RATING);
    }

    @Test
    @Transactional
    public void createVeterinarianRating() throws Exception {
        int databaseSizeBeforeCreate = veterinarianRatingRepository.findAll().size();

        // Create the VeterinarianRating

        restVeterinarianRatingMockMvc.perform(post("/api/veterinarianRatings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(veterinarianRating)))
                .andExpect(status().isCreated());

        // Validate the VeterinarianRating in the database
        List<VeterinarianRating> veterinarianRatings = veterinarianRatingRepository.findAll();
        assertThat(veterinarianRatings).hasSize(databaseSizeBeforeCreate + 1);
        VeterinarianRating testVeterinarianRating = veterinarianRatings.get(veterinarianRatings.size() - 1);
        assertThat(testVeterinarianRating.getRating()).isEqualTo(DEFAULT_RATING);
    }

    @Test
    @Transactional
    public void getAllVeterinarianRatings() throws Exception {
        // Initialize the database
        veterinarianRatingRepository.saveAndFlush(veterinarianRating);

        // Get all the veterinarianRatings
        restVeterinarianRatingMockMvc.perform(get("/api/veterinarianRatings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(veterinarianRating.getId().intValue())))
                .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)));
    }

    @Test
    @Transactional
    public void getVeterinarianRating() throws Exception {
        // Initialize the database
        veterinarianRatingRepository.saveAndFlush(veterinarianRating);

        // Get the veterinarianRating
        restVeterinarianRatingMockMvc.perform(get("/api/veterinarianRatings/{id}", veterinarianRating.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(veterinarianRating.getId().intValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING));
    }

    @Test
    @Transactional
    public void getNonExistingVeterinarianRating() throws Exception {
        // Get the veterinarianRating
        restVeterinarianRatingMockMvc.perform(get("/api/veterinarianRatings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVeterinarianRating() throws Exception {
        // Initialize the database
        veterinarianRatingRepository.saveAndFlush(veterinarianRating);

		int databaseSizeBeforeUpdate = veterinarianRatingRepository.findAll().size();

        // Update the veterinarianRating
        veterinarianRating.setRating(UPDATED_RATING);

        restVeterinarianRatingMockMvc.perform(put("/api/veterinarianRatings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(veterinarianRating)))
                .andExpect(status().isOk());

        // Validate the VeterinarianRating in the database
        List<VeterinarianRating> veterinarianRatings = veterinarianRatingRepository.findAll();
        assertThat(veterinarianRatings).hasSize(databaseSizeBeforeUpdate);
        VeterinarianRating testVeterinarianRating = veterinarianRatings.get(veterinarianRatings.size() - 1);
        assertThat(testVeterinarianRating.getRating()).isEqualTo(UPDATED_RATING);
    }

    @Test
    @Transactional
    public void deleteVeterinarianRating() throws Exception {
        // Initialize the database
        veterinarianRatingRepository.saveAndFlush(veterinarianRating);

		int databaseSizeBeforeDelete = veterinarianRatingRepository.findAll().size();

        // Get the veterinarianRating
        restVeterinarianRatingMockMvc.perform(delete("/api/veterinarianRatings/{id}", veterinarianRating.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<VeterinarianRating> veterinarianRatings = veterinarianRatingRepository.findAll();
        assertThat(veterinarianRatings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
