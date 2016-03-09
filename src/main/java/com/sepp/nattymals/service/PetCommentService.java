package com.sepp.nattymals.service;

import com.sepp.nattymals.domain.PetComment;
import com.sepp.nattymals.repository.PetCommentRepository;
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
 * Service Implementation for managing PetComment.
 */
@Service
@Transactional
public class PetCommentService {

    private final Logger log = LoggerFactory.getLogger(PetCommentService.class);
    
    @Inject
    private PetCommentRepository petCommentRepository;
    
    /**
     * Save a petComment.
     * @return the persisted entity
     */
    public PetComment save(PetComment petComment) {
        log.debug("Request to save PetComment : {}", petComment);
        PetComment result = petCommentRepository.save(petComment);
        return result;
    }

    /**
     *  get all the petComments.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PetComment> findAll(Pageable pageable) {
        log.debug("Request to get all PetComments");
        Page<PetComment> result = petCommentRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one petComment by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PetComment findOne(Long id) {
        log.debug("Request to get PetComment : {}", id);
        PetComment petComment = petCommentRepository.findOne(id);
        return petComment;
    }

    /**
     *  delete the  petComment by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete PetComment : {}", id);
        petCommentRepository.delete(id);
    }
}
