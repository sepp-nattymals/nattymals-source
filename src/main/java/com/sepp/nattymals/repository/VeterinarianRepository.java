package com.sepp.nattymals.repository;

import com.sepp.nattymals.domain.Veterinarian;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Veterinarian entity.
 */
public interface VeterinarianRepository extends JpaRepository<Veterinarian,Long> {

}
