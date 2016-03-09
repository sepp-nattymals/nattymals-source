package com.sepp.nattymals.repository;

import com.sepp.nattymals.domain.Medals;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Medals entity.
 */
public interface MedalsRepository extends JpaRepository<Medals,Long> {

}
