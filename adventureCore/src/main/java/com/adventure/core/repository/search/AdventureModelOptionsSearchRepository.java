package com.adventure.core.repository.search;

import com.adventure.core.domain.AdventureModelOptions;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdventureModelOptions entity.
 */
public interface AdventureModelOptionsSearchRepository extends ElasticsearchRepository<AdventureModelOptions, String> {
}
