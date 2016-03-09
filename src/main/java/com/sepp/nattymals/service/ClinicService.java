package com.sepp.nattymals.service;

import com.sepp.nattymals.domain.Clinic;
import com.sepp.nattymals.repository.ClinicRepository;
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
 * Service Implementation for managing Clinic.
 */
@Service
@Transactional
public class ClinicService {

    private final Logger log = LoggerFactory.getLogger(ClinicService.class);
    
    @Inject
    private ClinicRepository clinicRepository;
    
    /**
     * Save a clinic.
     * @return the persisted entity
     */
    public Clinic save(Clinic clinic) {
        log.debug("Request to save Clinic : {}", clinic);
        Clinic result = clinicRepository.save(clinic);
        return result;
    }

    /**
     *  get all the clinics.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Clinic> findAll(Pageable pageable) {
        log.debug("Request to get all Clinics");
        Page<Clinic> result = clinicRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one clinic by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Clinic findOne(Long id) {
        log.debug("Request to get Clinic : {}", id);
        Clinic clinic = clinicRepository.findOne(id);
        return clinic;
    }

    /**
     *  delete the  clinic by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Clinic : {}", id);
        clinicRepository.delete(id);
    }
}
