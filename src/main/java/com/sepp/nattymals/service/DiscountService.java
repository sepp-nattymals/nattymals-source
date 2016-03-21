package com.sepp.nattymals.service;

import com.sepp.nattymals.domain.Discount;
import com.sepp.nattymals.repository.DiscountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Discount.
 */
@Service
@Transactional
public class DiscountService {

    private final Logger log = LoggerFactory.getLogger(DiscountService.class);
    
    @Inject
    private DiscountRepository discountRepository;
    
    /**
     * Save a discount.
     * @return the persisted entity
     */
    public Discount save(Discount discount) {
        log.debug("Request to save Discount : {}", discount);
        Discount result = discountRepository.save(discount);
        return result;
    }

    /**
     *  get all the discounts.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Discount> findAll(Pageable pageable) {
        log.debug("Request to get all Discounts");
        Page<Discount> result = discountRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one discount by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Discount findOne(Long id) {
        log.debug("Request to get Discount : {}", id);
        Discount discount = discountRepository.findOne(id);
        return discount;
    }

    /**
     *  delete the  discount by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Discount : {}", id);
        discountRepository.delete(id);
    }
    
    
    /**
     * Get all the discounts from a company name 
     */
    public Page<Discount> findCompanyDiscountByName(String companyName, Pageable pageable){
    	
    	log.debug("Request to get all CompanyName discount: {}", companyName);
    	Page<Discount> result;
    	
    	result = discountRepository.findCompanyDiscountByName(companyName, pageable);
    	
    	return result;
    }
}
