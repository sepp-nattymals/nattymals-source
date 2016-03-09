package com.sepp.nattymals.service;

import com.sepp.nattymals.domain.Folder;
import com.sepp.nattymals.repository.FolderRepository;
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
 * Service Implementation for managing Folder.
 */
@Service
@Transactional
public class FolderService {

    private final Logger log = LoggerFactory.getLogger(FolderService.class);
    
    @Inject
    private FolderRepository folderRepository;
    
    /**
     * Save a folder.
     * @return the persisted entity
     */
    public Folder save(Folder folder) {
        log.debug("Request to save Folder : {}", folder);
        Folder result = folderRepository.save(folder);
        return result;
    }

    /**
     *  get all the folders.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Folder> findAll(Pageable pageable) {
        log.debug("Request to get all Folders");
        Page<Folder> result = folderRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one folder by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Folder findOne(Long id) {
        log.debug("Request to get Folder : {}", id);
        Folder folder = folderRepository.findOne(id);
        return folder;
    }

    /**
     *  delete the  folder by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Folder : {}", id);
        folderRepository.delete(id);
    }
}
