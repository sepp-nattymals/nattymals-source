package com.sepp.nattymals.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sepp.nattymals.domain.PremiumSubscription;
import com.sepp.nattymals.service.PremiumSubscriptionService;
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
 * REST controller for managing PremiumSubscription.
 */
@RestController
@RequestMapping("/api")
public class PremiumSubscriptionResource {

    private final Logger log = LoggerFactory.getLogger(PremiumSubscriptionResource.class);
        
    @Inject
    private PremiumSubscriptionService premiumSubscriptionService;
    
    /**
     * POST  /premiumSubscriptions -> Create a new premiumSubscription.
     */
    @RequestMapping(value = "/premiumSubscriptions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PremiumSubscription> createPremiumSubscription(@RequestBody PremiumSubscription premiumSubscription) throws URISyntaxException {
        log.debug("REST request to save PremiumSubscription : {}", premiumSubscription);
        if (premiumSubscription.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("premiumSubscription", "idexists", "A new premiumSubscription cannot already have an ID")).body(null);
        }
        PremiumSubscription result = premiumSubscriptionService.save(premiumSubscription);
        return ResponseEntity.created(new URI("/api/premiumSubscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("premiumSubscription", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /premiumSubscriptions -> Updates an existing premiumSubscription.
     */
    @RequestMapping(value = "/premiumSubscriptions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PremiumSubscription> updatePremiumSubscription(@RequestBody PremiumSubscription premiumSubscription) throws URISyntaxException {
        log.debug("REST request to update PremiumSubscription : {}", premiumSubscription);
        if (premiumSubscription.getId() == null) {
            return createPremiumSubscription(premiumSubscription);
        }
        PremiumSubscription result = premiumSubscriptionService.save(premiumSubscription);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("premiumSubscription", premiumSubscription.getId().toString()))
            .body(result);
    }

    /**
     * GET  /premiumSubscriptions -> get all the premiumSubscriptions.
     */
    @RequestMapping(value = "/premiumSubscriptions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PremiumSubscription>> getAllPremiumSubscriptions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PremiumSubscriptions");
        Page<PremiumSubscription> page = premiumSubscriptionService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/premiumSubscriptions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /premiumSubscriptions/:id -> get the "id" premiumSubscription.
     */
    @RequestMapping(value = "/premiumSubscriptions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PremiumSubscription> getPremiumSubscription(@PathVariable Long id) {
        log.debug("REST request to get PremiumSubscription : {}", id);
        PremiumSubscription premiumSubscription = premiumSubscriptionService.findOne(id);
        return Optional.ofNullable(premiumSubscription)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /premiumSubscriptions/:id -> delete the "id" premiumSubscription.
     */
    @RequestMapping(value = "/premiumSubscriptions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePremiumSubscription(@PathVariable Long id) {
        log.debug("REST request to delete PremiumSubscription : {}", id);
        premiumSubscriptionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("premiumSubscription", id.toString())).build();
    }
}
