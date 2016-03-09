package com.sepp.nattymals.repository;

import com.sepp.nattymals.domain.Adoption;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Adoption entity.
 */
public interface AdoptionRepository extends JpaRepository<Adoption,Long> {

}
