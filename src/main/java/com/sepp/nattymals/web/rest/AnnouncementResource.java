package com.sepp.nattymals.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sepp.nattymals.domain.Announcement;
import com.sepp.nattymals.service.AnnouncementService;
import com.sepp.nattymals.web.rest.util.HeaderUtil;
import com.sepp.nattymals.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Announcement.
 */
@RestController
@RequestMapping("/api")
public class AnnouncementResource {

    private final Logger log = LoggerFactory.getLogger(AnnouncementResource.class);
        
    @Inject
    private AnnouncementService announcementService;
    
    /**
     * POST  /announcements -> Create a new announcement.
     */
    @RequestMapping(value = "/announcements",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Announcement> createAnnouncement(@Valid @RequestBody Announcement announcement) throws URISyntaxException {
        log.debug("REST request to save Announcement : {}", announcement);
        if (announcement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("announcement", "idexists", "A new announcement cannot already have an ID")).body(null);
        }
        Announcement result = announcementService.save(announcement);
        return ResponseEntity.created(new URI("/api/announcements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("announcement", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /announcements -> Updates an existing announcement.
     */
    @RequestMapping(value = "/announcements",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Announcement> updateAnnouncement(@Valid @RequestBody Announcement announcement) throws URISyntaxException {
        log.debug("REST request to update Announcement : {}", announcement);
        if (announcement.getId() == null) {
            return createAnnouncement(announcement);
        }
        Announcement result = announcementService.save(announcement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("announcement", announcement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /announcements -> get all the announcements.
     */
    @RequestMapping(value = "/announcements",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Announcement>> getAllAnnouncements(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Announcements");
        Page<Announcement> page = announcementService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/announcements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /announcements/:id -> get the "id" announcement.
     */
    @RequestMapping(value = "/announcements/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Announcement> getAnnouncement(@PathVariable Long id) {
        log.debug("REST request to get Announcement : {}", id);
        Announcement announcement = announcementService.findOne(id);
        return Optional.ofNullable(announcement)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /announcements/:id -> delete the "id" announcement.
     */
    @RequestMapping(value = "/announcements/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        log.debug("REST request to delete Announcement : {}", id);
        announcementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("announcement", id.toString())).build();
    }
}
