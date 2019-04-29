package com.adventure.uaa.service.impl;

import com.adventure.uaa.service.AdventureItemService;
import com.adventure.uaa.domain.AdventureItem;
import com.adventure.uaa.repository.AdventureItemRepository;
import com.adventure.uaa.repository.search.AdventureItemSearchRepository;
import com.adventure.uaa.service.dto.AdventureItemDTO;
import com.adventure.uaa.service.mapper.AdventureItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AdventureItem.
 */
@Service
@Transactional
public class AdventureItemServiceImpl implements AdventureItemService {

    private final Logger log = LoggerFactory.getLogger(AdventureItemServiceImpl.class);

    private final AdventureItemRepository adventureItemRepository;

    private final AdventureItemMapper adventureItemMapper;

    private final AdventureItemSearchRepository adventureItemSearchRepository;

    public AdventureItemServiceImpl(AdventureItemRepository adventureItemRepository, AdventureItemMapper adventureItemMapper, AdventureItemSearchRepository adventureItemSearchRepository) {
        this.adventureItemRepository = adventureItemRepository;
        this.adventureItemMapper = adventureItemMapper;
        this.adventureItemSearchRepository = adventureItemSearchRepository;
    }

    /**
     * Save a adventureItem.
     *
     * @param adventureItemDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdventureItemDTO save(AdventureItemDTO adventureItemDTO) {
        log.debug("Request to save AdventureItem : {}", adventureItemDTO);
        AdventureItem adventureItem = adventureItemMapper.toEntity(adventureItemDTO);
        adventureItem = adventureItemRepository.save(adventureItem);
        AdventureItemDTO result = adventureItemMapper.toDto(adventureItem);
        adventureItemSearchRepository.save(adventureItem);
        return result;
    }

    /**
     * Get all the adventureItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdventureItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdventureItems");
        return adventureItemRepository.findAll(pageable)
            .map(adventureItemMapper::toDto);
    }


    /**
     * Get one adventureItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AdventureItemDTO> findOne(Long id) {
        log.debug("Request to get AdventureItem : {}", id);
        return adventureItemRepository.findById(id)
            .map(adventureItemMapper::toDto);
    }

    /**
     * Delete the adventureItem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdventureItem : {}", id);
        adventureItemRepository.deleteById(id);
        adventureItemSearchRepository.deleteById(id);
    }

    /**
     * Search for the adventureItem corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdventureItemDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AdventureItems for query {}", query);
        return adventureItemSearchRepository.search(queryStringQuery(query), pageable)
            .map(adventureItemMapper::toDto);
    }
}
