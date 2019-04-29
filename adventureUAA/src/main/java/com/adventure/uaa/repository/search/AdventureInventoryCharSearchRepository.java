package com.adventure.uaa.repository.search;

import com.adventure.uaa.domain.AdventureInventoryChar;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdventureInventoryChar entity.
 */
public interface AdventureInventoryCharSearchRepository extends ElasticsearchRepository<AdventureInventoryChar, Long> {
}
