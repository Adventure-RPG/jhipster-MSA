package com.adventure.site.repository.search;

import com.adventure.site.domain.AdventureCharacteristic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdventureCharacteristic entity.
 */
public interface AdventureCharacteristicSearchRepository extends ElasticsearchRepository<AdventureCharacteristic, String> {
}
