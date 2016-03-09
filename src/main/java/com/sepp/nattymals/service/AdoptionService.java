package com.sepp.nattymals.service;

import com.sepp.nattymals.domain.Adoption;
import com.sepp.nattymals.repository.AdoptionRepository;
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
 * Service Implementation for managing Adoption.
 */
@Service
@Transactional
public class AdoptionService {

    private final Logger log = LoggerFactory.getLogger(AdoptionService.class);
    
    @Inject
    private AdoptionRepository adoptionRepository;
    
    /**
     * Save a adoption.
     * @return the persisted entity
     */
    public Adoption save(Adoption adoption) {
        log.debug("Request to save Adoption : {}", adoption);
        Adoption result = adoptionRepository.save(adoption);
        return result;
    }

    /**
     *  get all the adoptions.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Adoption> findAll(Pageable pageable) {
        log.debug("Request to get all Adoptions");
        Page<Adoption> result = adoptionRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one adoption by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Adoption findOne(Long id) {
        log.debug("Request to get Adoption : {}", id);
        Adoption adoption = adoptionRepository.findOne(id);
        return adoption;
    }

    /**
     *  delete the  adoption by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Adoption : {}", id);
        adoptionRepository.delete(id);
    }
}
