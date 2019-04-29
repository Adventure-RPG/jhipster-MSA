package com.adventure.uaa.service.impl;

import com.adventure.uaa.service.AdventureRaceOptionsService;
import com.adventure.uaa.domain.AdventureRaceOptions;
import com.adventure.uaa.repository.AdventureRaceOptionsRepository;
import com.adventure.uaa.repository.search.AdventureRaceOptionsSearchRepository;
import com.adventure.uaa.service.dto.AdventureRaceOptionsDTO;
import com.adventure.uaa.service.mapper.AdventureRaceOptionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AdventureRaceOptions.
 */
@Service
@Transactional
public class AdventureRaceOptionsServiceImpl implements AdventureRaceOptionsService {

    private final Logger log = LoggerFactory.getLogger(AdventureRaceOptionsServiceImpl.class);

    private final AdventureRaceOptionsRepository adventureRaceOptionsRepository;

    private final AdventureRaceOptionsMapper adventureRaceOptionsMapper;

    private final AdventureRaceOptionsSearchRepository adventureRaceOptionsSearchRepository;

    public AdventureRaceOptionsServiceImpl(AdventureRaceOptionsRepository adventureRaceOptionsRepository, AdventureRaceOptionsMapper adventureRaceOptionsMapper, AdventureRaceOptionsSearchRepository adventureRaceOptionsSearchRepository) {
        this.adventureRaceOptionsRepository = adventureRaceOptionsRepository;
        this.adventureRaceOptionsMapper = adventureRaceOptionsMapper;
        this.adventureRaceOptionsSearchRepository = adventureRaceOptionsSearchRepository;
    }

    /**
     * Save a adventureRaceOptions.
     *
     * @param adventureRaceOptionsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdventureRaceOptionsDTO save(AdventureRaceOptionsDTO adventureRaceOptionsDTO) {
        log.debug("Request to save AdventureRaceOptions : {}", adventureRaceOptionsDTO);
        AdventureRaceOptions adventureRaceOptions = adventureRaceOptionsMapper.toEntity(adventureRaceOptionsDTO);
        adventureRaceOptions = adventureRaceOptionsRepository.save(adventureRaceOptions);
        AdventureRaceOptionsDTO result = adventureRaceOptionsMapper.toDto(adventureRaceOptions);
        adventureRaceOptionsSearchRepository.save(adventureRaceOptions);
        return result;
    }

    /**
     * Get all the adventureRaceOptions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdventureRaceOptionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdventureRaceOptions");
        return adventureRaceOptionsRepository.findAll(pageable)
            .map(adventureRaceOptionsMapper::toDto);
    }

    /**
     * Get all the AdventureRaceOptions with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<AdventureRaceOptionsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return adventureRaceOptionsRepository.findAllWithEagerRelationships(pageable).map(adventureRaceOptionsMapper::toDto);
    }
    

    /**
     * Get one adventureRaceOptions by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AdventureRaceOptionsDTO> findOne(Long id) {
        log.debug("Request to get AdventureRaceOptions : {}", id);
        return adventureRaceOptionsRepository.findOneWithEagerRelationships(id)
            .map(adventureRaceOptionsMapper::toDto);
    }

    /**
     * Delete the adventureRaceOptions by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdventureRaceOptions : {}", id);
        adventureRaceOptionsRepository.deleteById(id);
        adventureRaceOptionsSearchRepository.deleteById(id);
    }

    /**
     * Search for the adventureRaceOptions corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdventureRaceOptionsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AdventureRaceOptions for query {}", query);
        return adventureRaceOptionsSearchRepository.search(queryStringQuery(query), pageable)
            .map(adventureRaceOptionsMapper::toDto);
    }
}
