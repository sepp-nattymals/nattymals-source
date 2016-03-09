package com.sepp.nattymals.repository;

import com.sepp.nattymals.domain.PetCompany;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PetCompany entity.
 */
public interface PetCompanyRepository extends JpaRepository<PetCompany,Long> {

}
