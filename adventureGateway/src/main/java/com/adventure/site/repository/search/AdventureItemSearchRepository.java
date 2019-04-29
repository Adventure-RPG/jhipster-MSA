package com.adventure.site.repository.search;

import com.adventure.site.domain.AdventureItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdventureItem entity.
 */
public interface AdventureItemSearchRepository extends ElasticsearchRepository<AdventureItem, String> {
}
