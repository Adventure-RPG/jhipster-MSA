package com.adventure.site.repository.search;

import com.adventure.site.domain.AdventureFraction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdventureFraction entity.
 */
public interface AdventureFractionSearchRepository extends ElasticsearchRepository<AdventureFraction, String> {
}
