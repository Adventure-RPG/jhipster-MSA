package com.adventure.uaa.repository.search;

import com.adventure.uaa.domain.AdventureFraction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdventureFraction entity.
 */
public interface AdventureFractionSearchRepository extends ElasticsearchRepository<AdventureFraction, Long> {
}
