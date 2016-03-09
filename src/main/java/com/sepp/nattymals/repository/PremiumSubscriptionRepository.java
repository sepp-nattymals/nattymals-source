package com.sepp.nattymals.repository;

import com.sepp.nattymals.domain.PremiumSubscription;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PremiumSubscription entity.
 */
public interface PremiumSubscriptionRepository extends JpaRepository<PremiumSubscription,Long> {

}
