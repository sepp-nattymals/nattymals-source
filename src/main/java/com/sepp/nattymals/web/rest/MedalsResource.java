package com.sepp.nattymals.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sepp.nattymals.domain.Medals;
import com.sepp.nattymals.service.MedalsService;
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
 * REST controller for managing Medals.
 */
@RestController
@RequestMapping("/api")
public class MedalsResource {

    private final Logger log = LoggerFactory.getLogger(MedalsResource.class);
        
    @Inject
    private MedalsService medalsService;
    
    /**
     * POST  /medalss -> Create a new medals.
     */
    @RequestMapping(value = "/medalss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Medals> createMedals(@Valid @RequestBody Medals medals) throws URISyntaxException {
        log.debug("REST request to save Medals : {}", medals);
        if (medals.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("medals", "idexists", "A new medals cannot already have an ID")).body(null);
        }
        Medals result = medalsService.save(medals);
        return ResponseEntity.created(new URI("/api/medalss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("medals", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medalss -> Updates an existing medals.
     */
    @RequestMapping(value = "/medalss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Medals> updateMedals(@Valid @RequestBody Medals medals) throws URISyntaxException {
        log.debug("REST request to update Medals : {}", medals);
        if (medals.getId() == null) {
            return createMedals(medals);
        }
        Medals result = medalsService.save(medals);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("medals", medals.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medalss -> get all the medalss.
     */
    @RequestMapping(value = "/medalss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Medals>> getAllMedalss(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Medalss");
        Page<Medals> page = medalsService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/medalss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /medalss/:id -> get the "id" medals.
     */
    @RequestMapping(value = "/medalss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Medals> getMedals(@PathVariable Long id) {
        log.debug("REST request to get Medals : {}", id);
        Medals medals = medalsService.findOne(id);
        return Optional.ofNullable(medals)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /medalss/:id -> delete the "id" medals.
     */
    @RequestMapping(value = "/medalss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMedals(@PathVariable Long id) {
        log.debug("REST request to delete Medals : {}", id);
        medalsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("medals", id.toString())).build();
    }
}
