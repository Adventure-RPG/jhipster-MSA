package com.adventure.uaa.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of AdventureModelOptionsSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AdventureModelOptionsSearchRepositoryMockConfiguration {

    @MockBean
    private AdventureModelOptionsSearchRepository mockAdventureModelOptionsSearchRepository;

}
