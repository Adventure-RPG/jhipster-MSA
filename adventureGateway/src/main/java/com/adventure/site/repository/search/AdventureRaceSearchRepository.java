package com.adventure.site.repository.search;

import com.adventure.site.domain.AdventureRace;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AdventureRace} entity.
 */
public interface AdventureRaceSearchRepository extends ElasticsearchRepository<AdventureRace, String> {
}
