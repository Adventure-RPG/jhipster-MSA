package com.adventure.uaa.repository.search;

import com.adventure.uaa.domain.AdventureRaceOptions;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdventureRaceOptions entity.
 */
public interface AdventureRaceOptionsSearchRepository extends ElasticsearchRepository<AdventureRaceOptions, Long> {
}
