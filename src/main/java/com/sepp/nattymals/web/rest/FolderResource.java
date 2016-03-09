package com.sepp.nattymals.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sepp.nattymals.domain.Folder;
import com.sepp.nattymals.service.FolderService;
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
 * REST controller for managing Folder.
 */
@RestController
@RequestMapping("/api")
public class FolderResource {

    private final Logger log = LoggerFactory.getLogger(FolderResource.class);
        
    @Inject
    private FolderService folderService;
    
    /**
     * POST  /folders -> Create a new folder.
     */
    @RequestMapping(value = "/folders",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Folder> createFolder(@RequestBody Folder folder) throws URISyntaxException {
        log.debug("REST request to save Folder : {}", folder);
        if (folder.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("folder", "idexists", "A new folder cannot already have an ID")).body(null);
        }
        Folder result = folderService.save(folder);
        return ResponseEntity.created(new URI("/api/folders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("folder", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /folders -> Updates an existing folder.
     */
    @RequestMapping(value = "/folders",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Folder> updateFolder(@RequestBody Folder folder) throws URISyntaxException {
        log.debug("REST request to update Folder : {}", folder);
        if (folder.getId() == null) {
            return createFolder(folder);
        }
        Folder result = folderService.save(folder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("folder", folder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /folders -> get all the folders.
     */
    @RequestMapping(value = "/folders",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Folder>> getAllFolders(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Folders");
        Page<Folder> page = folderService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/folders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /folders/:id -> get the "id" folder.
     */
    @RequestMapping(value = "/folders/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Folder> getFolder(@PathVariable Long id) {
        log.debug("REST request to get Folder : {}", id);
        Folder folder = folderService.findOne(id);
        return Optional.ofNullable(folder)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /folders/:id -> delete the "id" folder.
     */
    @RequestMapping(value = "/folders/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFolder(@PathVariable Long id) {
        log.debug("REST request to delete Folder : {}", id);
        folderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("folder", id.toString())).build();
    }
}
