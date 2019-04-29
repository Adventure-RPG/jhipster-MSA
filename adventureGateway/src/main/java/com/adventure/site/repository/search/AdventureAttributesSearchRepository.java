package com.adventure.site.repository.search;

import com.adventure.site.domain.AdventureAttributes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdventureAttributes entity.
 */
public interface AdventureAttributesSearchRepository extends ElasticsearchRepository<AdventureAttributes, String> {
}
