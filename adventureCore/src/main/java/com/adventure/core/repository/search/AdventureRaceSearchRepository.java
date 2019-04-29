package com.adventure.core.repository.search;

import com.adventure.core.domain.AdventureRace;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdventureRace entity.
 */
public interface AdventureRaceSearchRepository extends ElasticsearchRepository<AdventureRace, String> {
}
