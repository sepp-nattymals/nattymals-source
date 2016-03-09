package com.sepp.nattymals.web.rest;

import com.sepp.nattymals.Application;
import com.sepp.nattymals.domain.Veterinarian;
import com.sepp.nattymals.repository.VeterinarianRepository;
import com.sepp.nattymals.service.VeterinarianService;

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
 * Test class for the VeterinarianResource REST controller.
 *
 * @see VeterinarianResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class VeterinarianResourceIntTest {

    private static final String DEFAULT_BANK_ACCOUNT = "AAAAA";
    private static final String UPDATED_BANK_ACCOUNT = "BBBBB";
    private static final String DEFAULT_WEB_ADDRESS = "AAAAA";
    private static final String UPDATED_WEB_ADDRESS = "BBBBB";
    private static final String DEFAULT_REFEREE_NUMBER = "AAAAA";
    private static final String UPDATED_REFEREE_NUMBER = "BBBBB";

    @Inject
    private VeterinarianRepository veterinarianRepository;

    @Inject
    private VeterinarianService veterinarianService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVeterinarianMockMvc;

    private Veterinarian veterinarian;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VeterinarianResource veterinarianResource = new VeterinarianResource();
        ReflectionTestUtils.setField(veterinarianResource, "veterinarianService", veterinarianService);
        this.restVeterinarianMockMvc = MockMvcBuilders.standaloneSetup(veterinarianResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        veterinarian = new Veterinarian();
        veterinarian.setBankAccount(DEFAULT_BANK_ACCOUNT);
        veterinarian.setWebAddress(DEFAULT_WEB_ADDRESS);
        veterinarian.setRefereeNumber(DEFAULT_REFEREE_NUMBER);
    }

    @Test
    @Transactional
    public void createVeterinarian() throws Exception {
        int databaseSizeBeforeCreate = veterinarianRepository.findAll().size();

        // Create the Veterinarian

        restVeterinarianMockMvc.perform(post("/api/veterinarians")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(veterinarian)))
                .andExpect(status().isCreated());

        // Validate the Veterinarian in the database
        List<Veterinarian> veterinarians = veterinarianRepository.findAll();
        assertThat(veterinarians).hasSize(databaseSizeBeforeCreate + 1);
        Veterinarian testVeterinarian = veterinarians.get(veterinarians.size() - 1);
        assertThat(testVeterinarian.getBankAccount()).isEqualTo(DEFAULT_BANK_ACCOUNT);
        assertThat(testVeterinarian.getWebAddress()).isEqualTo(DEFAULT_WEB_ADDRESS);
        assertThat(testVeterinarian.getRefereeNumber()).isEqualTo(DEFAULT_REFEREE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllVeterinarians() throws Exception {
        // Initialize the database
        veterinarianRepository.saveAndFlush(veterinarian);

        // Get all the veterinarians
        restVeterinarianMockMvc.perform(get("/api/veterinarians?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(veterinarian.getId().intValue())))
                .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT.toString())))
                .andExpect(jsonPath("$.[*].webAddress").value(hasItem(DEFAULT_WEB_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].refereeNumber").value(hasItem(DEFAULT_REFEREE_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getVeterinarian() throws Exception {
        // Initialize the database
        veterinarianRepository.saveAndFlush(veterinarian);

        // Get the veterinarian
        restVeterinarianMockMvc.perform(get("/api/veterinarians/{id}", veterinarian.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(veterinarian.getId().intValue()))
            .andExpect(jsonPath("$.bankAccount").value(DEFAULT_BANK_ACCOUNT.toString()))
            .andExpect(jsonPath("$.webAddress").value(DEFAULT_WEB_ADDRESS.toString()))
            .andExpect(jsonPath("$.refereeNumber").value(DEFAULT_REFEREE_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVeterinarian() throws Exception {
        // Get the veterinarian
        restVeterinarianMockMvc.perform(get("/api/veterinarians/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVeterinarian() throws Exception {
        // Initialize the database
        veterinarianRepository.saveAndFlush(veterinarian);

		int databaseSizeBeforeUpdate = veterinarianRepository.findAll().size();

        // Update the veterinarian
        veterinarian.setBankAccount(UPDATED_BANK_ACCOUNT);
        veterinarian.setWebAddress(UPDATED_WEB_ADDRESS);
        veterinarian.setRefereeNumber(UPDATED_REFEREE_NUMBER);

        restVeterinarianMockMvc.perform(put("/api/veterinarians")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(veterinarian)))
                .andExpect(status().isOk());

        // Validate the Veterinarian in the database
        List<Veterinarian> veterinarians = veterinarianRepository.findAll();
        assertThat(veterinarians).hasSize(databaseSizeBeforeUpdate);
        Veterinarian testVeterinarian = veterinarians.get(veterinarians.size() - 1);
        assertThat(testVeterinarian.getBankAccount()).isEqualTo(UPDATED_BANK_ACCOUNT);
        assertThat(testVeterinarian.getWebAddress()).isEqualTo(UPDATED_WEB_ADDRESS);
        assertThat(testVeterinarian.getRefereeNumber()).isEqualTo(UPDATED_REFEREE_NUMBER);
    }

    @Test
    @Transactional
    public void deleteVeterinarian() throws Exception {
        // Initialize the database
        veterinarianRepository.saveAndFlush(veterinarian);

		int databaseSizeBeforeDelete = veterinarianRepository.findAll().size();

        // Get the veterinarian
        restVeterinarianMockMvc.perform(delete("/api/veterinarians/{id}", veterinarian.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Veterinarian> veterinarians = veterinarianRepository.findAll();
        assertThat(veterinarians).hasSize(databaseSizeBeforeDelete - 1);
    }
}
