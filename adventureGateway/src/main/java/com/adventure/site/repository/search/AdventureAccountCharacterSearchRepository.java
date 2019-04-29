package com.adventure.site.repository.search;

import com.adventure.site.domain.AdventureAccountCharacter;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdventureAccountCharacter entity.
 */
public interface AdventureAccountCharacterSearchRepository extends ElasticsearchRepository<AdventureAccountCharacter, String> {
}
