package com.sepp.nattymals.repository;

import com.sepp.nattymals.domain.VeterinarianRating;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the VeterinarianRating entity.
 */
public interface VeterinarianRatingRepository extends JpaRepository<VeterinarianRating,Long> {

}
