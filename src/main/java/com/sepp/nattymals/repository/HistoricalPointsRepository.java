package com.sepp.nattymals.repository;

import com.sepp.nattymals.domain.HistoricalPoints;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the HistoricalPoints entity.
 */
public interface HistoricalPointsRepository extends JpaRepository<HistoricalPoints,Long> {

}
