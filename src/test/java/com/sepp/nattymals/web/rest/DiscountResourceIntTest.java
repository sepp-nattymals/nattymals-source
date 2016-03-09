package com.sepp.nattymals.web.rest;

import com.sepp.nattymals.Application;
import com.sepp.nattymals.domain.Discount;
import com.sepp.nattymals.repository.DiscountRepository;
import com.sepp.nattymals.service.DiscountService;

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
 * Test class for the DiscountResource REST controller.
 *
 * @see DiscountResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DiscountResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_COMPANY_NAME = "AAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBB";
    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final Integer DEFAULT_DISCOUNT_RATE = 0;
    private static final Integer UPDATED_DISCOUNT_RATE = 1;

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_DATE_STR = dateTimeFormatter.format(DEFAULT_START_DATE);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_DATE_STR = dateTimeFormatter.format(DEFAULT_END_DATE);

    @Inject
    private DiscountRepository discountRepository;

    @Inject
    private DiscountService discountService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDiscountMockMvc;

    private Discount discount;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DiscountResource discountResource = new DiscountResource();
        ReflectionTestUtils.setField(discountResource, "discountService", discountService);
        this.restDiscountMockMvc = MockMvcBuilders.standaloneSetup(discountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        discount = new Discount();
        discount.setCompanyName(DEFAULT_COMPANY_NAME);
        discount.setTitle(DEFAULT_TITLE);
        discount.setDescription(DEFAULT_DESCRIPTION);
        discount.setCode(DEFAULT_CODE);
        discount.setDiscountRate(DEFAULT_DISCOUNT_RATE);
        discount.setStartDate(DEFAULT_START_DATE);
        discount.setEndDate(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createDiscount() throws Exception {
        int databaseSizeBeforeCreate = discountRepository.findAll().size();

        // Create the Discount

        restDiscountMockMvc.perform(post("/api/discounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(discount)))
                .andExpect(status().isCreated());

        // Validate the Discount in the database
        List<Discount> discounts = discountRepository.findAll();
        assertThat(discounts).hasSize(databaseSizeBeforeCreate + 1);
        Discount testDiscount = discounts.get(discounts.size() - 1);
        assertThat(testDiscount.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testDiscount.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDiscount.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDiscount.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDiscount.getDiscountRate()).isEqualTo(DEFAULT_DISCOUNT_RATE);
        assertThat(testDiscount.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testDiscount.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void checkCompanyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountRepository.findAll().size();
        // set the field null
        discount.setCompanyName(null);

        // Create the Discount, which fails.

        restDiscountMockMvc.perform(post("/api/discounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(discount)))
                .andExpect(status().isBadRequest());

        List<Discount> discounts = discountRepository.findAll();
        assertThat(discounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountRepository.findAll().size();
        // set the field null
        discount.setTitle(null);

        // Create the Discount, which fails.

        restDiscountMockMvc.perform(post("/api/discounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(discount)))
                .andExpect(status().isBadRequest());

        List<Discount> discounts = discountRepository.findAll();
        assertThat(discounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountRepository.findAll().size();
        // set the field null
        discount.setCode(null);

        // Create the Discount, which fails.

        restDiscountMockMvc.perform(post("/api/discounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(discount)))
                .andExpect(status().isBadRequest());

        List<Discount> discounts = discountRepository.findAll();
        assertThat(discounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiscountRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountRepository.findAll().size();
        // set the field null
        discount.setDiscountRate(null);

        // Create the Discount, which fails.

        restDiscountMockMvc.perform(post("/api/discounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(discount)))
                .andExpect(status().isBadRequest());

        List<Discount> discounts = discountRepository.findAll();
        assertThat(discounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountRepository.findAll().size();
        // set the field null
        discount.setStartDate(null);

        // Create the Discount, which fails.

        restDiscountMockMvc.perform(post("/api/discounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(discount)))
                .andExpect(status().isBadRequest());

        List<Discount> discounts = discountRepository.findAll();
        assertThat(discounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountRepository.findAll().size();
        // set the field null
        discount.setEndDate(null);

        // Create the Discount, which fails.

        restDiscountMockMvc.perform(post("/api/discounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(discount)))
                .andExpect(status().isBadRequest());

        List<Discount> discounts = discountRepository.findAll();
        assertThat(discounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDiscounts() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discounts
        restDiscountMockMvc.perform(get("/api/discounts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(discount.getId().intValue())))
                .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].discountRate").value(hasItem(DEFAULT_DISCOUNT_RATE)))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE_STR)))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE_STR)));
    }

    @Test
    @Transactional
    public void getDiscount() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get the discount
        restDiscountMockMvc.perform(get("/api/discounts/{id}", discount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(discount.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.discountRate").value(DEFAULT_DISCOUNT_RATE))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE_STR))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingDiscount() throws Exception {
        // Get the discount
        restDiscountMockMvc.perform(get("/api/discounts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiscount() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

		int databaseSizeBeforeUpdate = discountRepository.findAll().size();

        // Update the discount
        discount.setCompanyName(UPDATED_COMPANY_NAME);
        discount.setTitle(UPDATED_TITLE);
        discount.setDescription(UPDATED_DESCRIPTION);
        discount.setCode(UPDATED_CODE);
        discount.setDiscountRate(UPDATED_DISCOUNT_RATE);
        discount.setStartDate(UPDATED_START_DATE);
        discount.setEndDate(UPDATED_END_DATE);

        restDiscountMockMvc.perform(put("/api/discounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(discount)))
                .andExpect(status().isOk());

        // Validate the Discount in the database
        List<Discount> discounts = discountRepository.findAll();
        assertThat(discounts).hasSize(databaseSizeBeforeUpdate);
        Discount testDiscount = discounts.get(discounts.size() - 1);
        assertThat(testDiscount.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testDiscount.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDiscount.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDiscount.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDiscount.getDiscountRate()).isEqualTo(UPDATED_DISCOUNT_RATE);
        assertThat(testDiscount.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testDiscount.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void deleteDiscount() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

		int databaseSizeBeforeDelete = discountRepository.findAll().size();

        // Get the discount
        restDiscountMockMvc.perform(delete("/api/discounts/{id}", discount.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Discount> discounts = discountRepository.findAll();
        assertThat(discounts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
