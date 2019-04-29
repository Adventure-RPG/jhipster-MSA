package com.adventure.uaa.web.rest;
import com.adventure.uaa.service.AdventureScriptService;
import com.adventure.uaa.web.rest.errors.BadRequestAlertException;
import com.adventure.uaa.web.rest.util.HeaderUtil;
import com.adventure.uaa.web.rest.util.PaginationUtil;
import com.adventure.uaa.service.dto.AdventureScriptDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AdventureScript.
 */
@RestController
@RequestMapping("/api")
public class AdventureScriptResource {

    private final Logger log = LoggerFactory.getLogger(AdventureScriptResource.class);

    private static final String ENTITY_NAME = "adventureScript";

    private final AdventureScriptService adventureScriptService;

    public AdventureScriptResource(AdventureScriptService adventureScriptService) {
        this.adventureScriptService = adventureScriptService;
    }

    /**
     * POST  /adventure-scripts : Create a new adventureScript.
     *
     * @param adventureScriptDTO the adventureScriptDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adventureScriptDTO, or with status 400 (Bad Request) if the adventureScript has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/adventure-scripts")
    public ResponseEntity<AdventureScriptDTO> createAdventureScript(@Valid @RequestBody AdventureScriptDTO adventureScriptDTO) throws URISyntaxException {
        log.debug("REST request to save AdventureScript : {}", adventureScriptDTO);
        if (adventureScriptDTO.getId() != null) {
            throw new BadRequestAlertException("A new adventureScript cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdventureScriptDTO result = adventureScriptService.save(adventureScriptDTO);
        return ResponseEntity.created(new URI("/api/adventure-scripts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /adventure-scripts : Updates an existing adventureScript.
     *
     * @param adventureScriptDTO the adventureScriptDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adventureScriptDTO,
     * or with status 400 (Bad Request) if the adventureScriptDTO is not valid,
     * or with status 500 (Internal Server Error) if the adventureScriptDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/adventure-scripts")
    public ResponseEntity<AdventureScriptDTO> updateAdventureScript(@Valid @RequestBody AdventureScriptDTO adventureScriptDTO) throws URISyntaxException {
        log.debug("REST request to update AdventureScript : {}", adventureScriptDTO);
        if (adventureScriptDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdventureScriptDTO result = adventureScriptService.save(adventureScriptDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adventureScriptDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /adventure-scripts : get all the adventureScripts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of adventureScripts in body
     */
    @GetMapping("/adventure-scripts")
    public ResponseEntity<List<AdventureScriptDTO>> getAllAdventureScripts(Pageable pageable) {
        log.debug("REST request to get a page of AdventureScripts");
        Page<AdventureScriptDTO> page = adventureScriptService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/adventure-scripts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /adventure-scripts/:id : get the "id" adventureScript.
     *
     * @param id the id of the adventureScriptDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adventureScriptDTO, or with status 404 (Not Found)
     */
    @GetMapping("/adventure-scripts/{id}")
    public ResponseEntity<AdventureScriptDTO> getAdventureScript(@PathVariable Long id) {
        log.debug("REST request to get AdventureScript : {}", id);
        Optional<AdventureScriptDTO> adventureScriptDTO = adventureScriptService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adventureScriptDTO);
    }

    /**
     * DELETE  /adventure-scripts/:id : delete the "id" adventureScript.
     *
     * @param id the id of the adventureScriptDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/adventure-scripts/{id}")
    public ResponseEntity<Void> deleteAdventureScript(@PathVariable Long id) {
        log.debug("REST request to delete AdventureScript : {}", id);
        adventureScriptService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/adventure-scripts?query=:query : search for the adventureScript corresponding
     * to the query.
     *
     * @param query the query of the adventureScript search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/adventure-scripts")
    public ResponseEntity<List<AdventureScriptDTO>> searchAdventureScripts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AdventureScripts for query {}", query);
        Page<AdventureScriptDTO> page = adventureScriptService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/adventure-scripts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
