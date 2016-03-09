package com.sepp.nattymals.repository;

import com.sepp.nattymals.domain.Announcement;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Announcement entity.
 */
public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {

}
