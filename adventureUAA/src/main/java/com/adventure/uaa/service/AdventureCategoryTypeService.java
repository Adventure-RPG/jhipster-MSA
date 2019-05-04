package com.adventure.uaa.service;

import com.adventure.uaa.service.dto.AdventureCategoryTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.adventure.uaa.domain.AdventureCategoryType}.
 */
public interface AdventureCategoryTypeService {

    /**
     * Save a adventureCategoryType.
     *
     * @param adventureCategoryTypeDTO the entity to save.
     * @return the persisted entity.
     */
    AdventureCategoryTypeDTO save(AdventureCategoryTypeDTO adventureCategoryTypeDTO);

    /**
     * Get all the adventureCategoryTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AdventureCategoryTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" adventureCategoryType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdventureCategoryTypeDTO> findOne(Long id);

    /**
     * Delete the "id" adventureCategoryType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the adventureCategoryType corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AdventureCategoryTypeDTO> search(String query, Pageable pageable);
}
