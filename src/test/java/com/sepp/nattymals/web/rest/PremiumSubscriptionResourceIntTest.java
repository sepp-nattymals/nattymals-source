package com.sepp.nattymals.web.rest;

import com.sepp.nattymals.Application;
import com.sepp.nattymals.domain.PremiumSubscription;
import com.sepp.nattymals.repository.PremiumSubscriptionRepository;
import com.sepp.nattymals.service.PremiumSubscriptionService;

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
 * Test class for the PremiumSubscriptionResource REST controller.
 *
 * @see PremiumSubscriptionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PremiumSubscriptionResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_DATE_STR = dateTimeFormatter.format(DEFAULT_START_DATE);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_DATE_STR = dateTimeFormatter.format(DEFAULT_END_DATE);

    private static final Double DEFAULT_FEE = 1D;
    private static final Double UPDATED_FEE = 2D;

    @Inject
    private PremiumSubscriptionRepository premiumSubscriptionRepository;

    @Inject
    private PremiumSubscriptionService premiumSubscriptionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPremiumSubscriptionMockMvc;

    private PremiumSubscription premiumSubscription;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PremiumSubscriptionResource premiumSubscriptionResource = new PremiumSubscriptionResource();
        ReflectionTestUtils.setField(premiumSubscriptionResource, "premiumSubscriptionService", premiumSubscriptionService);
        this.restPremiumSubscriptionMockMvc = MockMvcBuilders.standaloneSetup(premiumSubscriptionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        premiumSubscription = new PremiumSubscription();
        premiumSubscription.setStartDate(DEFAULT_START_DATE);
        premiumSubscription.setEndDate(DEFAULT_END_DATE);
        premiumSubscription.setFee(DEFAULT_FEE);
    }

    @Test
    @Transactional
    public void createPremiumSubscription() throws Exception {
        int databaseSizeBeforeCreate = premiumSubscriptionRepository.findAll().size();

        // Create the PremiumSubscription

        restPremiumSubscriptionMockMvc.perform(post("/api/premiumSubscriptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(premiumSubscription)))
                .andExpect(status().isCreated());

        // Validate the PremiumSubscription in the database
        List<PremiumSubscription> premiumSubscriptions = premiumSubscriptionRepository.findAll();
        assertThat(premiumSubscriptions).hasSize(databaseSizeBeforeCreate + 1);
        PremiumSubscription testPremiumSubscription = premiumSubscriptions.get(premiumSubscriptions.size() - 1);
        assertThat(testPremiumSubscription.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPremiumSubscription.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPremiumSubscription.getFee()).isEqualTo(DEFAULT_FEE);
    }

    @Test
    @Transactional
    public void getAllPremiumSubscriptions() throws Exception {
        // Initialize the database
        premiumSubscriptionRepository.saveAndFlush(premiumSubscription);

        // Get all the premiumSubscriptions
        restPremiumSubscriptionMockMvc.perform(get("/api/premiumSubscriptions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(premiumSubscription.getId().intValue())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE_STR)))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE_STR)))
                .andExpect(jsonPath("$.[*].fee").value(hasItem(DEFAULT_FEE.doubleValue())));
    }

    @Test
    @Transactional
    public void getPremiumSubscription() throws Exception {
        // Initialize the database
        premiumSubscriptionRepository.saveAndFlush(premiumSubscription);

        // Get the premiumSubscription
        restPremiumSubscriptionMockMvc.perform(get("/api/premiumSubscriptions/{id}", premiumSubscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(premiumSubscription.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE_STR))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE_STR))
            .andExpect(jsonPath("$.fee").value(DEFAULT_FEE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPremiumSubscription() throws Exception {
        // Get the premiumSubscription
        restPremiumSubscriptionMockMvc.perform(get("/api/premiumSubscriptions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePremiumSubscription() throws Exception {
        // Initialize the database
        premiumSubscriptionRepository.saveAndFlush(premiumSubscription);

		int databaseSizeBeforeUpdate = premiumSubscriptionRepository.findAll().size();

        // Update the premiumSubscription
        premiumSubscription.setStartDate(UPDATED_START_DATE);
        premiumSubscription.setEndDate(UPDATED_END_DATE);
        premiumSubscription.setFee(UPDATED_FEE);

        restPremiumSubscriptionMockMvc.perform(put("/api/premiumSubscriptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(premiumSubscription)))
                .andExpect(status().isOk());

        // Validate the PremiumSubscription in the database
        List<PremiumSubscription> premiumSubscriptions = premiumSubscriptionRepository.findAll();
        assertThat(premiumSubscriptions).hasSize(databaseSizeBeforeUpdate);
        PremiumSubscription testPremiumSubscription = premiumSubscriptions.get(premiumSubscriptions.size() - 1);
        assertThat(testPremiumSubscription.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPremiumSubscription.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPremiumSubscription.getFee()).isEqualTo(UPDATED_FEE);
    }

    @Test
    @Transactional
    public void deletePremiumSubscription() throws Exception {
        // Initialize the database
        premiumSubscriptionRepository.saveAndFlush(premiumSubscription);

		int databaseSizeBeforeDelete = premiumSubscriptionRepository.findAll().size();

        // Get the premiumSubscription
        restPremiumSubscriptionMockMvc.perform(delete("/api/premiumSubscriptions/{id}", premiumSubscription.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PremiumSubscription> premiumSubscriptions = premiumSubscriptionRepository.findAll();
        assertThat(premiumSubscriptions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
