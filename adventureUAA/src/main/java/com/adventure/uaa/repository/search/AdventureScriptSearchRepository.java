package com.adventure.uaa.repository.search;

import com.adventure.uaa.domain.AdventureScript;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdventureScript entity.
 */
public interface AdventureScriptSearchRepository extends ElasticsearchRepository<AdventureScript, Long> {
}
