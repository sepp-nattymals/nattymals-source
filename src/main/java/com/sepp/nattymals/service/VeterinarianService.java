package com.sepp.nattymals.service;

import com.sepp.nattymals.domain.Veterinarian;
import com.sepp.nattymals.repository.VeterinarianRepository;
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
 * Service Implementation for managing Veterinarian.
 */
@Service
@Transactional
public class VeterinarianService {

    private final Logger log = LoggerFactory.getLogger(VeterinarianService.class);
    
    @Inject
    private VeterinarianRepository veterinarianRepository;
    
    /**
     * Save a veterinarian.
     * @return the persisted entity
     */
    public Veterinarian save(Veterinarian veterinarian) {
        log.debug("Request to save Veterinarian : {}", veterinarian);
        Veterinarian result = veterinarianRepository.save(veterinarian);
        return result;
    }

    /**
     *  get all the veterinarians.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Veterinarian> findAll(Pageable pageable) {
        log.debug("Request to get all Veterinarians");
        Page<Veterinarian> result = veterinarianRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one veterinarian by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Veterinarian findOne(Long id) {
        log.debug("Request to get Veterinarian : {}", id);
        Veterinarian veterinarian = veterinarianRepository.findOne(id);
        return veterinarian;
    }

    /**
     *  delete the  veterinarian by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Veterinarian : {}", id);
        veterinarianRepository.delete(id);
    }
}
