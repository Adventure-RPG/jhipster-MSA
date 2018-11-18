package com.adventure.uaa.service;

import com.adventure.uaa.service.dto.AdventureScriptDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AdventureScript.
 */
public interface AdventureScriptService {

    /**
     * Save a adventureScript.
     *
     * @param adventureScriptDTO the entity to save
     * @return the persisted entity
     */
    AdventureScriptDTO save(AdventureScriptDTO adventureScriptDTO);

    /**
     * Get all the adventureScripts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdventureScriptDTO> findAll(Pageable pageable);


    /**
     * Get the "id" adventureScript.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AdventureScriptDTO> findOne(Long id);

    /**
     * Delete the "id" adventureScript.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the adventureScript corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdventureScriptDTO> search(String query, Pageable pageable);
}
