package com.adventure.site.repository.search;

import com.adventure.site.domain.AdventureInventoryChar;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AdventureInventoryChar} entity.
 */
public interface AdventureInventoryCharSearchRepository extends ElasticsearchRepository<AdventureInventoryChar, String> {
}
