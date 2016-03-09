package com.sepp.nattymals.repository;

import com.sepp.nattymals.domain.Folder;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Folder entity.
 */
public interface FolderRepository extends JpaRepository<Folder,Long> {

}
