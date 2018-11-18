package com.adventure.uaa.repository.search;

import com.adventure.uaa.domain.AdventureSkill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdventureSkill entity.
 */
public interface AdventureSkillSearchRepository extends ElasticsearchRepository<AdventureSkill, Long> {
}
