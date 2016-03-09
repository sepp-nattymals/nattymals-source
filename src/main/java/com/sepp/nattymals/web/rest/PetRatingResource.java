package com.sepp.nattymals.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sepp.nattymals.domain.PetRating;
import com.sepp.nattymals.service.PetRatingService;
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
 * REST controller for managing PetRating.
 */
@RestController
@RequestMapping("/api")
public class PetRatingResource {

    private final Logger log = LoggerFactory.getLogger(PetRatingResource.class);
        
    @Inject
    private PetRatingService petRatingService;
    
    /**
     * POST  /petRatings -> Create a new petRating.
     */
    @RequestMapping(value = "/petRatings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PetRating> createPetRating(@Valid @RequestBody PetRating petRating) throws URISyntaxException {
        log.debug("REST request to save PetRating : {}", petRating);
        if (petRating.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("petRating", "idexists", "A new petRating cannot already have an ID")).body(null);
        }
        PetRating result = petRatingService.save(petRating);
        return ResponseEntity.created(new URI("/api/petRatings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("petRating", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /petRatings -> Updates an existing petRating.
     */
    @RequestMapping(value = "/petRatings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PetRating> updatePetRating(@Valid @RequestBody PetRating petRating) throws URISyntaxException {
        log.debug("REST request to update PetRating : {}", petRating);
        if (petRating.getId() == null) {
            return createPetRating(petRating);
        }
        PetRating result = petRatingService.save(petRating);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("petRating", petRating.getId().toString()))
            .body(result);
    }

    /**
     * GET  /petRatings -> get all the petRatings.
     */
    @RequestMapping(value = "/petRatings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PetRating>> getAllPetRatings(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PetRatings");
        Page<PetRating> page = petRatingService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/petRatings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /petRatings/:id -> get the "id" petRating.
     */
    @RequestMapping(value = "/petRatings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PetRating> getPetRating(@PathVariable Long id) {
        log.debug("REST request to get PetRating : {}", id);
        PetRating petRating = petRatingService.findOne(id);
        return Optional.ofNullable(petRating)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /petRatings/:id -> delete the "id" petRating.
     */
    @RequestMapping(value = "/petRatings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePetRating(@PathVariable Long id) {
        log.debug("REST request to delete PetRating : {}", id);
        petRatingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("petRating", id.toString())).build();
    }
}
