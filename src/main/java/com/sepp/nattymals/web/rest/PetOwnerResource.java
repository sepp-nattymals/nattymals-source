package com.sepp.nattymals.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sepp.nattymals.domain.PetOwner;
import com.sepp.nattymals.service.PetOwnerService;
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
 * REST controller for managing PetOwner.
 */
@RestController
@RequestMapping("/api")
public class PetOwnerResource {

    private final Logger log = LoggerFactory.getLogger(PetOwnerResource.class);
        
    @Inject
    private PetOwnerService petOwnerService;
    
    /**
     * POST  /petOwners -> Create a new petOwner.
     */
    @RequestMapping(value = "/petOwners",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PetOwner> createPetOwner(@Valid @RequestBody PetOwner petOwner) throws URISyntaxException {
        log.debug("REST request to save PetOwner : {}", petOwner);
        if (petOwner.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("petOwner", "idexists", "A new petOwner cannot already have an ID")).body(null);
        }
        PetOwner result = petOwnerService.save(petOwner);
        return ResponseEntity.created(new URI("/api/petOwners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("petOwner", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /petOwners -> Updates an existing petOwner.
     */
    @RequestMapping(value = "/petOwners",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PetOwner> updatePetOwner(@Valid @RequestBody PetOwner petOwner) throws URISyntaxException {
        log.debug("REST request to update PetOwner : {}", petOwner);
        if (petOwner.getId() == null) {
            return createPetOwner(petOwner);
        }
        PetOwner result = petOwnerService.save(petOwner);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("petOwner", petOwner.getId().toString()))
            .body(result);
    }

    /**
     * GET  /petOwners -> get all the petOwners.
     */
    @RequestMapping(value = "/petOwners",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PetOwner>> getAllPetOwners(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PetOwners");
        Page<PetOwner> page = petOwnerService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/petOwners");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /petOwners/:id -> get the "id" petOwner.
     */
    @RequestMapping(value = "/petOwners/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PetOwner> getPetOwner(@PathVariable Long id) {
        log.debug("REST request to get PetOwner : {}", id);
        PetOwner petOwner = petOwnerService.findOne(id);
        return Optional.ofNullable(petOwner)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /petOwners/:id -> delete the "id" petOwner.
     */
    @RequestMapping(value = "/petOwners/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePetOwner(@PathVariable Long id) {
        log.debug("REST request to delete PetOwner : {}", id);
        petOwnerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("petOwner", id.toString())).build();
    }
}
