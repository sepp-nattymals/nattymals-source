package com.sepp.nattymals.repository;

import com.sepp.nattymals.domain.Message;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Message entity.
 */
public interface MessageRepository extends JpaRepository<Message,Long> {

}
