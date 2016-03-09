package com.sepp.nattymals.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sepp.nattymals.domain.PetCompany;
import com.sepp.nattymals.service.PetCompanyService;
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
 * REST controller for managing PetCompany.
 */
@RestController
@RequestMapping("/api")
public class PetCompanyResource {

    private final Logger log = LoggerFactory.getLogger(PetCompanyResource.class);
        
    @Inject
    private PetCompanyService petCompanyService;
    
    /**
     * POST  /petCompanys -> Create a new petCompany.
     */
    @RequestMapping(value = "/petCompanys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PetCompany> createPetCompany(@Valid @RequestBody PetCompany petCompany) throws URISyntaxException {
        log.debug("REST request to save PetCompany : {}", petCompany);
        if (petCompany.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("petCompany", "idexists", "A new petCompany cannot already have an ID")).body(null);
        }
        PetCompany result = petCompanyService.save(petCompany);
        return ResponseEntity.created(new URI("/api/petCompanys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("petCompany", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /petCompanys -> Updates an existing petCompany.
     */
    @RequestMapping(value = "/petCompanys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PetCompany> updatePetCompany(@Valid @RequestBody PetCompany petCompany) throws URISyntaxException {
        log.debug("REST request to update PetCompany : {}", petCompany);
        if (petCompany.getId() == null) {
            return createPetCompany(petCompany);
        }
        PetCompany result = petCompanyService.save(petCompany);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("petCompany", petCompany.getId().toString()))
            .body(result);
    }

    /**
     * GET  /petCompanys -> get all the petCompanys.
     */
    @RequestMapping(value = "/petCompanys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PetCompany>> getAllPetCompanys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PetCompanys");
        Page<PetCompany> page = petCompanyService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/petCompanys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /petCompanys/:id -> get the "id" petCompany.
     */
    @RequestMapping(value = "/petCompanys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PetCompany> getPetCompany(@PathVariable Long id) {
        log.debug("REST request to get PetCompany : {}", id);
        PetCompany petCompany = petCompanyService.findOne(id);
        return Optional.ofNullable(petCompany)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /petCompanys/:id -> delete the "id" petCompany.
     */
    @RequestMapping(value = "/petCompanys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePetCompany(@PathVariable Long id) {
        log.debug("REST request to delete PetCompany : {}", id);
        petCompanyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("petCompany", id.toString())).build();
    }
}
