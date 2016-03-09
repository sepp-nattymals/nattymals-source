package com.sepp.nattymals.service;

import com.sepp.nattymals.domain.PetOwner;
import com.sepp.nattymals.repository.PetOwnerRepository;
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
 * Service Implementation for managing PetOwner.
 */
@Service
@Transactional
public class PetOwnerService {

    private final Logger log = LoggerFactory.getLogger(PetOwnerService.class);
    
    @Inject
    private PetOwnerRepository petOwnerRepository;
    
    /**
     * Save a petOwner.
     * @return the persisted entity
     */
    public PetOwner save(PetOwner petOwner) {
        log.debug("Request to save PetOwner : {}", petOwner);
        PetOwner result = petOwnerRepository.save(petOwner);
        return result;
    }

    /**
     *  get all the petOwners.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PetOwner> findAll(Pageable pageable) {
        log.debug("Request to get all PetOwners");
        Page<PetOwner> result = petOwnerRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one petOwner by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PetOwner findOne(Long id) {
        log.debug("Request to get PetOwner : {}", id);
        PetOwner petOwner = petOwnerRepository.findOne(id);
        return petOwner;
    }

    /**
     *  delete the  petOwner by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete PetOwner : {}", id);
        petOwnerRepository.delete(id);
    }
}
