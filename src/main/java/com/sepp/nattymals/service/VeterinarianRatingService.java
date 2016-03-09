package com.sepp.nattymals.service;

import com.sepp.nattymals.domain.VeterinarianRating;
import com.sepp.nattymals.repository.VeterinarianRatingRepository;
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
 * Service Implementation for managing VeterinarianRating.
 */
@Service
@Transactional
public class VeterinarianRatingService {

    private final Logger log = LoggerFactory.getLogger(VeterinarianRatingService.class);
    
    @Inject
    private VeterinarianRatingRepository veterinarianRatingRepository;
    
    /**
     * Save a veterinarianRating.
     * @return the persisted entity
     */
    public VeterinarianRating save(VeterinarianRating veterinarianRating) {
        log.debug("Request to save VeterinarianRating : {}", veterinarianRating);
        VeterinarianRating result = veterinarianRatingRepository.save(veterinarianRating);
        return result;
    }

    /**
     *  get all the veterinarianRatings.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<VeterinarianRating> findAll(Pageable pageable) {
        log.debug("Request to get all VeterinarianRatings");
        Page<VeterinarianRating> result = veterinarianRatingRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one veterinarianRating by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public VeterinarianRating findOne(Long id) {
        log.debug("Request to get VeterinarianRating : {}", id);
        VeterinarianRating veterinarianRating = veterinarianRatingRepository.findOne(id);
        return veterinarianRating;
    }

    /**
     *  delete the  veterinarianRating by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete VeterinarianRating : {}", id);
        veterinarianRatingRepository.delete(id);
    }
}
