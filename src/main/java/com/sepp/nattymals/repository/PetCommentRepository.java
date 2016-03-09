package com.sepp.nattymals.repository;

import com.sepp.nattymals.domain.PetComment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PetComment entity.
 */
public interface PetCommentRepository extends JpaRepository<PetComment,Long> {

}
