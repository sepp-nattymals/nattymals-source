package com.sepp.nattymals.web.rest;

import com.sepp.nattymals.Application;
import com.sepp.nattymals.domain.PetComment;
import com.sepp.nattymals.repository.PetCommentRepository;
import com.sepp.nattymals.service.PetCommentService;

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
 * Test class for the PetCommentResource REST controller.
 *
 * @see PetCommentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PetCommentResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATION_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATION_DATE);
    private static final String DEFAULT_TEXT = "AAAAA";
    private static final String UPDATED_TEXT = "BBBBB";

    @Inject
    private PetCommentRepository petCommentRepository;

    @Inject
    private PetCommentService petCommentService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPetCommentMockMvc;

    private PetComment petComment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PetCommentResource petCommentResource = new PetCommentResource();
        ReflectionTestUtils.setField(petCommentResource, "petCommentService", petCommentService);
        this.restPetCommentMockMvc = MockMvcBuilders.standaloneSetup(petCommentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        petComment = new PetComment();
        petComment.setCreationDate(DEFAULT_CREATION_DATE);
        petComment.setText(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    public void createPetComment() throws Exception {
        int databaseSizeBeforeCreate = petCommentRepository.findAll().size();

        // Create the PetComment

        restPetCommentMockMvc.perform(post("/api/petComments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(petComment)))
                .andExpect(status().isCreated());

        // Validate the PetComment in the database
        List<PetComment> petComments = petCommentRepository.findAll();
        assertThat(petComments).hasSize(databaseSizeBeforeCreate + 1);
        PetComment testPetComment = petComments.get(petComments.size() - 1);
        assertThat(testPetComment.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testPetComment.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = petCommentRepository.findAll().size();
        // set the field null
        petComment.setCreationDate(null);

        // Create the PetComment, which fails.

        restPetCommentMockMvc.perform(post("/api/petComments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(petComment)))
                .andExpect(status().isBadRequest());

        List<PetComment> petComments = petCommentRepository.findAll();
        assertThat(petComments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = petCommentRepository.findAll().size();
        // set the field null
        petComment.setText(null);

        // Create the PetComment, which fails.

        restPetCommentMockMvc.perform(post("/api/petComments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(petComment)))
                .andExpect(status().isBadRequest());

        List<PetComment> petComments = petCommentRepository.findAll();
        assertThat(petComments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPetComments() throws Exception {
        // Initialize the database
        petCommentRepository.saveAndFlush(petComment);

        // Get all the petComments
        restPetCommentMockMvc.perform(get("/api/petComments?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(petComment.getId().intValue())))
                .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE_STR)))
                .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())));
    }

    @Test
    @Transactional
    public void getPetComment() throws Exception {
        // Initialize the database
        petCommentRepository.saveAndFlush(petComment);

        // Get the petComment
        restPetCommentMockMvc.perform(get("/api/petComments/{id}", petComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(petComment.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE_STR))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPetComment() throws Exception {
        // Get the petComment
        restPetCommentMockMvc.perform(get("/api/petComments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePetComment() throws Exception {
        // Initialize the database
        petCommentRepository.saveAndFlush(petComment);

		int databaseSizeBeforeUpdate = petCommentRepository.findAll().size();

        // Update the petComment
        petComment.setCreationDate(UPDATED_CREATION_DATE);
        petComment.setText(UPDATED_TEXT);

        restPetCommentMockMvc.perform(put("/api/petComments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(petComment)))
                .andExpect(status().isOk());

        // Validate the PetComment in the database
        List<PetComment> petComments = petCommentRepository.findAll();
        assertThat(petComments).hasSize(databaseSizeBeforeUpdate);
        PetComment testPetComment = petComments.get(petComments.size() - 1);
        assertThat(testPetComment.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testPetComment.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void deletePetComment() throws Exception {
        // Initialize the database
        petCommentRepository.saveAndFlush(petComment);

		int databaseSizeBeforeDelete = petCommentRepository.findAll().size();

        // Get the petComment
        restPetCommentMockMvc.perform(delete("/api/petComments/{id}", petComment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PetComment> petComments = petCommentRepository.findAll();
        assertThat(petComments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
