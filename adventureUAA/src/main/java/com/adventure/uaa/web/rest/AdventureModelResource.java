package com.adventure.uaa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adventure.uaa.service.AdventureModelService;
import com.adventure.uaa.web.rest.errors.BadRequestAlertException;
import com.adventure.uaa.web.rest.util.HeaderUtil;
import com.adventure.uaa.web.rest.util.PaginationUtil;
import com.adventure.uaa.service.dto.AdventureModelDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AdventureModel.
 */
@RestController
@RequestMapping("/api")
public class AdventureModelResource {

    private final Logger log = LoggerFactory.getLogger(AdventureModelResource.class);

    private static final String ENTITY_NAME = "adventureModel";

    private final AdventureModelService adventureModelService;

    public AdventureModelResource(AdventureModelService adventureModelService) {
        this.adventureModelService = adventureModelService;
    }

    /**
     * POST  /adventure-models : Create a new adventureModel.
     *
     * @param adventureModelDTO the adventureModelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adventureModelDTO, or with status 400 (Bad Request) if the adventureModel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/adventure-models")
    @Timed
    public ResponseEntity<AdventureModelDTO> createAdventureModel(@RequestBody AdventureModelDTO adventureModelDTO) throws URISyntaxException {
        log.debug("REST request to save AdventureModel : {}", adventureModelDTO);
        if (adventureModelDTO.getId() != null) {
            throw new BadRequestAlertException("A new adventureModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdventureModelDTO result = adventureModelService.save(adventureModelDTO);
        return ResponseEntity.created(new URI("/api/adventure-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /adventure-models : Updates an existing adventureModel.
     *
     * @param adventureModelDTO the adventureModelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adventureModelDTO,
     * or with status 400 (Bad Request) if the adventureModelDTO is not valid,
     * or with status 500 (Internal Server Error) if the adventureModelDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/adventure-models")
    @Timed
    public ResponseEntity<AdventureModelDTO> updateAdventureModel(@RequestBody AdventureModelDTO adventureModelDTO) throws URISyntaxException {
        log.debug("REST request to update AdventureModel : {}", adventureModelDTO);
        if (adventureModelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdventureModelDTO result = adventureModelService.save(adventureModelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adventureModelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /adventure-models : get all the adventureModels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of adventureModels in body
     */
    @GetMapping("/adventure-models")
    @Timed
    public ResponseEntity<List<AdventureModelDTO>> getAllAdventureModels(Pageable pageable) {
        log.debug("REST request to get a page of AdventureModels");
        Page<AdventureModelDTO> page = adventureModelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/adventure-models");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /adventure-models/:id : get the "id" adventureModel.
     *
     * @param id the id of the adventureModelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adventureModelDTO, or with status 404 (Not Found)
     */
    @GetMapping("/adventure-models/{id}")
    @Timed
    public ResponseEntity<AdventureModelDTO> getAdventureModel(@PathVariable Long id) {
        log.debug("REST request to get AdventureModel : {}", id);
        Optional<AdventureModelDTO> adventureModelDTO = adventureModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adventureModelDTO);
    }

    /**
     * DELETE  /adventure-models/:id : delete the "id" adventureModel.
     *
     * @param id the id of the adventureModelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/adventure-models/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdventureModel(@PathVariable Long id) {
        log.debug("REST request to delete AdventureModel : {}", id);
        adventureModelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/adventure-models?query=:query : search for the adventureModel corresponding
     * to the query.
     *
     * @param query the query of the adventureModel search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/adventure-models")
    @Timed
    public ResponseEntity<List<AdventureModelDTO>> searchAdventureModels(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AdventureModels for query {}", query);
        Page<AdventureModelDTO> page = adventureModelService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/adventure-models");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
