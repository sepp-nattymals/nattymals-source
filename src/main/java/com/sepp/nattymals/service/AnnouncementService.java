package com.sepp.nattymals.service;

import com.sepp.nattymals.domain.Announcement;
import com.sepp.nattymals.repository.AnnouncementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Announcement.
 */
@Service
@Transactional
public class AnnouncementService {

    private final Logger log = LoggerFactory.getLogger(AnnouncementService.class);
    
    @Inject
    private AnnouncementRepository announcementRepository;
    
    /**
     * Save a announcement.
     * @return the persisted entity
     */
    public Announcement save(Announcement announcement) {
        log.debug("Request to save Announcement : {}", announcement);
        Announcement result = announcementRepository.save(announcement);
        return result;
    }

    /**
     *  get all the announcements.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Announcement> findAll(Pageable pageable) {
        log.debug("Request to get all Announcements");
        Page<Announcement> result = announcementRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one announcement by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Announcement findOne(Long id) {
        log.debug("Request to get Announcement : {}", id);
        Announcement announcement = announcementRepository.findOne(id);
        return announcement;
    }

    /**
     *  delete the  announcement by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Announcement : {}", id);
        announcementRepository.delete(id);
    }
}
