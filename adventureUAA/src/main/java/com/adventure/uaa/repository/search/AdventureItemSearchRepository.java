package com.adventure.uaa.repository.search;

import com.adventure.uaa.domain.AdventureItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AdventureItem} entity.
 */
public interface AdventureItemSearchRepository extends ElasticsearchRepository<AdventureItem, Long> {
}
