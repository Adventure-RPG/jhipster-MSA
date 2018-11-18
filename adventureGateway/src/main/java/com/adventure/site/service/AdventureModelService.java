package com.adventure.site.service;

import com.adventure.site.service.dto.AdventureModelDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AdventureModel.
 */
public interface AdventureModelService {

    /**
     * Save a adventureModel.
     *
     * @param adventureModelDTO the entity to save
     * @return the persisted entity
     */
    AdventureModelDTO save(AdventureModelDTO adventureModelDTO);

    /**
     * Get all the adventureModels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdventureModelDTO> findAll(Pageable pageable);


    /**
     * Get the "id" adventureModel.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AdventureModelDTO> findOne(Long id);

    /**
     * Delete the "id" adventureModel.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the adventureModel corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdventureModelDTO> search(String query, Pageable pageable);
}
