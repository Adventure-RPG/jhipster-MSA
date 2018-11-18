package com.adventure.uaa.repository.search;

import com.adventure.uaa.domain.AdventureAttributes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdventureAttributes entity.
 */
public interface AdventureAttributesSearchRepository extends ElasticsearchRepository<AdventureAttributes, Long> {
}
