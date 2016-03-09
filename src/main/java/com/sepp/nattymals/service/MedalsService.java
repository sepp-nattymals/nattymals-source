package com.sepp.nattymals.service;

import com.sepp.nattymals.domain.Medals;
import com.sepp.nattymals.repository.MedalsRepository;
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
 * Service Implementation for managing Medals.
 */
@Service
@Transactional
public class MedalsService {

    private final Logger log = LoggerFactory.getLogger(MedalsService.class);
    
    @Inject
    private MedalsRepository medalsRepository;
    
    /**
     * Save a medals.
     * @return the persisted entity
     */
    public Medals save(Medals medals) {
        log.debug("Request to save Medals : {}", medals);
        Medals result = medalsRepository.save(medals);
        return result;
    }

    /**
     *  get all the medalss.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Medals> findAll(Pageable pageable) {
        log.debug("Request to get all Medalss");
        Page<Medals> result = medalsRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one medals by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Medals findOne(Long id) {
        log.debug("Request to get Medals : {}", id);
        Medals medals = medalsRepository.findOne(id);
        return medals;
    }

    /**
     *  delete the  medals by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Medals : {}", id);
        medalsRepository.delete(id);
    }
}
