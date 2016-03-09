package com.sepp.nattymals.web.rest;

import com.sepp.nattymals.Application;
import com.sepp.nattymals.domain.PetOwner;
import com.sepp.nattymals.repository.PetOwnerRepository;
import com.sepp.nattymals.service.PetOwnerService;

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
 * Test class for the PetOwnerResource REST controller.
 *
 * @see PetOwnerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PetOwnerResourceIntTest {


    private static final Integer DEFAULT_TOTAL_POINTS = 0;
    private static final Integer UPDATED_TOTAL_POINTS = 1;

    private static final Boolean DEFAULT_IS_BLOCKED = false;
    private static final Boolean UPDATED_IS_BLOCKED = true;

    @Inject
    private PetOwnerRepository petOwnerRepository;

    @Inject
    private PetOwnerService petOwnerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPetOwnerMockMvc;

    private PetOwner petOwner;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PetOwnerResource petOwnerResource = new PetOwnerResource();
        ReflectionTestUtils.setField(petOwnerResource, "petOwnerService", petOwnerService);
        this.restPetOwnerMockMvc = MockMvcBuilders.standaloneSetup(petOwnerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        petOwner = new PetOwner();
        petOwner.setTotalPoints(DEFAULT_TOTAL_POINTS);
        petOwner.setIsBlocked(DEFAULT_IS_BLOCKED);
    }

    @Test
    @Transactional
    public void createPetOwner() throws Exception {
        int databaseSizeBeforeCreate = petOwnerRepository.findAll().size();

        // Create the PetOwner

        restPetOwnerMockMvc.perform(post("/api/petOwners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(petOwner)))
                .andExpect(status().isCreated());

        // Validate the PetOwner in the database
        List<PetOwner> petOwners = petOwnerRepository.findAll();
        assertThat(petOwners).hasSize(databaseSizeBeforeCreate + 1);
        PetOwner testPetOwner = petOwners.get(petOwners.size() - 1);
        assertThat(testPetOwner.getTotalPoints()).isEqualTo(DEFAULT_TOTAL_POINTS);
        assertThat(testPetOwner.getIsBlocked()).isEqualTo(DEFAULT_IS_BLOCKED);
    }

    @Test
    @Transactional
    public void checkTotalPointsIsRequired() throws Exception {
        int databaseSizeBeforeTest = petOwnerRepository.findAll().size();
        // set the field null
        petOwner.setTotalPoints(null);

        // Create the PetOwner, which fails.

        restPetOwnerMockMvc.perform(post("/api/petOwners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(petOwner)))
                .andExpect(status().isBadRequest());

        List<PetOwner> petOwners = petOwnerRepository.findAll();
        assertThat(petOwners).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsBlockedIsRequired() throws Exception {
        int databaseSizeBeforeTest = petOwnerRepository.findAll().size();
        // set the field null
        petOwner.setIsBlocked(null);

        // Create the PetOwner, which fails.

        restPetOwnerMockMvc.perform(post("/api/petOwners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(petOwner)))
                .andExpect(status().isBadRequest());

        List<PetOwner> petOwners = petOwnerRepository.findAll();
        assertThat(petOwners).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPetOwners() throws Exception {
        // Initialize the database
        petOwnerRepository.saveAndFlush(petOwner);

        // Get all the petOwners
        restPetOwnerMockMvc.perform(get("/api/petOwners?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(petOwner.getId().intValue())))
                .andExpect(jsonPath("$.[*].totalPoints").value(hasItem(DEFAULT_TOTAL_POINTS)))
                .andExpect(jsonPath("$.[*].isBlocked").value(hasItem(DEFAULT_IS_BLOCKED.booleanValue())));
    }

    @Test
    @Transactional
    public void getPetOwner() throws Exception {
        // Initialize the database
        petOwnerRepository.saveAndFlush(petOwner);

        // Get the petOwner
        restPetOwnerMockMvc.perform(get("/api/petOwners/{id}", petOwner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(petOwner.getId().intValue()))
            .andExpect(jsonPath("$.totalPoints").value(DEFAULT_TOTAL_POINTS))
            .andExpect(jsonPath("$.isBlocked").value(DEFAULT_IS_BLOCKED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPetOwner() throws Exception {
        // Get the petOwner
        restPetOwnerMockMvc.perform(get("/api/petOwners/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePetOwner() throws Exception {
        // Initialize the database
        petOwnerRepository.saveAndFlush(petOwner);

		int databaseSizeBeforeUpdate = petOwnerRepository.findAll().size();

        // Update the petOwner
        petOwner.setTotalPoints(UPDATED_TOTAL_POINTS);
        petOwner.setIsBlocked(UPDATED_IS_BLOCKED);

        restPetOwnerMockMvc.perform(put("/api/petOwners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(petOwner)))
                .andExpect(status().isOk());

        // Validate the PetOwner in the database
        List<PetOwner> petOwners = petOwnerRepository.findAll();
        assertThat(petOwners).hasSize(databaseSizeBeforeUpdate);
        PetOwner testPetOwner = petOwners.get(petOwners.size() - 1);
        assertThat(testPetOwner.getTotalPoints()).isEqualTo(UPDATED_TOTAL_POINTS);
        assertThat(testPetOwner.getIsBlocked()).isEqualTo(UPDATED_IS_BLOCKED);
    }

    @Test
    @Transactional
    public void deletePetOwner() throws Exception {
        // Initialize the database
        petOwnerRepository.saveAndFlush(petOwner);

		int databaseSizeBeforeDelete = petOwnerRepository.findAll().size();

        // Get the petOwner
        restPetOwnerMockMvc.perform(delete("/api/petOwners/{id}", petOwner.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PetOwner> petOwners = petOwnerRepository.findAll();
        assertThat(petOwners).hasSize(databaseSizeBeforeDelete - 1);
    }
}
