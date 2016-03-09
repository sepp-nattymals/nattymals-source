package com.sepp.nattymals.web.rest;

import com.sepp.nattymals.Application;
import com.sepp.nattymals.domain.Adoption;
import com.sepp.nattymals.repository.AdoptionRepository;
import com.sepp.nattymals.service.AdoptionService;

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
 * Test class for the AdoptionResource REST controller.
 *
 * @see AdoptionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AdoptionResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_INFORMATIVE_TEXT = "AAAAA";
    private static final String UPDATED_INFORMATIVE_TEXT = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATION_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATION_DATE);

    private static final ZonedDateTime DEFAULT_MODIFICATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_MODIFICATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_MODIFICATION_DATE_STR = dateTimeFormatter.format(DEFAULT_MODIFICATION_DATE);

    private static final Boolean DEFAULT_IS_REMOVED = false;
    private static final Boolean UPDATED_IS_REMOVED = true;

    @Inject
    private AdoptionRepository adoptionRepository;

    @Inject
    private AdoptionService adoptionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAdoptionMockMvc;

    private Adoption adoption;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AdoptionResource adoptionResource = new AdoptionResource();
        ReflectionTestUtils.setField(adoptionResource, "adoptionService", adoptionService);
        this.restAdoptionMockMvc = MockMvcBuilders.standaloneSetup(adoptionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        adoption = new Adoption();
        adoption.setInformativeText(DEFAULT_INFORMATIVE_TEXT);
        adoption.setCreationDate(DEFAULT_CREATION_DATE);
        adoption.setModificationDate(DEFAULT_MODIFICATION_DATE);
        adoption.setIsRemoved(DEFAULT_IS_REMOVED);
    }

    @Test
    @Transactional
    public void createAdoption() throws Exception {
        int databaseSizeBeforeCreate = adoptionRepository.findAll().size();

        // Create the Adoption

        restAdoptionMockMvc.perform(post("/api/adoptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adoption)))
                .andExpect(status().isCreated());

        // Validate the Adoption in the database
        List<Adoption> adoptions = adoptionRepository.findAll();
        assertThat(adoptions).hasSize(databaseSizeBeforeCreate + 1);
        Adoption testAdoption = adoptions.get(adoptions.size() - 1);
        assertThat(testAdoption.getInformativeText()).isEqualTo(DEFAULT_INFORMATIVE_TEXT);
        assertThat(testAdoption.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAdoption.getModificationDate()).isEqualTo(DEFAULT_MODIFICATION_DATE);
        assertThat(testAdoption.getIsRemoved()).isEqualTo(DEFAULT_IS_REMOVED);
    }

    @Test
    @Transactional
    public void checkInformativeTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = adoptionRepository.findAll().size();
        // set the field null
        adoption.setInformativeText(null);

        // Create the Adoption, which fails.

        restAdoptionMockMvc.perform(post("/api/adoptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adoption)))
                .andExpect(status().isBadRequest());

        List<Adoption> adoptions = adoptionRepository.findAll();
        assertThat(adoptions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = adoptionRepository.findAll().size();
        // set the field null
        adoption.setCreationDate(null);

        // Create the Adoption, which fails.

        restAdoptionMockMvc.perform(post("/api/adoptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adoption)))
                .andExpect(status().isBadRequest());

        List<Adoption> adoptions = adoptionRepository.findAll();
        assertThat(adoptions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModificationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = adoptionRepository.findAll().size();
        // set the field null
        adoption.setModificationDate(null);

        // Create the Adoption, which fails.

        restAdoptionMockMvc.perform(post("/api/adoptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adoption)))
                .andExpect(status().isBadRequest());

        List<Adoption> adoptions = adoptionRepository.findAll();
        assertThat(adoptions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsRemovedIsRequired() throws Exception {
        int databaseSizeBeforeTest = adoptionRepository.findAll().size();
        // set the field null
        adoption.setIsRemoved(null);

        // Create the Adoption, which fails.

        restAdoptionMockMvc.perform(post("/api/adoptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adoption)))
                .andExpect(status().isBadRequest());

        List<Adoption> adoptions = adoptionRepository.findAll();
        assertThat(adoptions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdoptions() throws Exception {
        // Initialize the database
        adoptionRepository.saveAndFlush(adoption);

        // Get all the adoptions
        restAdoptionMockMvc.perform(get("/api/adoptions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(adoption.getId().intValue())))
                .andExpect(jsonPath("$.[*].informativeText").value(hasItem(DEFAULT_INFORMATIVE_TEXT.toString())))
                .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE_STR)))
                .andExpect(jsonPath("$.[*].modificationDate").value(hasItem(DEFAULT_MODIFICATION_DATE_STR)))
                .andExpect(jsonPath("$.[*].isRemoved").value(hasItem(DEFAULT_IS_REMOVED.booleanValue())));
    }

    @Test
    @Transactional
    public void getAdoption() throws Exception {
        // Initialize the database
        adoptionRepository.saveAndFlush(adoption);

        // Get the adoption
        restAdoptionMockMvc.perform(get("/api/adoptions/{id}", adoption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(adoption.getId().intValue()))
            .andExpect(jsonPath("$.informativeText").value(DEFAULT_INFORMATIVE_TEXT.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE_STR))
            .andExpect(jsonPath("$.modificationDate").value(DEFAULT_MODIFICATION_DATE_STR))
            .andExpect(jsonPath("$.isRemoved").value(DEFAULT_IS_REMOVED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAdoption() throws Exception {
        // Get the adoption
        restAdoptionMockMvc.perform(get("/api/adoptions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdoption() throws Exception {
        // Initialize the database
        adoptionRepository.saveAndFlush(adoption);

		int databaseSizeBeforeUpdate = adoptionRepository.findAll().size();

        // Update the adoption
        adoption.setInformativeText(UPDATED_INFORMATIVE_TEXT);
        adoption.setCreationDate(UPDATED_CREATION_DATE);
        adoption.setModificationDate(UPDATED_MODIFICATION_DATE);
        adoption.setIsRemoved(UPDATED_IS_REMOVED);

        restAdoptionMockMvc.perform(put("/api/adoptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adoption)))
                .andExpect(status().isOk());

        // Validate the Adoption in the database
        List<Adoption> adoptions = adoptionRepository.findAll();
        assertThat(adoptions).hasSize(databaseSizeBeforeUpdate);
        Adoption testAdoption = adoptions.get(adoptions.size() - 1);
        assertThat(testAdoption.getInformativeText()).isEqualTo(UPDATED_INFORMATIVE_TEXT);
        assertThat(testAdoption.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAdoption.getModificationDate()).isEqualTo(UPDATED_MODIFICATION_DATE);
        assertThat(testAdoption.getIsRemoved()).isEqualTo(UPDATED_IS_REMOVED);
    }

    @Test
    @Transactional
    public void deleteAdoption() throws Exception {
        // Initialize the database
        adoptionRepository.saveAndFlush(adoption);

		int databaseSizeBeforeDelete = adoptionRepository.findAll().size();

        // Get the adoption
        restAdoptionMockMvc.perform(delete("/api/adoptions/{id}", adoption.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Adoption> adoptions = adoptionRepository.findAll();
        assertThat(adoptions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
