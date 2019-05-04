package com.adventure.site.repository.search;

import com.adventure.site.domain.AdventureCategoryType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AdventureCategoryType} entity.
 */
public interface AdventureCategoryTypeSearchRepository extends ElasticsearchRepository<AdventureCategoryType, String> {
}
