package com.sepp.nattymals.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sepp.nattymals.domain.HistoricalPoints;
import com.sepp.nattymals.service.HistoricalPointsService;
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
 * REST controller for managing HistoricalPoints.
 */
@RestController
@RequestMapping("/api")
public class HistoricalPointsResource {

    private final Logger log = LoggerFactory.getLogger(HistoricalPointsResource.class);
        
    @Inject
    private HistoricalPointsService historicalPointsService;
    
    /**
     * POST  /historicalPointss -> Create a new historicalPoints.
     */
    @RequestMapping(value = "/historicalPointss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HistoricalPoints> createHistoricalPoints(@Valid @RequestBody HistoricalPoints historicalPoints) throws URISyntaxException {
        log.debug("REST request to save HistoricalPoints : {}", historicalPoints);
        if (historicalPoints.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("historicalPoints", "idexists", "A new historicalPoints cannot already have an ID")).body(null);
        }
        HistoricalPoints result = historicalPointsService.save(historicalPoints);
        return ResponseEntity.created(new URI("/api/historicalPointss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("historicalPoints", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /historicalPointss -> Updates an existing historicalPoints.
     */
    @RequestMapping(value = "/historicalPointss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HistoricalPoints> updateHistoricalPoints(@Valid @RequestBody HistoricalPoints historicalPoints) throws URISyntaxException {
        log.debug("REST request to update HistoricalPoints : {}", historicalPoints);
        if (historicalPoints.getId() == null) {
            return createHistoricalPoints(historicalPoints);
        }
        HistoricalPoints result = historicalPointsService.save(historicalPoints);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("historicalPoints", historicalPoints.getId().toString()))
            .body(result);
    }

    /**
     * GET  /historicalPointss -> get all the historicalPointss.
     */
    @RequestMapping(value = "/historicalPointss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HistoricalPoints>> getAllHistoricalPointss(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HistoricalPointss");
        Page<HistoricalPoints> page = historicalPointsService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/historicalPointss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /historicalPointss/:id -> get the "id" historicalPoints.
     */
    @RequestMapping(value = "/historicalPointss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HistoricalPoints> getHistoricalPoints(@PathVariable Long id) {
        log.debug("REST request to get HistoricalPoints : {}", id);
        HistoricalPoints historicalPoints = historicalPointsService.findOne(id);
        return Optional.ofNullable(historicalPoints)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /historicalPointss/:id -> delete the "id" historicalPoints.
     */
    @RequestMapping(value = "/historicalPointss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHistoricalPoints(@PathVariable Long id) {
        log.debug("REST request to delete HistoricalPoints : {}", id);
        historicalPointsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("historicalPoints", id.toString())).build();
    }
}
