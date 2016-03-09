package com.sepp.nattymals.repository;

import com.sepp.nattymals.domain.Contract;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Contract entity.
 */
public interface ContractRepository extends JpaRepository<Contract,Long> {

}
