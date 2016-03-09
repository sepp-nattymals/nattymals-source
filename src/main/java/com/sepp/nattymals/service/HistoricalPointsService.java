package com.sepp.nattymals.service;

import com.sepp.nattymals.domain.HistoricalPoints;
import com.sepp.nattymals.repository.HistoricalPointsRepository;
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
 * Service Implementation for managing HistoricalPoints.
 */
@Service
@Transactional
public class HistoricalPointsService {

    private final Logger log = LoggerFactory.getLogger(HistoricalPointsService.class);
    
    @Inject
    private HistoricalPointsRepository historicalPointsRepository;
    
    /**
     * Save a historicalPoints.
     * @return the persisted entity
     */
    public HistoricalPoints save(HistoricalPoints historicalPoints) {
        log.debug("Request to save HistoricalPoints : {}", historicalPoints);
        HistoricalPoints result = historicalPointsRepository.save(historicalPoints);
        return result;
    }

    /**
     *  get all the historicalPointss.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<HistoricalPoints> findAll(Pageable pageable) {
        log.debug("Request to get all HistoricalPointss");
        Page<HistoricalPoints> result = historicalPointsRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one historicalPoints by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public HistoricalPoints findOne(Long id) {
        log.debug("Request to get HistoricalPoints : {}", id);
        HistoricalPoints historicalPoints = historicalPointsRepository.findOne(id);
        return historicalPoints;
    }

    /**
     *  delete the  historicalPoints by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete HistoricalPoints : {}", id);
        historicalPointsRepository.delete(id);
    }
}
