package com.sepp.nattymals.service;

import com.sepp.nattymals.domain.PetCompany;
import com.sepp.nattymals.repository.PetCompanyRepository;
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
 * Service Implementation for managing PetCompany.
 */
@Service
@Transactional
public class PetCompanyService {

    private final Logger log = LoggerFactory.getLogger(PetCompanyService.class);
    
    @Inject
    private PetCompanyRepository petCompanyRepository;
    
    /**
     * Save a petCompany.
     * @return the persisted entity
     */
    public PetCompany save(PetCompany petCompany) {
        log.debug("Request to save PetCompany : {}", petCompany);
        PetCompany result = petCompanyRepository.save(petCompany);
        return result;
    }

    /**
     *  get all the petCompanys.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PetCompany> findAll(Pageable pageable) {
        log.debug("Request to get all PetCompanys");
        Page<PetCompany> result = petCompanyRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one petCompany by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PetCompany findOne(Long id) {
        log.debug("Request to get PetCompany : {}", id);
        PetCompany petCompany = petCompanyRepository.findOne(id);
        return petCompany;
    }

    /**
     *  delete the  petCompany by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete PetCompany : {}", id);
        petCompanyRepository.delete(id);
    }
}
