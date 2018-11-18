package com.adventure.uaa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adventure.uaa.service.AdventureSkillService;
import com.adventure.uaa.web.rest.errors.BadRequestAlertException;
import com.adventure.uaa.web.rest.util.HeaderUtil;
import com.adventure.uaa.web.rest.util.PaginationUtil;
import com.adventure.uaa.service.dto.AdventureSkillDTO;
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
 * REST controller for managing AdventureSkill.
 */
@RestController
@RequestMapping("/api")
public class AdventureSkillResource {

    private final Logger log = LoggerFactory.getLogger(AdventureSkillResource.class);

    private static final String ENTITY_NAME = "adventureSkill";

    private final AdventureSkillService adventureSkillService;

    public AdventureSkillResource(AdventureSkillService adventureSkillService) {
        this.adventureSkillService = adventureSkillService;
    }

    /**
     * POST  /adventure-skills : Create a new adventureSkill.
     *
     * @param adventureSkillDTO the adventureSkillDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adventureSkillDTO, or with status 400 (Bad Request) if the adventureSkill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/adventure-skills")
    @Timed
    public ResponseEntity<AdventureSkillDTO> createAdventureSkill(@Valid @RequestBody AdventureSkillDTO adventureSkillDTO) throws URISyntaxException {
        log.debug("REST request to save AdventureSkill : {}", adventureSkillDTO);
        if (adventureSkillDTO.getId() != null) {
            throw new BadRequestAlertException("A new adventureSkill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdventureSkillDTO result = adventureSkillService.save(adventureSkillDTO);
        return ResponseEntity.created(new URI("/api/adventure-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /adventure-skills : Updates an existing adventureSkill.
     *
     * @param adventureSkillDTO the adventureSkillDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adventureSkillDTO,
     * or with status 400 (Bad Request) if the adventureSkillDTO is not valid,
     * or with status 500 (Internal Server Error) if the adventureSkillDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/adventure-skills")
    @Timed
    public ResponseEntity<AdventureSkillDTO> updateAdventureSkill(@Valid @RequestBody AdventureSkillDTO adventureSkillDTO) throws URISyntaxException {
        log.debug("REST request to update AdventureSkill : {}", adventureSkillDTO);
        if (adventureSkillDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdventureSkillDTO result = adventureSkillService.save(adventureSkillDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adventureSkillDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /adventure-skills : get all the adventureSkills.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of adventureSkills in body
     */
    @GetMapping("/adventure-skills")
    @Timed
    public ResponseEntity<List<AdventureSkillDTO>> getAllAdventureSkills(Pageable pageable) {
        log.debug("REST request to get a page of AdventureSkills");
        Page<AdventureSkillDTO> page = adventureSkillService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/adventure-skills");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /adventure-skills/:id : get the "id" adventureSkill.
     *
     * @param id the id of the adventureSkillDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adventureSkillDTO, or with status 404 (Not Found)
     */
    @GetMapping("/adventure-skills/{id}")
    @Timed
    public ResponseEntity<AdventureSkillDTO> getAdventureSkill(@PathVariable Long id) {
        log.debug("REST request to get AdventureSkill : {}", id);
        Optional<AdventureSkillDTO> adventureSkillDTO = adventureSkillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adventureSkillDTO);
    }

    /**
     * DELETE  /adventure-skills/:id : delete the "id" adventureSkill.
     *
     * @param id the id of the adventureSkillDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/adventure-skills/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdventureSkill(@PathVariable Long id) {
        log.debug("REST request to delete AdventureSkill : {}", id);
        adventureSkillService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/adventure-skills?query=:query : search for the adventureSkill corresponding
     * to the query.
     *
     * @param query the query of the adventureSkill search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/adventure-skills")
    @Timed
    public ResponseEntity<List<AdventureSkillDTO>> searchAdventureSkills(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AdventureSkills for query {}", query);
        Page<AdventureSkillDTO> page = adventureSkillService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/adventure-skills");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
