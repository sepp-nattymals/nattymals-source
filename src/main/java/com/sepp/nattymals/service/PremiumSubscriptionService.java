package com.sepp.nattymals.service;

import com.sepp.nattymals.domain.PremiumSubscription;
import com.sepp.nattymals.repository.PremiumSubscriptionRepository;
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
 * Service Implementation for managing PremiumSubscription.
 */
@Service
@Transactional
public class PremiumSubscriptionService {

    private final Logger log = LoggerFactory.getLogger(PremiumSubscriptionService.class);
    
    @Inject
    private PremiumSubscriptionRepository premiumSubscriptionRepository;
    
    /**
     * Save a premiumSubscription.
     * @return the persisted entity
     */
    public PremiumSubscription save(PremiumSubscription premiumSubscription) {
        log.debug("Request to save PremiumSubscription : {}", premiumSubscription);
        PremiumSubscription result = premiumSubscriptionRepository.save(premiumSubscription);
        return result;
    }

    /**
     *  get all the premiumSubscriptions.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PremiumSubscription> findAll(Pageable pageable) {
        log.debug("Request to get all PremiumSubscriptions");
        Page<PremiumSubscription> result = premiumSubscriptionRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one premiumSubscription by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PremiumSubscription findOne(Long id) {
        log.debug("Request to get PremiumSubscription : {}", id);
        PremiumSubscription premiumSubscription = premiumSubscriptionRepository.findOne(id);
        return premiumSubscription;
    }

    /**
     *  delete the  premiumSubscription by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete PremiumSubscription : {}", id);
        premiumSubscriptionRepository.delete(id);
    }
}
