package com.sepp.nattymals.repository;

import com.sepp.nattymals.domain.PetRating;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PetRating entity.
 */
public interface PetRatingRepository extends JpaRepository<PetRating,Long> {

}
