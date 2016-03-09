package com.sepp.nattymals.repository;

import com.sepp.nattymals.domain.Clinic;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Clinic entity.
 */
public interface ClinicRepository extends JpaRepository<Clinic,Long> {

}
