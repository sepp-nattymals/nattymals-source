package com.sepp.nattymals.repository;

import com.sepp.nattymals.domain.Payment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Payment entity.
 */
public interface PaymentRepository extends JpaRepository<Payment,Long> {

}
