package com.sepp.nattymals.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sepp.nattymals.domain.VeterinarianComment;
import com.sepp.nattymals.service.VeterinarianCommentService;
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
 * REST controller for managing VeterinarianComment.
 */
@RestController
@RequestMapping("/api")
public class VeterinarianCommentResource {

    private final Logger log = LoggerFactory.getLogger(VeterinarianCommentResource.class);
        
    @Inject
    private VeterinarianCommentService veterinarianCommentService;
    
    /**
     * POST  /veterinarianComments -> Create a new veterinarianComment.
     */
    @RequestMapping(value = "/veterinarianComments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VeterinarianComment> createVeterinarianComment(@RequestBody VeterinarianComment veterinarianComment) throws URISyntaxException {
        log.debug("REST request to save VeterinarianComment : {}", veterinarianComment);
        if (veterinarianComment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("veterinarianComment", "idexists", "A new veterinarianComment cannot already have an ID")).body(null);
        }
        VeterinarianComment result = veterinarianCommentService.save(veterinarianComment);
        return ResponseEntity.created(new URI("/api/veterinarianComments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("veterinarianComment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /veterinarianComments -> Updates an existing veterinarianComment.
     */
    @RequestMapping(value = "/veterinarianComments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VeterinarianComment> updateVeterinarianComment(@RequestBody VeterinarianComment veterinarianComment) throws URISyntaxException {
        log.debug("REST request to update VeterinarianComment : {}", veterinarianComment);
        if (veterinarianComment.getId() == null) {
            return createVeterinarianComment(veterinarianComment);
        }
        VeterinarianComment result = veterinarianCommentService.save(veterinarianComment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("veterinarianComment", veterinarianComment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /veterinarianComments -> get all the veterinarianComments.
     */
    @RequestMapping(value = "/veterinarianComments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<VeterinarianComment>> getAllVeterinarianComments(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of VeterinarianComments");
        Page<VeterinarianComment> page = veterinarianCommentService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/veterinarianComments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /veterinarianComments/:id -> get the "id" veterinarianComment.
     */
    @RequestMapping(value = "/veterinarianComments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VeterinarianComment> getVeterinarianComment(@PathVariable Long id) {
        log.debug("REST request to get VeterinarianComment : {}", id);
        VeterinarianComment veterinarianComment = veterinarianCommentService.findOne(id);
        return Optional.ofNullable(veterinarianComment)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /veterinarianComments/:id -> delete the "id" veterinarianComment.
     */
    @RequestMapping(value = "/veterinarianComments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVeterinarianComment(@PathVariable Long id) {
        log.debug("REST request to delete VeterinarianComment : {}", id);
        veterinarianCommentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("veterinarianComment", id.toString())).build();
    }
}
