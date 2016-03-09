package com.sepp.nattymals.service;

import com.sepp.nattymals.domain.PetRating;
import com.sepp.nattymals.repository.PetRatingRepository;
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
 * Service Implementation for managing PetRating.
 */
@Service
@Transactional
public class PetRatingService {

    private final Logger log = LoggerFactory.getLogger(PetRatingService.class);
    
    @Inject
    private PetRatingRepository petRatingRepository;
    
    /**
     * Save a petRating.
     * @return the persisted entity
     */
    public PetRating save(PetRating petRating) {
        log.debug("Request to save PetRating : {}", petRating);
        PetRating result = petRatingRepository.save(petRating);
        return result;
    }

    /**
     *  get all the petRatings.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PetRating> findAll(Pageable pageable) {
        log.debug("Request to get all PetRatings");
        Page<PetRating> result = petRatingRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one petRating by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PetRating findOne(Long id) {
        log.debug("Request to get PetRating : {}", id);
        PetRating petRating = petRatingRepository.findOne(id);
        return petRating;
    }

    /**
     *  delete the  petRating by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete PetRating : {}", id);
        petRatingRepository.delete(id);
    }
}
