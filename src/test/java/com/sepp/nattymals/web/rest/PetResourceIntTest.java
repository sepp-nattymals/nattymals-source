package com.sepp.nattymals.web.rest;

import com.sepp.nattymals.Application;
import com.sepp.nattymals.domain.Pet;
import com.sepp.nattymals.repository.PetRepository;
import com.sepp.nattymals.service.PetService;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PetResource REST controller.
 *
 * @see PetResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PetResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAA";
    private static final String UPDATED_TYPE = "BBBBB";
    private static final String DEFAULT_SEX = "AAAAA";
    private static final String UPDATED_SEX = "BBBBB";

    private static final Double DEFAULT_WEIGHT = 0D;
    private static final Double UPDATED_WEIGHT = 1D;

    private static final Boolean DEFAULT_HAS_PEDIGREE = false;
    private static final Boolean UPDATED_HAS_PEDIGREE = true;
    private static final String DEFAULT_RACE = "AAAAA";
    private static final String UPDATED_RACE = "BBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_DATING = false;
    private static final Boolean UPDATED_DATING = true;
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private PetRepository petRepository;

    @Inject
    private PetService petService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPetMockMvc;

    private Pet pet;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PetResource petResource = new PetResource();
        ReflectionTestUtils.setField(petResource, "petService", petService);
        this.restPetMockMvc = MockMvcBuilders.standaloneSetup(petResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pet = new Pet();
        pet.setType(DEFAULT_TYPE);
        pet.setSex(DEFAULT_SEX);
        pet.setWeight(DEFAULT_WEIGHT);
        pet.setHasPedigree(DEFAULT_HAS_PEDIGREE);
        pet.setRace(DEFAULT_RACE);
        pet.setBirthDate(DEFAULT_BIRTH_DATE);
        pet.setPhoto(DEFAULT_PHOTO);
        pet.setPhotoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        pet.setDating(DEFAULT_DATING);
        pet.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPet() throws Exception {
        int databaseSizeBeforeCreate = petRepository.findAll().size();

        // Create the Pet

        restPetMockMvc.perform(post("/api/pets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pet)))
                .andExpect(status().isCreated());

        // Validate the Pet in the database
        List<Pet> pets = petRepository.findAll();
        assertThat(pets).hasSize(databaseSizeBeforeCreate + 1);
        Pet testPet = pets.get(pets.size() - 1);
        assertThat(testPet.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPet.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testPet.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testPet.getHasPedigree()).isEqualTo(DEFAULT_HAS_PEDIGREE);
        assertThat(testPet.getRace()).isEqualTo(DEFAULT_RACE);
        assertThat(testPet.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testPet.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testPet.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testPet.getDating()).isEqualTo(DEFAULT_DATING);
        assertThat(testPet.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = petRepository.findAll().size();
        // set the field null
        pet.setType(null);

        // Create the Pet, which fails.

        restPetMockMvc.perform(post("/api/pets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pet)))
                .andExpect(status().isBadRequest());

        List<Pet> pets = petRepository.findAll();
        assertThat(pets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexIsRequired() throws Exception {
        int databaseSizeBeforeTest = petRepository.findAll().size();
        // set the field null
        pet.setSex(null);

        // Create the Pet, which fails.

        restPetMockMvc.perform(post("/api/pets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pet)))
                .andExpect(status().isBadRequest());

        List<Pet> pets = petRepository.findAll();
        assertThat(pets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = petRepository.findAll().size();
        // set the field null
        pet.setWeight(null);

        // Create the Pet, which fails.

        restPetMockMvc.perform(post("/api/pets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pet)))
                .andExpect(status().isBadRequest());

        List<Pet> pets = petRepository.findAll();
        assertThat(pets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHasPedigreeIsRequired() throws Exception {
        int databaseSizeBeforeTest = petRepository.findAll().size();
        // set the field null
        pet.setHasPedigree(null);

        // Create the Pet, which fails.

        restPetMockMvc.perform(post("/api/pets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pet)))
                .andExpect(status().isBadRequest());

        List<Pet> pets = petRepository.findAll();
        assertThat(pets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRaceIsRequired() throws Exception {
        int databaseSizeBeforeTest = petRepository.findAll().size();
        // set the field null
        pet.setRace(null);

        // Create the Pet, which fails.

        restPetMockMvc.perform(post("/api/pets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pet)))
                .andExpect(status().isBadRequest());

        List<Pet> pets = petRepository.findAll();
        assertThat(pets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = petRepository.findAll().size();
        // set the field null
        pet.setBirthDate(null);

        // Create the Pet, which fails.

        restPetMockMvc.perform(post("/api/pets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pet)))
                .andExpect(status().isBadRequest());

        List<Pet> pets = petRepository.findAll();
        assertThat(pets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatingIsRequired() throws Exception {
        int databaseSizeBeforeTest = petRepository.findAll().size();
        // set the field null
        pet.setDating(null);

        // Create the Pet, which fails.

        restPetMockMvc.perform(post("/api/pets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pet)))
                .andExpect(status().isBadRequest());

        List<Pet> pets = petRepository.findAll();
        assertThat(pets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = petRepository.findAll().size();
        // set the field null
        pet.setName(null);

        // Create the Pet, which fails.

        restPetMockMvc.perform(post("/api/pets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pet)))
                .andExpect(status().isBadRequest());

        List<Pet> pets = petRepository.findAll();
        assertThat(pets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPets() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get all the pets
        restPetMockMvc.perform(get("/api/pets?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pet.getId().intValue())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
                .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
                .andExpect(jsonPath("$.[*].hasPedigree").value(hasItem(DEFAULT_HAS_PEDIGREE.booleanValue())))
                .andExpect(jsonPath("$.[*].race").value(hasItem(DEFAULT_RACE.toString())))
                .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
                .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
                .andExpect(jsonPath("$.[*].dating").value(hasItem(DEFAULT_DATING.booleanValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPet() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get the pet
        restPetMockMvc.perform(get("/api/pets/{id}", pet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pet.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.hasPedigree").value(DEFAULT_HAS_PEDIGREE.booleanValue()))
            .andExpect(jsonPath("$.race").value(DEFAULT_RACE.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.dating").value(DEFAULT_DATING.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPet() throws Exception {
        // Get the pet
        restPetMockMvc.perform(get("/api/pets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePet() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

		int databaseSizeBeforeUpdate = petRepository.findAll().size();

        // Update the pet
        pet.setType(UPDATED_TYPE);
        pet.setSex(UPDATED_SEX);
        pet.setWeight(UPDATED_WEIGHT);
        pet.setHasPedigree(UPDATED_HAS_PEDIGREE);
        pet.setRace(UPDATED_RACE);
        pet.setBirthDate(UPDATED_BIRTH_DATE);
        pet.setPhoto(UPDATED_PHOTO);
        pet.setPhotoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        pet.setDating(UPDATED_DATING);
        pet.setName(UPDATED_NAME);

        restPetMockMvc.perform(put("/api/pets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pet)))
                .andExpect(status().isOk());

        // Validate the Pet in the database
        List<Pet> pets = petRepository.findAll();
        assertThat(pets).hasSize(databaseSizeBeforeUpdate);
        Pet testPet = pets.get(pets.size() - 1);
        assertThat(testPet.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPet.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testPet.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testPet.getHasPedigree()).isEqualTo(UPDATED_HAS_PEDIGREE);
        assertThat(testPet.getRace()).isEqualTo(UPDATED_RACE);
        assertThat(testPet.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testPet.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPet.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testPet.getDating()).isEqualTo(UPDATED_DATING);
        assertThat(testPet.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deletePet() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

		int databaseSizeBeforeDelete = petRepository.findAll().size();

        // Get the pet
        restPetMockMvc.perform(delete("/api/pets/{id}", pet.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Pet> pets = petRepository.findAll();
        assertThat(pets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
