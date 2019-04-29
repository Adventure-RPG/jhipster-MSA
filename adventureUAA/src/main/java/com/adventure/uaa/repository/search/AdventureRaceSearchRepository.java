package com.adventure.uaa.repository.search;

import com.adventure.uaa.domain.AdventureRace;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdventureRace entity.
 */
public interface AdventureRaceSearchRepository extends ElasticsearchRepository<AdventureRace, Long> {
}
