package com.sepp.nattymals.repository;

import com.sepp.nattymals.domain.Pet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pet entity.
 */
public interface PetRepository extends JpaRepository<Pet,Long> {

//	Page<Pet> findAllByType(Pageable pageable);

}
