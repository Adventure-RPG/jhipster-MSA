package com.adventure.site.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link AdventureCharacteristicSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AdventureCharacteristicSearchRepositoryMockConfiguration {

    @MockBean
    private AdventureCharacteristicSearchRepository mockAdventureCharacteristicSearchRepository;

}
