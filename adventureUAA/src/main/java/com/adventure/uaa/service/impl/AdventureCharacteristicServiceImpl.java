package com.adventure.uaa.service.impl;

import com.adventure.uaa.service.AdventureCharacteristicService;
import com.adventure.uaa.domain.AdventureCharacteristic;
import com.adventure.uaa.repository.AdventureCharacteristicRepository;
import com.adventure.uaa.repository.search.AdventureCharacteristicSearchRepository;
import com.adventure.uaa.service.dto.AdventureCharacteristicDTO;
import com.adventure.uaa.service.mapper.AdventureCharacteristicMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AdventureCharacteristic.
 */
@Service
@Transactional
public class AdventureCharacteristicServiceImpl implements AdventureCharacteristicService {

    private final Logger log = LoggerFactory.getLogger(AdventureCharacteristicServiceImpl.class);

    private final AdventureCharacteristicRepository adventureCharacteristicRepository;

    private final AdventureCharacteristicMapper adventureCharacteristicMapper;

    private final AdventureCharacteristicSearchRepository adventureCharacteristicSearchRepository;

    public AdventureCharacteristicServiceImpl(AdventureCharacteristicRepository adventureCharacteristicRepository, AdventureCharacteristicMapper adventureCharacteristicMapper, AdventureCharacteristicSearchRepository adventureCharacteristicSearchRepository) {
        this.adventureCharacteristicRepository = adventureCharacteristicRepository;
        this.adventureCharacteristicMapper = adventureCharacteristicMapper;
        this.adventureCharacteristicSearchRepository = adventureCharacteristicSearchRepository;
    }

    /**
     * Save a adventureCharacteristic.
     *
     * @param adventureCharacteristicDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdventureCharacteristicDTO save(AdventureCharacteristicDTO adventureCharacteristicDTO) {
        log.debug("Request to save AdventureCharacteristic : {}", adventureCharacteristicDTO);

        AdventureCharacteristic adventureCharacteristic = adventureCharacteristicMapper.toEntity(adventureCharacteristicDTO);
        adventureCharacteristic = adventureCharacteristicRepository.save(adventureCharacteristic);
        AdventureCharacteristicDTO result = adventureCharacteristicMapper.toDto(adventureCharacteristic);
        adventureCharacteristicSearchRepository.save(adventureCharacteristic);
        return result;
    }

    /**
     * Get all the adventureCharacteristics.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdventureCharacteristicDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdventureCharacteristics");
        return adventureCharacteristicRepository.findAll(pageable)
            .map(adventureCharacteristicMapper::toDto);
    }


    /**
     * Get one adventureCharacteristic by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AdventureCharacteristicDTO> findOne(Long id) {
        log.debug("Request to get AdventureCharacteristic : {}", id);
        return adventureCharacteristicRepository.findById(id)
            .map(adventureCharacteristicMapper::toDto);
    }

    /**
     * Delete the adventureCharacteristic by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdventureCharacteristic : {}", id);
        adventureCharacteristicRepository.deleteById(id);
        adventureCharacteristicSearchRepository.deleteById(id);
    }

    /**
     * Search for the adventureCharacteristic corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdventureCharacteristicDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AdventureCharacteristics for query {}", query);
        return adventureCharacteristicSearchRepository.search(queryStringQuery(query), pageable)
            .map(adventureCharacteristicMapper::toDto);
    }
}
