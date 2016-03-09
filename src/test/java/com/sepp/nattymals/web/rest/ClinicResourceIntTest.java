package com.sepp.nattymals.web.rest;

import com.sepp.nattymals.Application;
import com.sepp.nattymals.domain.Clinic;
import com.sepp.nattymals.repository.ClinicRepository;
import com.sepp.nattymals.service.ClinicService;

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
 * Test class for the ClinicResource REST controller.
 *
 * @see ClinicResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ClinicResourceIntTest {

    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";
    private static final String DEFAULT_CITY = "AAAAA";
    private static final String UPDATED_CITY = "BBBBB";
    private static final String DEFAULT_PROVINCE = "AAAAA";
    private static final String UPDATED_PROVINCE = "BBBBB";
    private static final String DEFAULT_SCHEDULE = "AAAAA";
    private static final String UPDATED_SCHEDULE = "BBBBB";
    private static final String DEFAULT_PHONE_NUMBER = "AAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBB";

    @Inject
    private ClinicRepository clinicRepository;

    @Inject
    private ClinicService clinicService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restClinicMockMvc;

    private Clinic clinic;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClinicResource clinicResource = new ClinicResource();
        ReflectionTestUtils.setField(clinicResource, "clinicService", clinicService);
        this.restClinicMockMvc = MockMvcBuilders.standaloneSetup(clinicResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        clinic = new Clinic();
        clinic.setAddress(DEFAULT_ADDRESS);
        clinic.setCity(DEFAULT_CITY);
        clinic.setProvince(DEFAULT_PROVINCE);
        clinic.setSchedule(DEFAULT_SCHEDULE);
        clinic.setPhoneNumber(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void createClinic() throws Exception {
        int databaseSizeBeforeCreate = clinicRepository.findAll().size();

        // Create the Clinic

        restClinicMockMvc.perform(post("/api/clinics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clinic)))
                .andExpect(status().isCreated());

        // Validate the Clinic in the database
        List<Clinic> clinics = clinicRepository.findAll();
        assertThat(clinics).hasSize(databaseSizeBeforeCreate + 1);
        Clinic testClinic = clinics.get(clinics.size() - 1);
        assertThat(testClinic.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testClinic.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testClinic.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testClinic.getSchedule()).isEqualTo(DEFAULT_SCHEDULE);
        assertThat(testClinic.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllClinics() throws Exception {
        // Initialize the database
        clinicRepository.saveAndFlush(clinic);

        // Get all the clinics
        restClinicMockMvc.perform(get("/api/clinics?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(clinic.getId().intValue())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE.toString())))
                .andExpect(jsonPath("$.[*].schedule").value(hasItem(DEFAULT_SCHEDULE.toString())))
                .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getClinic() throws Exception {
        // Initialize the database
        clinicRepository.saveAndFlush(clinic);

        // Get the clinic
        restClinicMockMvc.perform(get("/api/clinics/{id}", clinic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(clinic.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE.toString()))
            .andExpect(jsonPath("$.schedule").value(DEFAULT_SCHEDULE.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClinic() throws Exception {
        // Get the clinic
        restClinicMockMvc.perform(get("/api/clinics/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClinic() throws Exception {
        // Initialize the database
        clinicRepository.saveAndFlush(clinic);

		int databaseSizeBeforeUpdate = clinicRepository.findAll().size();

        // Update the clinic
        clinic.setAddress(UPDATED_ADDRESS);
        clinic.setCity(UPDATED_CITY);
        clinic.setProvince(UPDATED_PROVINCE);
        clinic.setSchedule(UPDATED_SCHEDULE);
        clinic.setPhoneNumber(UPDATED_PHONE_NUMBER);

        restClinicMockMvc.perform(put("/api/clinics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clinic)))
                .andExpect(status().isOk());

        // Validate the Clinic in the database
        List<Clinic> clinics = clinicRepository.findAll();
        assertThat(clinics).hasSize(databaseSizeBeforeUpdate);
        Clinic testClinic = clinics.get(clinics.size() - 1);
        assertThat(testClinic.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testClinic.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testClinic.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testClinic.getSchedule()).isEqualTo(UPDATED_SCHEDULE);
        assertThat(testClinic.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void deleteClinic() throws Exception {
        // Initialize the database
        clinicRepository.saveAndFlush(clinic);

		int databaseSizeBeforeDelete = clinicRepository.findAll().size();

        // Get the clinic
        restClinicMockMvc.perform(delete("/api/clinics/{id}", clinic.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Clinic> clinics = clinicRepository.findAll();
        assertThat(clinics).hasSize(databaseSizeBeforeDelete - 1);
    }
}
