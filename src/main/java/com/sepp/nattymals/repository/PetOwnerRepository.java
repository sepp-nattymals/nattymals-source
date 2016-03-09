package com.sepp.nattymals.repository;

import com.sepp.nattymals.domain.PetOwner;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PetOwner entity.
 */
public interface PetOwnerRepository extends JpaRepository<PetOwner,Long> {

}
