package com.adventure.uaa.service.impl;

import com.adventure.uaa.service.AdventureModelService;
import com.adventure.uaa.domain.AdventureModel;
import com.adventure.uaa.repository.AdventureModelRepository;
import com.adventure.uaa.repository.search.AdventureModelSearchRepository;
import com.adventure.uaa.service.dto.AdventureModelDTO;
import com.adventure.uaa.service.mapper.AdventureModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AdventureModel.
 */
@Service
@Transactional
public class AdventureModelServiceImpl implements AdventureModelService {

    private final Logger log = LoggerFactory.getLogger(AdventureModelServiceImpl.class);

    private final AdventureModelRepository adventureModelRepository;

    private final AdventureModelMapper adventureModelMapper;

    private final AdventureModelSearchRepository adventureModelSearchRepository;

    public AdventureModelServiceImpl(AdventureModelRepository adventureModelRepository, AdventureModelMapper adventureModelMapper, AdventureModelSearchRepository adventureModelSearchRepository) {
        this.adventureModelRepository = adventureModelRepository;
        this.adventureModelMapper = adventureModelMapper;
        this.adventureModelSearchRepository = adventureModelSearchRepository;
    }

    /**
     * Save a adventureModel.
     *
     * @param adventureModelDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdventureModelDTO save(AdventureModelDTO adventureModelDTO) {
        log.debug("Request to save AdventureModel : {}", adventureModelDTO);

        AdventureModel adventureModel = adventureModelMapper.toEntity(adventureModelDTO);
        adventureModel = adventureModelRepository.save(adventureModel);
        AdventureModelDTO result = adventureModelMapper.toDto(adventureModel);
        adventureModelSearchRepository.save(adventureModel);
        return result;
    }

    /**
     * Get all the adventureModels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdventureModelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdventureModels");
        return adventureModelRepository.findAll(pageable)
            .map(adventureModelMapper::toDto);
    }


    /**
     * Get one adventureModel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AdventureModelDTO> findOne(Long id) {
        log.debug("Request to get AdventureModel : {}", id);
        return adventureModelRepository.findById(id)
            .map(adventureModelMapper::toDto);
    }

    /**
     * Delete the adventureModel by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdventureModel : {}", id);
        adventureModelRepository.deleteById(id);
        adventureModelSearchRepository.deleteById(id);
    }

    /**
     * Search for the adventureModel corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdventureModelDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AdventureModels for query {}", query);
        return adventureModelSearchRepository.search(queryStringQuery(query), pageable)
            .map(adventureModelMapper::toDto);
    }
}
