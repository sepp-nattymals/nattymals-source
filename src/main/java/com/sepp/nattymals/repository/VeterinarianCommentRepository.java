package com.sepp.nattymals.repository;

import com.sepp.nattymals.domain.VeterinarianComment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the VeterinarianComment entity.
 */
public interface VeterinarianCommentRepository extends JpaRepository<VeterinarianComment,Long> {

}
