package com.sepp.nattymals.service;

import com.sepp.nattymals.domain.Actor;
import com.sepp.nattymals.repository.ActorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Actor.
 */
@Service
@Transactional
public class ActorService {

    private final Logger log = LoggerFactory.getLogger(ActorService.class);
    
    @Inject
    private ActorRepository actorRepository;
    
    /**
     * Save a actor.
     * @return the persisted entity
     */
    public Actor save(Actor actor) {
        log.debug("Request to save Actor : {}", actor);
        Actor result = actorRepository.save(actor);
        return result;
    }

    /**
     *  get all the actors.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Actor> findAll(Pageable pageable) {
        log.debug("Request to get all Actors");
        Page<Actor> result = actorRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one actor by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Actor findOne(Long id) {
        log.debug("Request to get Actor : {}", id);
        Actor actor = actorRepository.findOne(id);
        return actor;
    }

    /**
     *  delete the  actor by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Actor : {}", id);
        actorRepository.delete(id);
    }
}
