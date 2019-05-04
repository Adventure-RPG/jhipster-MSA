package com.adventure.uaa.service.impl;

import com.adventure.uaa.service.AdventureAttributesService;
import com.adventure.uaa.domain.AdventureAttributes;
import com.adventure.uaa.repository.AdventureAttributesRepository;
import com.adventure.uaa.repository.search.AdventureAttributesSearchRepository;
import com.adventure.uaa.service.dto.AdventureAttributesDTO;
import com.adventure.uaa.service.mapper.AdventureAttributesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link AdventureAttributes}.
 */
@Service
@Transactional
public class AdventureAttributesServiceImpl implements AdventureAttributesService {

    private final Logger log = LoggerFactory.getLogger(AdventureAttributesServiceImpl.class);

    private final AdventureAttributesRepository adventureAttributesRepository;

    private final AdventureAttributesMapper adventureAttributesMapper;

    private final AdventureAttributesSearchRepository adventureAttributesSearchRepository;

    public AdventureAttributesServiceImpl(AdventureAttributesRepository adventureAttributesRepository, AdventureAttributesMapper adventureAttributesMapper, AdventureAttributesSearchRepository adventureAttributesSearchRepository) {
        this.adventureAttributesRepository = adventureAttributesRepository;
        this.adventureAttributesMapper = adventureAttributesMapper;
        this.adventureAttributesSearchRepository = adventureAttributesSearchRepository;
    }

    /**
     * Save a adventureAttributes.
     *
     * @param adventureAttributesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AdventureAttributesDTO save(AdventureAttributesDTO adventureAttributesDTO) {
        log.debug("Request to save AdventureAttributes : {}", adventureAttributesDTO);
        AdventureAttributes adventureAttributes = adventureAttributesMapper.toEntity(adventureAttributesDTO);
        adventureAttributes = adventureAttributesRepository.save(adventureAttributes);
        AdventureAttributesDTO result = adventureAttributesMapper.toDto(adventureAttributes);
        adventureAttributesSearchRepository.save(adventureAttributes);
        return result;
    }

    /**
     * Get all the adventureAttributes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdventureAttributesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdventureAttributes");
        return adventureAttributesRepository.findAll(pageable)
            .map(adventureAttributesMapper::toDto);
    }


    /**
     * Get one adventureAttributes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AdventureAttributesDTO> findOne(Long id) {
        log.debug("Request to get AdventureAttributes : {}", id);
        return adventureAttributesRepository.findById(id)
            .map(adventureAttributesMapper::toDto);
    }

    /**
     * Delete the adventureAttributes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdventureAttributes : {}", id);
        adventureAttributesRepository.deleteById(id);
        adventureAttributesSearchRepository.deleteById(id);
    }

    /**
     * Search for the adventureAttributes corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdventureAttributesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AdventureAttributes for query {}", query);
        return adventureAttributesSearchRepository.search(queryStringQuery(query), pageable)
            .map(adventureAttributesMapper::toDto);
    }
}
