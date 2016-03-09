package com.sepp.nattymals.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sepp.nattymals.domain.Clinic;
import com.sepp.nattymals.service.ClinicService;
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
 * REST controller for managing Clinic.
 */
@RestController
@RequestMapping("/api")
public class ClinicResource {

    private final Logger log = LoggerFactory.getLogger(ClinicResource.class);
        
    @Inject
    private ClinicService clinicService;
    
    /**
     * POST  /clinics -> Create a new clinic.
     */
    @RequestMapping(value = "/clinics",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Clinic> createClinic(@RequestBody Clinic clinic) throws URISyntaxException {
        log.debug("REST request to save Clinic : {}", clinic);
        if (clinic.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("clinic", "idexists", "A new clinic cannot already have an ID")).body(null);
        }
        Clinic result = clinicService.save(clinic);
        return ResponseEntity.created(new URI("/api/clinics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("clinic", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clinics -> Updates an existing clinic.
     */
    @RequestMapping(value = "/clinics",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Clinic> updateClinic(@RequestBody Clinic clinic) throws URISyntaxException {
        log.debug("REST request to update Clinic : {}", clinic);
        if (clinic.getId() == null) {
            return createClinic(clinic);
        }
        Clinic result = clinicService.save(clinic);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("clinic", clinic.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clinics -> get all the clinics.
     */
    @RequestMapping(value = "/clinics",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Clinic>> getAllClinics(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Clinics");
        Page<Clinic> page = clinicService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clinics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clinics/:id -> get the "id" clinic.
     */
    @RequestMapping(value = "/clinics/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Clinic> getClinic(@PathVariable Long id) {
        log.debug("REST request to get Clinic : {}", id);
        Clinic clinic = clinicService.findOne(id);
        return Optional.ofNullable(clinic)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /clinics/:id -> delete the "id" clinic.
     */
    @RequestMapping(value = "/clinics/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteClinic(@PathVariable Long id) {
        log.debug("REST request to delete Clinic : {}", id);
        clinicService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("clinic", id.toString())).build();
    }
}
