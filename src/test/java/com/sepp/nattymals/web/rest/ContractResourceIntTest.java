package com.sepp.nattymals.web.rest;

import com.sepp.nattymals.Application;
import com.sepp.nattymals.domain.Contract;
import com.sepp.nattymals.repository.ContractRepository;
import com.sepp.nattymals.service.ContractService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ContractResource REST controller.
 *
 * @see ContractResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ContractResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";

    private static final Double DEFAULT_FEE = 0D;
    private static final Double UPDATED_FEE = 1D;
    private static final String DEFAULT_REFERENCE_CODE = "AAAAA";
    private static final String UPDATED_REFERENCE_CODE = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATION_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATION_DATE);

    private static final ZonedDateTime DEFAULT_TERMINATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_TERMINATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_TERMINATION_DATE_STR = dateTimeFormatter.format(DEFAULT_TERMINATION_DATE);
    private static final String DEFAULT_TERMS = "AAAAA";
    private static final String UPDATED_TERMS = "BBBBB";

    private static final Boolean DEFAULT_REGISTERED = false;
    private static final Boolean UPDATED_REGISTERED = true;

    @Inject
    private ContractRepository contractRepository;

    @Inject
    private ContractService contractService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restContractMockMvc;

    private Contract contract;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContractResource contractResource = new ContractResource();
        ReflectionTestUtils.setField(contractResource, "contractService", contractService);
        this.restContractMockMvc = MockMvcBuilders.standaloneSetup(contractResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        contract = new Contract();
        contract.setTitle(DEFAULT_TITLE);
        contract.setFee(DEFAULT_FEE);
        contract.setReferenceCode(DEFAULT_REFERENCE_CODE);
        contract.setCreationDate(DEFAULT_CREATION_DATE);
        contract.setTerminationDate(DEFAULT_TERMINATION_DATE);
        contract.setTerms(DEFAULT_TERMS);
        contract.setRegistered(DEFAULT_REGISTERED);
    }

    @Test
    @Transactional
    public void createContract() throws Exception {
        int databaseSizeBeforeCreate = contractRepository.findAll().size();

        // Create the Contract

        restContractMockMvc.perform(post("/api/contracts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contract)))
                .andExpect(status().isCreated());

        // Validate the Contract in the database
        List<Contract> contracts = contractRepository.findAll();
        assertThat(contracts).hasSize(databaseSizeBeforeCreate + 1);
        Contract testContract = contracts.get(contracts.size() - 1);
        assertThat(testContract.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testContract.getFee()).isEqualTo(DEFAULT_FEE);
        assertThat(testContract.getReferenceCode()).isEqualTo(DEFAULT_REFERENCE_CODE);
        assertThat(testContract.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testContract.getTerminationDate()).isEqualTo(DEFAULT_TERMINATION_DATE);
        assertThat(testContract.getTerms()).isEqualTo(DEFAULT_TERMS);
        assertThat(testContract.getRegistered()).isEqualTo(DEFAULT_REGISTERED);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractRepository.findAll().size();
        // set the field null
        contract.setTitle(null);

        // Create the Contract, which fails.

        restContractMockMvc.perform(post("/api/contracts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contract)))
                .andExpect(status().isBadRequest());

        List<Contract> contracts = contractRepository.findAll();
        assertThat(contracts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractRepository.findAll().size();
        // set the field null
        contract.setFee(null);

        // Create the Contract, which fails.

        restContractMockMvc.perform(post("/api/contracts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contract)))
                .andExpect(status().isBadRequest());

        List<Contract> contracts = contractRepository.findAll();
        assertThat(contracts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReferenceCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractRepository.findAll().size();
        // set the field null
        contract.setReferenceCode(null);

        // Create the Contract, which fails.

        restContractMockMvc.perform(post("/api/contracts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contract)))
                .andExpect(status().isBadRequest());

        List<Contract> contracts = contractRepository.findAll();
        assertThat(contracts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractRepository.findAll().size();
        // set the field null
        contract.setCreationDate(null);

        // Create the Contract, which fails.

        restContractMockMvc.perform(post("/api/contracts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contract)))
                .andExpect(status().isBadRequest());

        List<Contract> contracts = contractRepository.findAll();
        assertThat(contracts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTerminationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractRepository.findAll().size();
        // set the field null
        contract.setTerminationDate(null);

        // Create the Contract, which fails.

        restContractMockMvc.perform(post("/api/contracts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contract)))
                .andExpect(status().isBadRequest());

        List<Contract> contracts = contractRepository.findAll();
        assertThat(contracts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegisteredIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractRepository.findAll().size();
        // set the field null
        contract.setRegistered(null);

        // Create the Contract, which fails.

        restContractMockMvc.perform(post("/api/contracts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contract)))
                .andExpect(status().isBadRequest());

        List<Contract> contracts = contractRepository.findAll();
        assertThat(contracts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContracts() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get all the contracts
        restContractMockMvc.perform(get("/api/contracts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(contract.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].fee").value(hasItem(DEFAULT_FEE.doubleValue())))
                .andExpect(jsonPath("$.[*].referenceCode").value(hasItem(DEFAULT_REFERENCE_CODE.toString())))
                .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE_STR)))
                .andExpect(jsonPath("$.[*].terminationDate").value(hasItem(DEFAULT_TERMINATION_DATE_STR)))
                .andExpect(jsonPath("$.[*].terms").value(hasItem(DEFAULT_TERMS.toString())))
                .andExpect(jsonPath("$.[*].registered").value(hasItem(DEFAULT_REGISTERED.booleanValue())));
    }

    @Test
    @Transactional
    public void getContract() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get the contract
        restContractMockMvc.perform(get("/api/contracts/{id}", contract.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(contract.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.fee").value(DEFAULT_FEE.doubleValue()))
            .andExpect(jsonPath("$.referenceCode").value(DEFAULT_REFERENCE_CODE.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE_STR))
            .andExpect(jsonPath("$.terminationDate").value(DEFAULT_TERMINATION_DATE_STR))
            .andExpect(jsonPath("$.terms").value(DEFAULT_TERMS.toString()))
            .andExpect(jsonPath("$.registered").value(DEFAULT_REGISTERED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingContract() throws Exception {
        // Get the contract
        restContractMockMvc.perform(get("/api/contracts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContract() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

		int databaseSizeBeforeUpdate = contractRepository.findAll().size();

        // Update the contract
        contract.setTitle(UPDATED_TITLE);
        contract.setFee(UPDATED_FEE);
        contract.setReferenceCode(UPDATED_REFERENCE_CODE);
        contract.setCreationDate(UPDATED_CREATION_DATE);
        contract.setTerminationDate(UPDATED_TERMINATION_DATE);
        contract.setTerms(UPDATED_TERMS);
        contract.setRegistered(UPDATED_REGISTERED);

        restContractMockMvc.perform(put("/api/contracts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contract)))
                .andExpect(status().isOk());

        // Validate the Contract in the database
        List<Contract> contracts = contractRepository.findAll();
        assertThat(contracts).hasSize(databaseSizeBeforeUpdate);
        Contract testContract = contracts.get(contracts.size() - 1);
        assertThat(testContract.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testContract.getFee()).isEqualTo(UPDATED_FEE);
        assertThat(testContract.getReferenceCode()).isEqualTo(UPDATED_REFERENCE_CODE);
        assertThat(testContract.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testContract.getTerminationDate()).isEqualTo(UPDATED_TERMINATION_DATE);
        assertThat(testContract.getTerms()).isEqualTo(UPDATED_TERMS);
        assertThat(testContract.getRegistered()).isEqualTo(UPDATED_REGISTERED);
    }

    @Test
    @Transactional
    public void deleteContract() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

		int databaseSizeBeforeDelete = contractRepository.findAll().size();

        // Get the contract
        restContractMockMvc.perform(delete("/api/contracts/{id}", contract.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Contract> contracts = contractRepository.findAll();
        assertThat(contracts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
