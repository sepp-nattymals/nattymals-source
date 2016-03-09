package com.sepp.nattymals.service;

import com.sepp.nattymals.domain.Contract;
import com.sepp.nattymals.repository.ContractRepository;
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
 * Service Implementation for managing Contract.
 */
@Service
@Transactional
public class ContractService {

    private final Logger log = LoggerFactory.getLogger(ContractService.class);
    
    @Inject
    private ContractRepository contractRepository;
    
    /**
     * Save a contract.
     * @return the persisted entity
     */
    public Contract save(Contract contract) {
        log.debug("Request to save Contract : {}", contract);
        Contract result = contractRepository.save(contract);
        return result;
    }

    /**
     *  get all the contracts.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Contract> findAll(Pageable pageable) {
        log.debug("Request to get all Contracts");
        Page<Contract> result = contractRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one contract by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Contract findOne(Long id) {
        log.debug("Request to get Contract : {}", id);
        Contract contract = contractRepository.findOne(id);
        return contract;
    }

    /**
     *  delete the  contract by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Contract : {}", id);
        contractRepository.delete(id);
    }
}
