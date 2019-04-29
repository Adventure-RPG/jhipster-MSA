package com.adventure.uaa.repository.search;

import com.adventure.uaa.domain.AdventureModelOptions;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdventureModelOptions entity.
 */
public interface AdventureModelOptionsSearchRepository extends ElasticsearchRepository<AdventureModelOptions, Long> {
}
