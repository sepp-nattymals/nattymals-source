package com.sepp.nattymals.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sepp.nattymals.domain.Actor;
import com.sepp.nattymals.service.ActorService;
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
 * REST controller for managing Actor.
 */
@RestController
@RequestMapping("/api")
public class ActorResource {

    private final Logger log = LoggerFactory.getLogger(ActorResource.class);
        
    @Inject
    private ActorService actorService;
    
    /**
     * POST  /actors -> Create a new actor.
     */
    @RequestMapping(value = "/actors",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Actor> createActor(@Valid @RequestBody Actor actor) throws URISyntaxException {
        log.debug("REST request to save Actor : {}", actor);
        if (actor.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("actor", "idexists", "A new actor cannot already have an ID")).body(null);
        }
        Actor result = actorService.save(actor);
        return ResponseEntity.created(new URI("/api/actors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("actor", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /actors -> Updates an existing actor.
     */
    @RequestMapping(value = "/actors",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Actor> updateActor(@Valid @RequestBody Actor actor) throws URISyntaxException {
        log.debug("REST request to update Actor : {}", actor);
        if (actor.getId() == null) {
            return createActor(actor);
        }
        Actor result = actorService.save(actor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("actor", actor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /actors -> get all the actors.
     */
    @RequestMapping(value = "/actors",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Actor>> getAllActors(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Actors");
        Page<Actor> page = actorService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /actors/:id -> get the "id" actor.
     */
    @RequestMapping(value = "/actors/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Actor> getActor(@PathVariable Long id) {
        log.debug("REST request to get Actor : {}", id);
        Actor actor = actorService.findOne(id);
        return Optional.ofNullable(actor)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /actors/:id -> delete the "id" actor.
     */
    @RequestMapping(value = "/actors/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteActor(@PathVariable Long id) {
        log.debug("REST request to delete Actor : {}", id);
        actorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("actor", id.toString())).build();
    }
}
