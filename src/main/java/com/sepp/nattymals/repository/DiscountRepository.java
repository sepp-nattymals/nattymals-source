package com.sepp.nattymals.repository;

import com.sepp.nattymals.domain.Discount;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.Collection;
import java.util.List;

/**
 * Spring Data JPA repository for the Discount entity.
 */
public interface DiscountRepository extends JpaRepository<Discount,Long> {
	
		
	@Query("select d from Discount d where d.companyName = ?1")
	public Page<Discount> findCompanyDiscountByName(String companyName, Pageable pageable);

}
