package com.sepp.nattymals.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sepp.nattymals.domain.Adoption;
import com.sepp.nattymals.service.AdoptionService;
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
 * REST controller for managing Adoption.
 */
@RestController
@RequestMapping("/api")
public class AdoptionResource {

    private final Logger log = LoggerFactory.getLogger(AdoptionResource.class);
        
    @Inject
    private AdoptionService adoptionService;
    
    /**
     * POST  /adoptions -> Create a new adoption.
     */
    @RequestMapping(value = "/adoptions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Adoption> createAdoption(@Valid @RequestBody Adoption adoption) throws URISyntaxException {
        log.debug("REST request to save Adoption : {}", adoption);
        if (adoption.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("adoption", "idexists", "A new adoption cannot already have an ID")).body(null);
        }
        Adoption result = adoptionService.save(adoption);
        return ResponseEntity.created(new URI("/api/adoptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("adoption", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /adoptions -> Updates an existing adoption.
     */
    @RequestMapping(value = "/adoptions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Adoption> updateAdoption(@Valid @RequestBody Adoption adoption) throws URISyntaxException {
        log.debug("REST request to update Adoption : {}", adoption);
        if (adoption.getId() == null) {
            return createAdoption(adoption);
        }
        Adoption result = adoptionService.save(adoption);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("adoption", adoption.getId().toString()))
            .body(result);
    }

    /**
     * GET  /adoptions -> get all the adoptions.
     */
    @RequestMapping(value = "/adoptions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Adoption>> getAllAdoptions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Adoptions");
        Page<Adoption> page = adoptionService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/adoptions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /adoptions/:id -> get the "id" adoption.
     */
    @RequestMapping(value = "/adoptions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Adoption> getAdoption(@PathVariable Long id) {
        log.debug("REST request to get Adoption : {}", id);
        Adoption adoption = adoptionService.findOne(id);
        return Optional.ofNullable(adoption)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /adoptions/:id -> delete the "id" adoption.
     */
    @RequestMapping(value = "/adoptions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAdoption(@PathVariable Long id) {
        log.debug("REST request to delete Adoption : {}", id);
        adoptionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("adoption", id.toString())).build();
    }
}
