package com.adventure.uaa.service;

import com.adventure.uaa.service.dto.AdventureItemDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.adventure.uaa.domain.AdventureItem}.
 */
public interface AdventureItemService {

    /**
     * Save a adventureItem.
     *
     * @param adventureItemDTO the entity to save.
     * @return the persisted entity.
     */
    AdventureItemDTO save(AdventureItemDTO adventureItemDTO);

    /**
     * Get all the adventureItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AdventureItemDTO> findAll(Pageable pageable);


    /**
     * Get the "id" adventureItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdventureItemDTO> findOne(Long id);

    /**
     * Delete the "id" adventureItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the adventureItem corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AdventureItemDTO> search(String query, Pageable pageable);
}
