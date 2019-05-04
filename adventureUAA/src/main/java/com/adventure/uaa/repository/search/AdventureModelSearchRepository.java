package com.adventure.uaa.repository.search;

import com.adventure.uaa.domain.AdventureModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AdventureModel} entity.
 */
public interface AdventureModelSearchRepository extends ElasticsearchRepository<AdventureModel, Long> {
}
