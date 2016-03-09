package com.sepp.nattymals.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sepp.nattymals.domain.VeterinarianRating;
import com.sepp.nattymals.service.VeterinarianRatingService;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing VeterinarianRating.
 */
@RestController
@RequestMapping("/api")
public class VeterinarianRatingResource {

    private final Logger log = LoggerFactory.getLogger(VeterinarianRatingResource.class);
        
    @Inject
    private VeterinarianRatingService veterinarianRatingService;
    
    /**
     * POST  /veterinarianRatings -> Create a new veterinarianRating.
     */
    @RequestMapping(value = "/veterinarianRatings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VeterinarianRating> createVeterinarianRating(@RequestBody VeterinarianRating veterinarianRating) throws URISyntaxException {
        log.debug("REST request to save VeterinarianRating : {}", veterinarianRating);
        if (veterinarianRating.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("veterinarianRating", "idexists", "A new veterinarianRating cannot already have an ID")).body(null);
        }
        VeterinarianRating result = veterinarianRatingService.save(veterinarianRating);
        return ResponseEntity.created(new URI("/api/veterinarianRatings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("veterinarianRating", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /veterinarianRatings -> Updates an existing veterinarianRating.
     */
    @RequestMapping(value = "/veterinarianRatings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VeterinarianRating> updateVeterinarianRating(@RequestBody VeterinarianRating veterinarianRating) throws URISyntaxException {
        log.debug("REST request to update VeterinarianRating : {}", veterinarianRating);
        if (veterinarianRating.getId() == null) {
            return createVeterinarianRating(veterinarianRating);
        }
        VeterinarianRating result = veterinarianRatingService.save(veterinarianRating);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("veterinarianRating", veterinarianRating.getId().toString()))
            .body(result);
    }

    /**
     * GET  /veterinarianRatings -> get all the veterinarianRatings.
     */
    @RequestMapping(value = "/veterinarianRatings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<VeterinarianRating>> getAllVeterinarianRatings(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of VeterinarianRatings");
        Page<VeterinarianRating> page = veterinarianRatingService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/veterinarianRatings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /veterinarianRatings/:id -> get the "id" veterinarianRating.
     */
    @RequestMapping(value = "/veterinarianRatings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VeterinarianRating> getVeterinarianRating(@PathVariable Long id) {
        log.debug("REST request to get VeterinarianRating : {}", id);
        VeterinarianRating veterinarianRating = veterinarianRatingService.findOne(id);
        return Optional.ofNullable(veterinarianRating)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /veterinarianRatings/:id -> delete the "id" veterinarianRating.
     */
    @RequestMapping(value = "/veterinarianRatings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVeterinarianRating(@PathVariable Long id) {
        log.debug("REST request to delete VeterinarianRating : {}", id);
        veterinarianRatingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("veterinarianRating", id.toString())).build();
    }
}
