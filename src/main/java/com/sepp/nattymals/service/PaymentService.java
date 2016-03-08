package com.sepp.nattymals.service;

import com.sepp.nattymals.domain.Payment;
import com.sepp.nattymals.repository.PaymentRepository;
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
 * Service Implementation for managing Payment.
 */
@Service
@Transactional
public class PaymentService {

    private final Logger log = LoggerFactory.getLogger(PaymentService.class);
    
    @Inject
    private PaymentRepository paymentRepository;
    
    /**
     * Save a payment.
     * @return the persisted entity
     */
    public Payment save(Payment payment) {
        log.debug("Request to save Payment : {}", payment);
        Payment result = paymentRepository.save(payment);
        return result;
    }

    /**
     *  get all the payments.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Payment> findAll(Pageable pageable) {
        log.debug("Request to get all Payments");
        Page<Payment> result = paymentRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one payment by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Payment findOne(Long id) {
        log.debug("Request to get Payment : {}", id);
        Payment payment = paymentRepository.findOne(id);
        return payment;
    }

    /**
     *  delete the  payment by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Payment : {}", id);
        paymentRepository.delete(id);
    }
}
