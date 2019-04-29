package com.adventure.site.web.rest;
import com.adventure.site.service.AdventureCategoryTypeService;
import com.adventure.site.web.rest.errors.BadRequestAlertException;
import com.adventure.site.web.rest.util.HeaderUtil;
import com.adventure.site.web.rest.util.PaginationUtil;
import com.adventure.site.service.dto.AdventureCategoryTypeDTO;
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
 * REST controller for managing AdventureCategoryType.
 */
@RestController
@RequestMapping("/api")
public class AdventureCategoryTypeResource {

    private final Logger log = LoggerFactory.getLogger(AdventureCategoryTypeResource.class);

    private static final String ENTITY_NAME = "adventureCategoryType";

    private final AdventureCategoryTypeService adventureCategoryTypeService;

    public AdventureCategoryTypeResource(AdventureCategoryTypeService adventureCategoryTypeService) {
        this.adventureCategoryTypeService = adventureCategoryTypeService;
    }

    /**
     * POST  /adventure-category-types : Create a new adventureCategoryType.
     *
     * @param adventureCategoryTypeDTO the adventureCategoryTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adventureCategoryTypeDTO, or with status 400 (Bad Request) if the adventureCategoryType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/adventure-category-types")
    public ResponseEntity<AdventureCategoryTypeDTO> createAdventureCategoryType(@Valid @RequestBody AdventureCategoryTypeDTO adventureCategoryTypeDTO) throws URISyntaxException {
        log.debug("REST request to save AdventureCategoryType : {}", adventureCategoryTypeDTO);
        if (adventureCategoryTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new adventureCategoryType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdventureCategoryTypeDTO result = adventureCategoryTypeService.save(adventureCategoryTypeDTO);
        return ResponseEntity.created(new URI("/api/adventure-category-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /adventure-category-types : Updates an existing adventureCategoryType.
     *
     * @param adventureCategoryTypeDTO the adventureCategoryTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adventureCategoryTypeDTO,
     * or with status 400 (Bad Request) if the adventureCategoryTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the adventureCategoryTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/adventure-category-types")
    public ResponseEntity<AdventureCategoryTypeDTO> updateAdventureCategoryType(@Valid @RequestBody AdventureCategoryTypeDTO adventureCategoryTypeDTO) throws URISyntaxException {
        log.debug("REST request to update AdventureCategoryType : {}", adventureCategoryTypeDTO);
        if (adventureCategoryTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdventureCategoryTypeDTO result = adventureCategoryTypeService.save(adventureCategoryTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adventureCategoryTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /adventure-category-types : get all the adventureCategoryTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of adventureCategoryTypes in body
     */
    @GetMapping("/adventure-category-types")
    public ResponseEntity<List<AdventureCategoryTypeDTO>> getAllAdventureCategoryTypes(Pageable pageable) {
        log.debug("REST request to get a page of AdventureCategoryTypes");
        Page<AdventureCategoryTypeDTO> page = adventureCategoryTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/adventure-category-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /adventure-category-types/:id : get the "id" adventureCategoryType.
     *
     * @param id the id of the adventureCategoryTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adventureCategoryTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/adventure-category-types/{id}")
    public ResponseEntity<AdventureCategoryTypeDTO> getAdventureCategoryType(@PathVariable String id) {
        log.debug("REST request to get AdventureCategoryType : {}", id);
        Optional<AdventureCategoryTypeDTO> adventureCategoryTypeDTO = adventureCategoryTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adventureCategoryTypeDTO);
    }

    /**
     * DELETE  /adventure-category-types/:id : delete the "id" adventureCategoryType.
     *
     * @param id the id of the adventureCategoryTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/adventure-category-types/{id}")
    public ResponseEntity<Void> deleteAdventureCategoryType(@PathVariable String id) {
        log.debug("REST request to delete AdventureCategoryType : {}", id);
        adventureCategoryTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/adventure-category-types?query=:query : search for the adventureCategoryType corresponding
     * to the query.
     *
     * @param query the query of the adventureCategoryType search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/adventure-category-types")
    public ResponseEntity<List<AdventureCategoryTypeDTO>> searchAdventureCategoryTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AdventureCategoryTypes for query {}", query);
        Page<AdventureCategoryTypeDTO> page = adventureCategoryTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/adventure-category-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
