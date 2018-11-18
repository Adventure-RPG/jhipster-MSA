package com.adventure.site.service;

import com.adventure.site.service.dto.AdventureAccountCharacterDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AdventureAccountCharacter.
 */
public interface AdventureAccountCharacterService {

    /**
     * Save a adventureAccountCharacter.
     *
     * @param adventureAccountCharacterDTO the entity to save
     * @return the persisted entity
     */
    AdventureAccountCharacterDTO save(AdventureAccountCharacterDTO adventureAccountCharacterDTO);

    /**
     * Get all the adventureAccountCharacters.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdventureAccountCharacterDTO> findAll(Pageable pageable);

    /**
     * Get all the AdventureAccountCharacter with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<AdventureAccountCharacterDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" adventureAccountCharacter.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AdventureAccountCharacterDTO> findOne(Long id);

    /**
     * Delete the "id" adventureAccountCharacter.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the adventureAccountCharacter corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdventureAccountCharacterDTO> search(String query, Pageable pageable);
}
