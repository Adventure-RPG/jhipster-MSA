package com.adventure.core.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adventure.core.service.AdventureItemService;
import com.adventure.core.web.rest.errors.BadRequestAlertException;
import com.adventure.core.web.rest.util.HeaderUtil;
import com.adventure.core.web.rest.util.PaginationUtil;
import com.adventure.core.service.dto.AdventureItemDTO;
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
 * REST controller for managing AdventureItem.
 */
@RestController
@RequestMapping("/api")
public class AdventureItemResource {

    private final Logger log = LoggerFactory.getLogger(AdventureItemResource.class);

    private static final String ENTITY_NAME = "adventureCoreAdventureItem";

    private final AdventureItemService adventureItemService;

    public AdventureItemResource(AdventureItemService adventureItemService) {
        this.adventureItemService = adventureItemService;
    }

    /**
     * POST  /adventure-items : Create a new adventureItem.
     *
     * @param adventureItemDTO the adventureItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adventureItemDTO, or with status 400 (Bad Request) if the adventureItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/adventure-items")
    @Timed
    public ResponseEntity<AdventureItemDTO> createAdventureItem(@RequestBody AdventureItemDTO adventureItemDTO) throws URISyntaxException {
        log.debug("REST request to save AdventureItem : {}", adventureItemDTO);
        if (adventureItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new adventureItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdventureItemDTO result = adventureItemService.save(adventureItemDTO);
        return ResponseEntity.created(new URI("/api/adventure-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /adventure-items : Updates an existing adventureItem.
     *
     * @param adventureItemDTO the adventureItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adventureItemDTO,
     * or with status 400 (Bad Request) if the adventureItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the adventureItemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/adventure-items")
    @Timed
    public ResponseEntity<AdventureItemDTO> updateAdventureItem(@RequestBody AdventureItemDTO adventureItemDTO) throws URISyntaxException {
        log.debug("REST request to update AdventureItem : {}", adventureItemDTO);
        if (adventureItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdventureItemDTO result = adventureItemService.save(adventureItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adventureItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /adventure-items : get all the adventureItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of adventureItems in body
     */
    @GetMapping("/adventure-items")
    @Timed
    public ResponseEntity<List<AdventureItemDTO>> getAllAdventureItems(Pageable pageable) {
        log.debug("REST request to get a page of AdventureItems");
        Page<AdventureItemDTO> page = adventureItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/adventure-items");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /adventure-items/:id : get the "id" adventureItem.
     *
     * @param id the id of the adventureItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adventureItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/adventure-items/{id}")
    @Timed
    public ResponseEntity<AdventureItemDTO> getAdventureItem(@PathVariable String id) {
        log.debug("REST request to get AdventureItem : {}", id);
        Optional<AdventureItemDTO> adventureItemDTO = adventureItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adventureItemDTO);
    }

    /**
     * DELETE  /adventure-items/:id : delete the "id" adventureItem.
     *
     * @param id the id of the adventureItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/adventure-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdventureItem(@PathVariable String id) {
        log.debug("REST request to delete AdventureItem : {}", id);
        adventureItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/adventure-items?query=:query : search for the adventureItem corresponding
     * to the query.
     *
     * @param query the query of the adventureItem search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/adventure-items")
    @Timed
    public ResponseEntity<List<AdventureItemDTO>> searchAdventureItems(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AdventureItems for query {}", query);
        Page<AdventureItemDTO> page = adventureItemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/adventure-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
