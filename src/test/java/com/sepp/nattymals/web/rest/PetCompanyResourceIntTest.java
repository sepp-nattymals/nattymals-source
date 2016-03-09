package com.sepp.nattymals.web.rest;

import com.sepp.nattymals.Application;
import com.sepp.nattymals.domain.PetCompany;
import com.sepp.nattymals.repository.PetCompanyRepository;
import com.sepp.nattymals.service.PetCompanyService;

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
 * Test class for the PetCompanyResource REST controller.
 *
 * @see PetCompanyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PetCompanyResourceIntTest {

    private static final String DEFAULT_BANK_ACCOUNT = "AAAAA";
    private static final String UPDATED_BANK_ACCOUNT = "BBBBB";
    private static final String DEFAULT_NIF = "AAAAA";
    private static final String UPDATED_NIF = "BBBBB";

    @Inject
    private PetCompanyRepository petCompanyRepository;

    @Inject
    private PetCompanyService petCompanyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPetCompanyMockMvc;

    private PetCompany petCompany;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PetCompanyResource petCompanyResource = new PetCompanyResource();
        ReflectionTestUtils.setField(petCompanyResource, "petCompanyService", petCompanyService);
        this.restPetCompanyMockMvc = MockMvcBuilders.standaloneSetup(petCompanyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        petCompany = new PetCompany();
        petCompany.setBankAccount(DEFAULT_BANK_ACCOUNT);
        petCompany.setNif(DEFAULT_NIF);
    }

    @Test
    @Transactional
    public void createPetCompany() throws Exception {
        int databaseSizeBeforeCreate = petCompanyRepository.findAll().size();

        // Create the PetCompany

        restPetCompanyMockMvc.perform(post("/api/petCompanys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(petCompany)))
                .andExpect(status().isCreated());

        // Validate the PetCompany in the database
        List<PetCompany> petCompanys = petCompanyRepository.findAll();
        assertThat(petCompanys).hasSize(databaseSizeBeforeCreate + 1);
        PetCompany testPetCompany = petCompanys.get(petCompanys.size() - 1);
        assertThat(testPetCompany.getBankAccount()).isEqualTo(DEFAULT_BANK_ACCOUNT);
        assertThat(testPetCompany.getNif()).isEqualTo(DEFAULT_NIF);
    }

    @Test
    @Transactional
    public void checkBankAccountIsRequired() throws Exception {
        int databaseSizeBeforeTest = petCompanyRepository.findAll().size();
        // set the field null
        petCompany.setBankAccount(null);

        // Create the PetCompany, which fails.

        restPetCompanyMockMvc.perform(post("/api/petCompanys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(petCompany)))
                .andExpect(status().isBadRequest());

        List<PetCompany> petCompanys = petCompanyRepository.findAll();
        assertThat(petCompanys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNifIsRequired() throws Exception {
        int databaseSizeBeforeTest = petCompanyRepository.findAll().size();
        // set the field null
        petCompany.setNif(null);

        // Create the PetCompany, which fails.

        restPetCompanyMockMvc.perform(post("/api/petCompanys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(petCompany)))
                .andExpect(status().isBadRequest());

        List<PetCompany> petCompanys = petCompanyRepository.findAll();
        assertThat(petCompanys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPetCompanys() throws Exception {
        // Initialize the database
        petCompanyRepository.saveAndFlush(petCompany);

        // Get all the petCompanys
        restPetCompanyMockMvc.perform(get("/api/petCompanys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(petCompany.getId().intValue())))
                .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT.toString())))
                .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF.toString())));
    }

    @Test
    @Transactional
    public void getPetCompany() throws Exception {
        // Initialize the database
        petCompanyRepository.saveAndFlush(petCompany);

        // Get the petCompany
        restPetCompanyMockMvc.perform(get("/api/petCompanys/{id}", petCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(petCompany.getId().intValue()))
            .andExpect(jsonPath("$.bankAccount").value(DEFAULT_BANK_ACCOUNT.toString()))
            .andExpect(jsonPath("$.nif").value(DEFAULT_NIF.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPetCompany() throws Exception {
        // Get the petCompany
        restPetCompanyMockMvc.perform(get("/api/petCompanys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePetCompany() throws Exception {
        // Initialize the database
        petCompanyRepository.saveAndFlush(petCompany);

		int databaseSizeBeforeUpdate = petCompanyRepository.findAll().size();

        // Update the petCompany
        petCompany.setBankAccount(UPDATED_BANK_ACCOUNT);
        petCompany.setNif(UPDATED_NIF);

        restPetCompanyMockMvc.perform(put("/api/petCompanys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(petCompany)))
                .andExpect(status().isOk());

        // Validate the PetCompany in the database
        List<PetCompany> petCompanys = petCompanyRepository.findAll();
        assertThat(petCompanys).hasSize(databaseSizeBeforeUpdate);
        PetCompany testPetCompany = petCompanys.get(petCompanys.size() - 1);
        assertThat(testPetCompany.getBankAccount()).isEqualTo(UPDATED_BANK_ACCOUNT);
        assertThat(testPetCompany.getNif()).isEqualTo(UPDATED_NIF);
    }

    @Test
    @Transactional
    public void deletePetCompany() throws Exception {
        // Initialize the database
        petCompanyRepository.saveAndFlush(petCompany);

		int databaseSizeBeforeDelete = petCompanyRepository.findAll().size();

        // Get the petCompany
        restPetCompanyMockMvc.perform(delete("/api/petCompanys/{id}", petCompany.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PetCompany> petCompanys = petCompanyRepository.findAll();
        assertThat(petCompanys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
