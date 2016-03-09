package com.sepp.nattymals.service;

import com.sepp.nattymals.domain.Administrator;
import com.sepp.nattymals.repository.AdministratorRepository;
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
 * Service Implementation for managing Administrator.
 */
@Service
@Transactional
public class AdministratorService {

    private final Logger log = LoggerFactory.getLogger(AdministratorService.class);
    
    @Inject
    private AdministratorRepository administratorRepository;
    
    /**
     * Save a administrator.
     * @return the persisted entity
     */
    public Administrator save(Administrator administrator) {
        log.debug("Request to save Administrator : {}", administrator);
        Administrator result = administratorRepository.save(administrator);
        return result;
    }

    /**
     *  get all the administrators.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Administrator> findAll(Pageable pageable) {
        log.debug("Request to get all Administrators");
        Page<Administrator> result = administratorRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one administrator by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Administrator findOne(Long id) {
        log.debug("Request to get Administrator : {}", id);
        Administrator administrator = administratorRepository.findOne(id);
        return administrator;
    }

    /**
     *  delete the  administrator by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Administrator : {}", id);
        administratorRepository.delete(id);
    }
}
