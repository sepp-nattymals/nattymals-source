package com.sepp.nattymals.repository;

import com.sepp.nattymals.domain.Actor;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Actor entity.
 */
public interface ActorRepository extends JpaRepository<Actor,Long> {

}
