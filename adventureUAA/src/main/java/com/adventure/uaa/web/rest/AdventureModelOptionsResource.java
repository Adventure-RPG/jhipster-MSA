package com.adventure.uaa.web.rest;
import com.adventure.uaa.service.AdventureModelOptionsService;
import com.adventure.uaa.web.rest.errors.BadRequestAlertException;
import com.adventure.uaa.web.rest.util.HeaderUtil;
import com.adventure.uaa.web.rest.util.PaginationUtil;
import com.adventure.uaa.service.dto.AdventureModelOptionsDTO;
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
 * REST controller for managing AdventureModelOptions.
 */
@RestController
@RequestMapping("/api")
public class AdventureModelOptionsResource {

    private final Logger log = LoggerFactory.getLogger(AdventureModelOptionsResource.class);

    private static final String ENTITY_NAME = "adventureModelOptions";

    private final AdventureModelOptionsService adventureModelOptionsService;

    public AdventureModelOptionsResource(AdventureModelOptionsService adventureModelOptionsService) {
        this.adventureModelOptionsService = adventureModelOptionsService;
    }

    /**
     * POST  /adventure-model-options : Create a new adventureModelOptions.
     *
     * @param adventureModelOptionsDTO the adventureModelOptionsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adventureModelOptionsDTO, or with status 400 (Bad Request) if the adventureModelOptions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/adventure-model-options")
    public ResponseEntity<AdventureModelOptionsDTO> createAdventureModelOptions(@RequestBody AdventureModelOptionsDTO adventureModelOptionsDTO) throws URISyntaxException {
        log.debug("REST request to save AdventureModelOptions : {}", adventureModelOptionsDTO);
        if (adventureModelOptionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new adventureModelOptions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdventureModelOptionsDTO result = adventureModelOptionsService.save(adventureModelOptionsDTO);
        return ResponseEntity.created(new URI("/api/adventure-model-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /adventure-model-options : Updates an existing adventureModelOptions.
     *
     * @param adventureModelOptionsDTO the adventureModelOptionsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adventureModelOptionsDTO,
     * or with status 400 (Bad Request) if the adventureModelOptionsDTO is not valid,
     * or with status 500 (Internal Server Error) if the adventureModelOptionsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/adventure-model-options")
    public ResponseEntity<AdventureModelOptionsDTO> updateAdventureModelOptions(@RequestBody AdventureModelOptionsDTO adventureModelOptionsDTO) throws URISyntaxException {
        log.debug("REST request to update AdventureModelOptions : {}", adventureModelOptionsDTO);
        if (adventureModelOptionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdventureModelOptionsDTO result = adventureModelOptionsService.save(adventureModelOptionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adventureModelOptionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /adventure-model-options : get all the adventureModelOptions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of adventureModelOptions in body
     */
    @GetMapping("/adventure-model-options")
    public ResponseEntity<List<AdventureModelOptionsDTO>> getAllAdventureModelOptions(Pageable pageable) {
        log.debug("REST request to get a page of AdventureModelOptions");
        Page<AdventureModelOptionsDTO> page = adventureModelOptionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/adventure-model-options");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /adventure-model-options/:id : get the "id" adventureModelOptions.
     *
     * @param id the id of the adventureModelOptionsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adventureModelOptionsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/adventure-model-options/{id}")
    public ResponseEntity<AdventureModelOptionsDTO> getAdventureModelOptions(@PathVariable Long id) {
        log.debug("REST request to get AdventureModelOptions : {}", id);
        Optional<AdventureModelOptionsDTO> adventureModelOptionsDTO = adventureModelOptionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adventureModelOptionsDTO);
    }

    /**
     * DELETE  /adventure-model-options/:id : delete the "id" adventureModelOptions.
     *
     * @param id the id of the adventureModelOptionsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/adventure-model-options/{id}")
    public ResponseEntity<Void> deleteAdventureModelOptions(@PathVariable Long id) {
        log.debug("REST request to delete AdventureModelOptions : {}", id);
        adventureModelOptionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/adventure-model-options?query=:query : search for the adventureModelOptions corresponding
     * to the query.
     *
     * @param query the query of the adventureModelOptions search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/adventure-model-options")
    public ResponseEntity<List<AdventureModelOptionsDTO>> searchAdventureModelOptions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AdventureModelOptions for query {}", query);
        Page<AdventureModelOptionsDTO> page = adventureModelOptionsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/adventure-model-options");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
