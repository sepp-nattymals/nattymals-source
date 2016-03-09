package com.sepp.nattymals.service;

import com.sepp.nattymals.domain.VeterinarianComment;
import com.sepp.nattymals.repository.VeterinarianCommentRepository;
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
 * Service Implementation for managing VeterinarianComment.
 */
@Service
@Transactional
public class VeterinarianCommentService {

    private final Logger log = LoggerFactory.getLogger(VeterinarianCommentService.class);
    
    @Inject
    private VeterinarianCommentRepository veterinarianCommentRepository;
    
    /**
     * Save a veterinarianComment.
     * @return the persisted entity
     */
    public VeterinarianComment save(VeterinarianComment veterinarianComment) {
        log.debug("Request to save VeterinarianComment : {}", veterinarianComment);
        VeterinarianComment result = veterinarianCommentRepository.save(veterinarianComment);
        return result;
    }

    /**
     *  get all the veterinarianComments.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<VeterinarianComment> findAll(Pageable pageable) {
        log.debug("Request to get all VeterinarianComments");
        Page<VeterinarianComment> result = veterinarianCommentRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one veterinarianComment by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public VeterinarianComment findOne(Long id) {
        log.debug("Request to get VeterinarianComment : {}", id);
        VeterinarianComment veterinarianComment = veterinarianCommentRepository.findOne(id);
        return veterinarianComment;
    }

    /**
     *  delete the  veterinarianComment by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete VeterinarianComment : {}", id);
        veterinarianCommentRepository.delete(id);
    }
}
