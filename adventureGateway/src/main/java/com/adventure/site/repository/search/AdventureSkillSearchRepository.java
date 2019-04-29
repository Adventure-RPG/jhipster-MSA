package com.adventure.site.repository.search;

import com.adventure.site.domain.AdventureSkill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdventureSkill entity.
 */
public interface AdventureSkillSearchRepository extends ElasticsearchRepository<AdventureSkill, String> {
}
