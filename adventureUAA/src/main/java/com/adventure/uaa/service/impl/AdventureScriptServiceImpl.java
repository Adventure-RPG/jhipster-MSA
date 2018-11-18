package com.adventure.uaa.service.impl;

import com.adventure.uaa.service.AdventureScriptService;
import com.adventure.uaa.domain.AdventureScript;
import com.adventure.uaa.repository.AdventureScriptRepository;
import com.adventure.uaa.repository.search.AdventureScriptSearchRepository;
import com.adventure.uaa.service.dto.AdventureScriptDTO;
import com.adventure.uaa.service.mapper.AdventureScriptMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AdventureScript.
 */
@Service
@Transactional
public class AdventureScriptServiceImpl implements AdventureScriptService {

    private final Logger log = LoggerFactory.getLogger(AdventureScriptServiceImpl.class);

    private final AdventureScriptRepository adventureScriptRepository;

    private final AdventureScriptMapper adventureScriptMapper;

    private final AdventureScriptSearchRepository adventureScriptSearchRepository;

    public AdventureScriptServiceImpl(AdventureScriptRepository adventureScriptRepository, AdventureScriptMapper adventureScriptMapper, AdventureScriptSearchRepository adventureScriptSearchRepository) {
        this.adventureScriptRepository = adventureScriptRepository;
        this.adventureScriptMapper = adventureScriptMapper;
        this.adventureScriptSearchRepository = adventureScriptSearchRepository;
    }

    /**
     * Save a adventureScript.
     *
     * @param adventureScriptDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdventureScriptDTO save(AdventureScriptDTO adventureScriptDTO) {
        log.debug("Request to save AdventureScript : {}", adventureScriptDTO);

        AdventureScript adventureScript = adventureScriptMapper.toEntity(adventureScriptDTO);
        adventureScript = adventureScriptRepository.save(adventureScript);
        AdventureScriptDTO result = adventureScriptMapper.toDto(adventureScript);
        adventureScriptSearchRepository.save(adventureScript);
        return result;
    }

    /**
     * Get all the adventureScripts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdventureScriptDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdventureScripts");
        return adventureScriptRepository.findAll(pageable)
            .map(adventureScriptMapper::toDto);
    }


    /**
     * Get one adventureScript by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AdventureScriptDTO> findOne(Long id) {
        log.debug("Request to get AdventureScript : {}", id);
        return adventureScriptRepository.findById(id)
            .map(adventureScriptMapper::toDto);
    }

    /**
     * Delete the adventureScript by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdventureScript : {}", id);
        adventureScriptRepository.deleteById(id);
        adventureScriptSearchRepository.deleteById(id);
    }

    /**
     * Search for the adventureScript corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdventureScriptDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AdventureScripts for query {}", query);
        return adventureScriptSearchRepository.search(queryStringQuery(query), pageable)
            .map(adventureScriptMapper::toDto);
    }
}
