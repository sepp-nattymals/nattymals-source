package com.sepp.nattymals.service;

import com.sepp.nattymals.domain.Pet;
import com.sepp.nattymals.repository.PetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Pet.
 */
@Service
@Transactional
public class PetService {

    private final Logger log = LoggerFactory.getLogger(PetService.class);
    
    @Inject
    private PetRepository petRepository;
    
    /**
     * Save a pet.
     * @return the persisted entity
     */
    public Pet save(Pet pet) {
        log.debug("Request to save Pet : {}", pet);
        Pet result = petRepository.save(pet);
        return result;
    }

    /**
     *  get all the pets.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Pet> findAll(Pageable pageable) {
        log.debug("Request to get all Pets");
        Page<Pet> result = petRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one pet by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Pet findOne(Long id) {
        log.debug("Request to get Pet : {}", id);
        Pet pet = petRepository.findOne(id);
        return pet;
    }

    /**
     *  delete the  pet by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Pet : {}", id);
        petRepository.delete(id);
    }
}
