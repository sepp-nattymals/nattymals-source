package com.sepp.nattymals.web.rest;

import com.sepp.nattymals.Application;
import com.sepp.nattymals.domain.Payment;
import com.sepp.nattymals.repository.PaymentRepository;
import com.sepp.nattymals.service.PaymentService;

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
 * Test class for the PaymentResource REST controller.
 *
 * @see PaymentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PaymentResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_CONCEPT = "AAAAA";
    private static final String UPDATED_CONCEPT = "BBBBB";

    private static final Double DEFAULT_AMOUNT = 0D;
    private static final Double UPDATED_AMOUNT = 1D;

    private static final ZonedDateTime DEFAULT_PAYMENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_PAYMENT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_PAYMENT_DATE_STR = dateTimeFormatter.format(DEFAULT_PAYMENT_DATE);
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    @Inject
    private PaymentRepository paymentRepository;

    @Inject
    private PaymentService paymentService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPaymentMockMvc;

    private Payment payment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PaymentResource paymentResource = new PaymentResource();
        ReflectionTestUtils.setField(paymentResource, "paymentService", paymentService);
        this.restPaymentMockMvc = MockMvcBuilders.standaloneSetup(paymentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        payment = new Payment();
        payment.setConcept(DEFAULT_CONCEPT);
        payment.setAmount(DEFAULT_AMOUNT);
        payment.setPaymentDate(DEFAULT_PAYMENT_DATE);
        payment.setComment(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createPayment() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();

        // Create the Payment

        restPaymentMockMvc.perform(post("/api/payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payment)))
                .andExpect(status().isCreated());

        // Validate the Payment in the database
        List<Payment> payments = paymentRepository.findAll();
        assertThat(payments).hasSize(databaseSizeBeforeCreate + 1);
        Payment testPayment = payments.get(payments.size() - 1);
        assertThat(testPayment.getConcept()).isEqualTo(DEFAULT_CONCEPT);
        assertThat(testPayment.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPayment.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testPayment.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void checkConceptIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentRepository.findAll().size();
        // set the field null
        payment.setConcept(null);

        // Create the Payment, which fails.

        restPaymentMockMvc.perform(post("/api/payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payment)))
                .andExpect(status().isBadRequest());

        List<Payment> payments = paymentRepository.findAll();
        assertThat(payments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentRepository.findAll().size();
        // set the field null
        payment.setAmount(null);

        // Create the Payment, which fails.

        restPaymentMockMvc.perform(post("/api/payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payment)))
                .andExpect(status().isBadRequest());

        List<Payment> payments = paymentRepository.findAll();
        assertThat(payments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentRepository.findAll().size();
        // set the field null
        payment.setPaymentDate(null);

        // Create the Payment, which fails.

        restPaymentMockMvc.perform(post("/api/payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payment)))
                .andExpect(status().isBadRequest());

        List<Payment> payments = paymentRepository.findAll();
        assertThat(payments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentRepository.findAll().size();
        // set the field null
        payment.setComment(null);

        // Create the Payment, which fails.

        restPaymentMockMvc.perform(post("/api/payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payment)))
                .andExpect(status().isBadRequest());

        List<Payment> payments = paymentRepository.findAll();
        assertThat(payments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPayments() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the payments
        restPaymentMockMvc.perform(get("/api/payments?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
                .andExpect(jsonPath("$.[*].concept").value(hasItem(DEFAULT_CONCEPT.toString())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
                .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE_STR)))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getPayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get the payment
        restPaymentMockMvc.perform(get("/api/payments/{id}", payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(payment.getId().intValue()))
            .andExpect(jsonPath("$.concept").value(DEFAULT_CONCEPT.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE_STR))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPayment() throws Exception {
        // Get the payment
        restPaymentMockMvc.perform(get("/api/payments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

		int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment
        payment.setConcept(UPDATED_CONCEPT);
        payment.setAmount(UPDATED_AMOUNT);
        payment.setPaymentDate(UPDATED_PAYMENT_DATE);
        payment.setComment(UPDATED_COMMENT);

        restPaymentMockMvc.perform(put("/api/payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payment)))
                .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> payments = paymentRepository.findAll();
        assertThat(payments).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = payments.get(payments.size() - 1);
        assertThat(testPayment.getConcept()).isEqualTo(UPDATED_CONCEPT);
        assertThat(testPayment.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPayment.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testPayment.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void deletePayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

		int databaseSizeBeforeDelete = paymentRepository.findAll().size();

        // Get the payment
        restPaymentMockMvc.perform(delete("/api/payments/{id}", payment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Payment> payments = paymentRepository.findAll();
        assertThat(payments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
