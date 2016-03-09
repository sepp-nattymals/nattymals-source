package com.sepp.nattymals.web.rest;

import com.sepp.nattymals.Application;
import com.sepp.nattymals.domain.Folder;
import com.sepp.nattymals.repository.FolderRepository;
import com.sepp.nattymals.service.FolderService;

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
 * Test class for the FolderResource REST controller.
 *
 * @see FolderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FolderResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private FolderRepository folderRepository;

    @Inject
    private FolderService folderService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFolderMockMvc;

    private Folder folder;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FolderResource folderResource = new FolderResource();
        ReflectionTestUtils.setField(folderResource, "folderService", folderService);
        this.restFolderMockMvc = MockMvcBuilders.standaloneSetup(folderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        folder = new Folder();
        folder.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createFolder() throws Exception {
        int databaseSizeBeforeCreate = folderRepository.findAll().size();

        // Create the Folder

        restFolderMockMvc.perform(post("/api/folders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(folder)))
                .andExpect(status().isCreated());

        // Validate the Folder in the database
        List<Folder> folders = folderRepository.findAll();
        assertThat(folders).hasSize(databaseSizeBeforeCreate + 1);
        Folder testFolder = folders.get(folders.size() - 1);
        assertThat(testFolder.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllFolders() throws Exception {
        // Initialize the database
        folderRepository.saveAndFlush(folder);

        // Get all the folders
        restFolderMockMvc.perform(get("/api/folders?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(folder.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getFolder() throws Exception {
        // Initialize the database
        folderRepository.saveAndFlush(folder);

        // Get the folder
        restFolderMockMvc.perform(get("/api/folders/{id}", folder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(folder.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFolder() throws Exception {
        // Get the folder
        restFolderMockMvc.perform(get("/api/folders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFolder() throws Exception {
        // Initialize the database
        folderRepository.saveAndFlush(folder);

		int databaseSizeBeforeUpdate = folderRepository.findAll().size();

        // Update the folder
        folder.setName(UPDATED_NAME);

        restFolderMockMvc.perform(put("/api/folders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(folder)))
                .andExpect(status().isOk());

        // Validate the Folder in the database
        List<Folder> folders = folderRepository.findAll();
        assertThat(folders).hasSize(databaseSizeBeforeUpdate);
        Folder testFolder = folders.get(folders.size() - 1);
        assertThat(testFolder.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteFolder() throws Exception {
        // Initialize the database
        folderRepository.saveAndFlush(folder);

		int databaseSizeBeforeDelete = folderRepository.findAll().size();

        // Get the folder
        restFolderMockMvc.perform(delete("/api/folders/{id}", folder.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Folder> folders = folderRepository.findAll();
        assertThat(folders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
