package com.sepp.nattymals.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sepp.nattymals.domain.Veterinarian;
import com.sepp.nattymals.service.VeterinarianService;
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
 * REST controller for managing Veterinarian.
 */
@RestController
@RequestMapping("/api")
public class VeterinarianResource {

    private final Logger log = LoggerFactory.getLogger(VeterinarianResource.class);
        
    @Inject
    private VeterinarianService veterinarianService;
    
    /**
     * POST  /veterinarians -> Create a new veterinarian.
     */
    @RequestMapping(value = "/veterinarians",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Veterinarian> createVeterinarian(@RequestBody Veterinarian veterinarian) throws URISyntaxException {
        log.debug("REST request to save Veterinarian : {}", veterinarian);
        if (veterinarian.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("veterinarian", "idexists", "A new veterinarian cannot already have an ID")).body(null);
        }
        Veterinarian result = veterinarianService.save(veterinarian);
        return ResponseEntity.created(new URI("/api/veterinarians/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("veterinarian", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /veterinarians -> Updates an existing veterinarian.
     */
    @RequestMapping(value = "/veterinarians",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Veterinarian> updateVeterinarian(@RequestBody Veterinarian veterinarian) throws URISyntaxException {
        log.debug("REST request to update Veterinarian : {}", veterinarian);
        if (veterinarian.getId() == null) {
            return createVeterinarian(veterinarian);
        }
        Veterinarian result = veterinarianService.save(veterinarian);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("veterinarian", veterinarian.getId().toString()))
            .body(result);
    }

    /**
     * GET  /veterinarians -> get all the veterinarians.
     */
    @RequestMapping(value = "/veterinarians",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Veterinarian>> getAllVeterinarians(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Veterinarians");
        Page<Veterinarian> page = veterinarianService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/veterinarians");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /veterinarians/:id -> get the "id" veterinarian.
     */
    @RequestMapping(value = "/veterinarians/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Veterinarian> getVeterinarian(@PathVariable Long id) {
        log.debug("REST request to get Veterinarian : {}", id);
        Veterinarian veterinarian = veterinarianService.findOne(id);
        return Optional.ofNullable(veterinarian)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /veterinarians/:id -> delete the "id" veterinarian.
     */
    @RequestMapping(value = "/veterinarians/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVeterinarian(@PathVariable Long id) {
        log.debug("REST request to delete Veterinarian : {}", id);
        veterinarianService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("veterinarian", id.toString())).build();
    }
}
