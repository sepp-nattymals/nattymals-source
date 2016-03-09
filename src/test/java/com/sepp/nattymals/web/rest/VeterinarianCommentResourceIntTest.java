package com.sepp.nattymals.web.rest;

import com.sepp.nattymals.Application;
import com.sepp.nattymals.domain.VeterinarianComment;
import com.sepp.nattymals.repository.VeterinarianCommentRepository;
import com.sepp.nattymals.service.VeterinarianCommentService;

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
 * Test class for the VeterinarianCommentResource REST controller.
 *
 * @see VeterinarianCommentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class VeterinarianCommentResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATION_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATION_DATE);
    private static final String DEFAULT_TEXT = "AAAAA";
    private static final String UPDATED_TEXT = "BBBBB";

    @Inject
    private VeterinarianCommentRepository veterinarianCommentRepository;

    @Inject
    private VeterinarianCommentService veterinarianCommentService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVeterinarianCommentMockMvc;

    private VeterinarianComment veterinarianComment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VeterinarianCommentResource veterinarianCommentResource = new VeterinarianCommentResource();
        ReflectionTestUtils.setField(veterinarianCommentResource, "veterinarianCommentService", veterinarianCommentService);
        this.restVeterinarianCommentMockMvc = MockMvcBuilders.standaloneSetup(veterinarianCommentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        veterinarianComment = new VeterinarianComment();
        veterinarianComment.setCreationDate(DEFAULT_CREATION_DATE);
        veterinarianComment.setText(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    public void createVeterinarianComment() throws Exception {
        int databaseSizeBeforeCreate = veterinarianCommentRepository.findAll().size();

        // Create the VeterinarianComment

        restVeterinarianCommentMockMvc.perform(post("/api/veterinarianComments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(veterinarianComment)))
                .andExpect(status().isCreated());

        // Validate the VeterinarianComment in the database
        List<VeterinarianComment> veterinarianComments = veterinarianCommentRepository.findAll();
        assertThat(veterinarianComments).hasSize(databaseSizeBeforeCreate + 1);
        VeterinarianComment testVeterinarianComment = veterinarianComments.get(veterinarianComments.size() - 1);
        assertThat(testVeterinarianComment.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testVeterinarianComment.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    public void getAllVeterinarianComments() throws Exception {
        // Initialize the database
        veterinarianCommentRepository.saveAndFlush(veterinarianComment);

        // Get all the veterinarianComments
        restVeterinarianCommentMockMvc.perform(get("/api/veterinarianComments?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(veterinarianComment.getId().intValue())))
                .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE_STR)))
                .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())));
    }

    @Test
    @Transactional
    public void getVeterinarianComment() throws Exception {
        // Initialize the database
        veterinarianCommentRepository.saveAndFlush(veterinarianComment);

        // Get the veterinarianComment
        restVeterinarianCommentMockMvc.perform(get("/api/veterinarianComments/{id}", veterinarianComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(veterinarianComment.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE_STR))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVeterinarianComment() throws Exception {
        // Get the veterinarianComment
        restVeterinarianCommentMockMvc.perform(get("/api/veterinarianComments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVeterinarianComment() throws Exception {
        // Initialize the database
        veterinarianCommentRepository.saveAndFlush(veterinarianComment);

		int databaseSizeBeforeUpdate = veterinarianCommentRepository.findAll().size();

        // Update the veterinarianComment
        veterinarianComment.setCreationDate(UPDATED_CREATION_DATE);
        veterinarianComment.setText(UPDATED_TEXT);

        restVeterinarianCommentMockMvc.perform(put("/api/veterinarianComments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(veterinarianComment)))
                .andExpect(status().isOk());

        // Validate the VeterinarianComment in the database
        List<VeterinarianComment> veterinarianComments = veterinarianCommentRepository.findAll();
        assertThat(veterinarianComments).hasSize(databaseSizeBeforeUpdate);
        VeterinarianComment testVeterinarianComment = veterinarianComments.get(veterinarianComments.size() - 1);
        assertThat(testVeterinarianComment.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testVeterinarianComment.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void deleteVeterinarianComment() throws Exception {
        // Initialize the database
        veterinarianCommentRepository.saveAndFlush(veterinarianComment);

		int databaseSizeBeforeDelete = veterinarianCommentRepository.findAll().size();

        // Get the veterinarianComment
        restVeterinarianCommentMockMvc.perform(delete("/api/veterinarianComments/{id}", veterinarianComment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<VeterinarianComment> veterinarianComments = veterinarianCommentRepository.findAll();
        assertThat(veterinarianComments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
