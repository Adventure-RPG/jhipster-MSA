package com.adventure.site.repository.search;

import com.adventure.site.domain.AdventureScript;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AdventureScript} entity.
 */
public interface AdventureScriptSearchRepository extends ElasticsearchRepository<AdventureScript, String> {
}
