package com.adventure.uaa.repository.search;

import com.adventure.uaa.domain.AdventureCharacteristic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AdventureCharacteristic} entity.
 */
public interface AdventureCharacteristicSearchRepository extends ElasticsearchRepository<AdventureCharacteristic, Long> {
}
