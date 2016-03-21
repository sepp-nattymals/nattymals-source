package com.sepp.nattymals.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sepp.nattymals.domain.Discount;
import com.sepp.nattymals.service.DiscountService;
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
 * REST controller for managing Discount.
 */
@RestController
@RequestMapping("/api")
public class DiscountResource {

    private final Logger log = LoggerFactory.getLogger(DiscountResource.class);
        
    @Inject
    private DiscountService discountService;
    
    /**
     * POST  /discounts -> Create a new discount.
     */
    @RequestMapping(value = "/discounts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Discount> createDiscount(@Valid @RequestBody Discount discount) throws URISyntaxException {
        log.debug("REST request to save Discount : {}", discount);
        if (discount.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("discount", "idexists", "A new discount cannot already have an ID")).body(null);
        }
        Discount result = discountService.save(discount);
        return ResponseEntity.created(new URI("/api/discounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("discount", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /discounts -> Updates an existing discount.
     */
    @RequestMapping(value = "/discounts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Discount> updateDiscount(@Valid @RequestBody Discount discount) throws URISyntaxException {
        log.debug("REST request to update Discount : {}", discount);
        if (discount.getId() == null) {
            return createDiscount(discount);
        }
        Discount result = discountService.save(discount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("discount", discount.getId().toString()))
            .body(result);
    }

    /**
     * GET  /discounts -> get all the discounts.
     */
    @RequestMapping(value = "/discounts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Discount>> getAllDiscounts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Discounts");
        Page<Discount> page = discountService.findAll(pageable);    	
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/discounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /discounts/:id -> get the "id" discount.
     */
    @RequestMapping(value = "/discounts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Discount> getDiscount(@PathVariable Long id) {
        log.debug("REST request to get Discount : {}", id);
        Discount discount = discountService.findOne(id);
        return Optional.ofNullable(discount)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /discounts/:id -> delete the "id" discount.
     */
    @RequestMapping(value = "/discounts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
        log.debug("REST request to delete Discount : {}", id);
        discountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("discount", id.toString())).build();
    }
    
    /**
     * GET  /discounts/:companyName -> get all the discounts from Company Name.
     */
    @RequestMapping(value = "/discounts/{companyName}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Discount>> getAllDiscountsFromCompanyName(Pageable pageable, @PathVariable String companyName)
        throws URISyntaxException {
        log.debug("REST request to get a page of {} Discounts", companyName);
        Page<Discount> page = discountService.findCompanyDiscountByName(companyName, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/discounts/{companyName}");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
