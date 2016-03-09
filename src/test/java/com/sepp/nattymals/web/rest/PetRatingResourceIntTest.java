package com.sepp.nattymals.web.rest;

import com.sepp.nattymals.Application;
import com.sepp.nattymals.domain.PetRating;
import com.sepp.nattymals.repository.PetRatingRepository;
import com.sepp.nattymals.service.PetRatingService;

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
 * Test class for the PetRatingResource REST controller.
 *
 * @see PetRatingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PetRatingResourceIntTest {


    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;

    @Inject
    private PetRatingRepository petRatingRepository;

    @Inject
    private PetRatingService petRatingService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPetRatingMockMvc;

    private PetRating petRating;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PetRatingResource petRatingResource = new PetRatingResource();
        ReflectionTestUtils.setField(petRatingResource, "petRatingService", petRatingService);
        this.restPetRatingMockMvc = MockMvcBuilders.standaloneSetup(petRatingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        petRating = new PetRating();
        petRating.setRating(DEFAULT_RATING);
    }

    @Test
    @Transactional
    public void createPetRating() throws Exception {
        int databaseSizeBeforeCreate = petRatingRepository.findAll().size();

        // Create the PetRating

        restPetRatingMockMvc.perform(post("/api/petRatings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(petRating)))
                .andExpect(status().isCreated());

        // Validate the PetRating in the database
        List<PetRating> petRatings = petRatingRepository.findAll();
        assertThat(petRatings).hasSize(databaseSizeBeforeCreate + 1);
        PetRating testPetRating = petRatings.get(petRatings.size() - 1);
        assertThat(testPetRating.getRating()).isEqualTo(DEFAULT_RATING);
    }

    @Test
    @Transactional
    public void checkRatingIsRequired() throws Exception {
        int databaseSizeBeforeTest = petRatingRepository.findAll().size();
        // set the field null
        petRating.setRating(null);

        // Create the PetRating, which fails.

        restPetRatingMockMvc.perform(post("/api/petRatings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(petRating)))
                .andExpect(status().isBadRequest());

        List<PetRating> petRatings = petRatingRepository.findAll();
        assertThat(petRatings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPetRatings() throws Exception {
        // Initialize the database
        petRatingRepository.saveAndFlush(petRating);

        // Get all the petRatings
        restPetRatingMockMvc.perform(get("/api/petRatings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(petRating.getId().intValue())))
                .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)));
    }

    @Test
    @Transactional
    public void getPetRating() throws Exception {
        // Initialize the database
        petRatingRepository.saveAndFlush(petRating);

        // Get the petRating
        restPetRatingMockMvc.perform(get("/api/petRatings/{id}", petRating.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(petRating.getId().intValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING));
    }

    @Test
    @Transactional
    public void getNonExistingPetRating() throws Exception {
        // Get the petRating
        restPetRatingMockMvc.perform(get("/api/petRatings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePetRating() throws Exception {
        // Initialize the database
        petRatingRepository.saveAndFlush(petRating);

		int databaseSizeBeforeUpdate = petRatingRepository.findAll().size();

        // Update the petRating
        petRating.setRating(UPDATED_RATING);

        restPetRatingMockMvc.perform(put("/api/petRatings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(petRating)))
                .andExpect(status().isOk());

        // Validate the PetRating in the database
        List<PetRating> petRatings = petRatingRepository.findAll();
        assertThat(petRatings).hasSize(databaseSizeBeforeUpdate);
        PetRating testPetRating = petRatings.get(petRatings.size() - 1);
        assertThat(testPetRating.getRating()).isEqualTo(UPDATED_RATING);
    }

    @Test
    @Transactional
    public void deletePetRating() throws Exception {
        // Initialize the database
        petRatingRepository.saveAndFlush(petRating);

		int databaseSizeBeforeDelete = petRatingRepository.findAll().size();

        // Get the petRating
        restPetRatingMockMvc.perform(delete("/api/petRatings/{id}", petRating.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PetRating> petRatings = petRatingRepository.findAll();
        assertThat(petRatings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
