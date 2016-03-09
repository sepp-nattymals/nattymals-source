package com.sepp.nattymals.repository;

import com.sepp.nattymals.domain.Administrator;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Administrator entity.
 */
public interface AdministratorRepository extends JpaRepository<Administrator,Long> {

}
