package com.sepp.nattymals.repository;

import com.sepp.nattymals.domain.Actor;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Actor entity.
 */
public interface ActorRepository extends JpaRepository<Actor,Long> {
	
	@Query("select a from Actor a where a.user.id = ?1")
	Actor getPrincipal(Long id);

}
