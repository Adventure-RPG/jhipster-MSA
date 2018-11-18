package com.adventure.uaa.web.rest;

import com.adventure.uaa.AdventureUaaApp;

import com.adventure.uaa.config.SecurityBeanOverrideConfiguration;

import com.adventure.uaa.domain.AdventureRaceOptions;
import com.adventure.uaa.repository.AdventureRaceOptionsRepository;
import com.adventure.uaa.repository.search.AdventureRaceOptionsSearchRepository;
import com.adventure.uaa.service.AdventureRaceOptionsService;
import com.adventure.uaa.service.dto.AdventureRaceOptionsDTO;
import com.adventure.uaa.service.mapper.AdventureRaceOptionsMapper;
import com.adventure.uaa.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static com.adventure.uaa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AdventureRaceOptionsResource REST controller.
 *
 * @see AdventureRaceOptionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdventureUaaApp.class)
public class AdventureRaceOptionsResourceIntTest {

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;

    @Autowired
    private AdventureRaceOptionsRepository adventureRaceOptionsRepository;

    @Mock
    private AdventureRaceOptionsRepository adventureRaceOptionsRepositoryMock;

    @Autowired
    private AdventureRaceOptionsMapper adventureRaceOptionsMapper;

    @Mock
    private AdventureRaceOptionsService adventureRaceOptionsServiceMock;

    @Autowired
    private AdventureRaceOptionsService adventureRaceOptionsService;

    /**
     * This repository is mocked in the com.adventure.uaa.repository.search test package.
     *
     * @see com.adventure.uaa.repository.search.AdventureRaceOptionsSearchRepositoryMockConfiguration
     */
    @Autowired
    private AdventureRaceOptionsSearchRepository mockAdventureRaceOptionsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdventureRaceOptionsMockMvc;

    private AdventureRaceOptions adventureRaceOptions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdventureRaceOptionsResource adventureRaceOptionsResource = new AdventureRaceOptionsResource(adventureRaceOptionsService);
        this.restAdventureRaceOptionsMockMvc = MockMvcBuilders.standaloneSetup(adventureRaceOptionsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdventureRaceOptions createEntity(EntityManager em) {
        AdventureRaceOptions adventureRaceOptions = new AdventureRaceOptions()
            .height(DEFAULT_HEIGHT)
            .weight(DEFAULT_WEIGHT);
        return adventureRaceOptions;
    }

    @Before
    public void initTest() {
        adventureRaceOptions = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdventureRaceOptions() throws Exception {
        int databaseSizeBeforeCreate = adventureRaceOptionsRepository.findAll().size();

        // Create the AdventureRaceOptions
        AdventureRaceOptionsDTO adventureRaceOptionsDTO = adventureRaceOptionsMapper.toDto(adventureRaceOptions);
        restAdventureRaceOptionsMockMvc.perform(post("/api/adventure-race-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureRaceOptionsDTO)))
            .andExpect(status().isCreated());

        // Validate the AdventureRaceOptions in the database
        List<AdventureRaceOptions> adventureRaceOptionsList = adventureRaceOptionsRepository.findAll();
        assertThat(adventureRaceOptionsList).hasSize(databaseSizeBeforeCreate + 1);
        AdventureRaceOptions testAdventureRaceOptions = adventureRaceOptionsList.get(adventureRaceOptionsList.size() - 1);
        assertThat(testAdventureRaceOptions.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testAdventureRaceOptions.getWeight()).isEqualTo(DEFAULT_WEIGHT);

        // Validate the AdventureRaceOptions in Elasticsearch
        verify(mockAdventureRaceOptionsSearchRepository, times(1)).save(testAdventureRaceOptions);
    }

    @Test
    @Transactional
    public void createAdventureRaceOptionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adventureRaceOptionsRepository.findAll().size();

        // Create the AdventureRaceOptions with an existing ID
        adventureRaceOptions.setId(1L);
        AdventureRaceOptionsDTO adventureRaceOptionsDTO = adventureRaceOptionsMapper.toDto(adventureRaceOptions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdventureRaceOptionsMockMvc.perform(post("/api/adventure-race-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureRaceOptionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdventureRaceOptions in the database
        List<AdventureRaceOptions> adventureRaceOptionsList = adventureRaceOptionsRepository.findAll();
        assertThat(adventureRaceOptionsList).hasSize(databaseSizeBeforeCreate);

        // Validate the AdventureRaceOptions in Elasticsearch
        verify(mockAdventureRaceOptionsSearchRepository, times(0)).save(adventureRaceOptions);
    }

    @Test
    @Transactional
    public void checkHeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureRaceOptionsRepository.findAll().size();
        // set the field null
        adventureRaceOptions.setHeight(null);

        // Create the AdventureRaceOptions, which fails.
        AdventureRaceOptionsDTO adventureRaceOptionsDTO = adventureRaceOptionsMapper.toDto(adventureRaceOptions);

        restAdventureRaceOptionsMockMvc.perform(post("/api/adventure-race-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureRaceOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureRaceOptions> adventureRaceOptionsList = adventureRaceOptionsRepository.findAll();
        assertThat(adventureRaceOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureRaceOptionsRepository.findAll().size();
        // set the field null
        adventureRaceOptions.setWeight(null);

        // Create the AdventureRaceOptions, which fails.
        AdventureRaceOptionsDTO adventureRaceOptionsDTO = adventureRaceOptionsMapper.toDto(adventureRaceOptions);

        restAdventureRaceOptionsMockMvc.perform(post("/api/adventure-race-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureRaceOptionsDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureRaceOptions> adventureRaceOptionsList = adventureRaceOptionsRepository.findAll();
        assertThat(adventureRaceOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdventureRaceOptions() throws Exception {
        // Initialize the database
        adventureRaceOptionsRepository.saveAndFlush(adventureRaceOptions);

        // Get all the adventureRaceOptionsList
        restAdventureRaceOptionsMockMvc.perform(get("/api/adventure-race-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adventureRaceOptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllAdventureRaceOptionsWithEagerRelationshipsIsEnabled() throws Exception {
        AdventureRaceOptionsResource adventureRaceOptionsResource = new AdventureRaceOptionsResource(adventureRaceOptionsServiceMock);
        when(adventureRaceOptionsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAdventureRaceOptionsMockMvc = MockMvcBuilders.standaloneSetup(adventureRaceOptionsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAdventureRaceOptionsMockMvc.perform(get("/api/adventure-race-options?eagerload=true"))
        .andExpect(status().isOk());

        verify(adventureRaceOptionsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAdventureRaceOptionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        AdventureRaceOptionsResource adventureRaceOptionsResource = new AdventureRaceOptionsResource(adventureRaceOptionsServiceMock);
            when(adventureRaceOptionsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAdventureRaceOptionsMockMvc = MockMvcBuilders.standaloneSetup(adventureRaceOptionsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAdventureRaceOptionsMockMvc.perform(get("/api/adventure-race-options?eagerload=true"))
        .andExpect(status().isOk());

            verify(adventureRaceOptionsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAdventureRaceOptions() throws Exception {
        // Initialize the database
        adventureRaceOptionsRepository.saveAndFlush(adventureRaceOptions);

        // Get the adventureRaceOptions
        restAdventureRaceOptionsMockMvc.perform(get("/api/adventure-race-options/{id}", adventureRaceOptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adventureRaceOptions.getId().intValue()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT));
    }

    @Test
    @Transactional
    public void getNonExistingAdventureRaceOptions() throws Exception {
        // Get the adventureRaceOptions
        restAdventureRaceOptionsMockMvc.perform(get("/api/adventure-race-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdventureRaceOptions() throws Exception {
        // Initialize the database
        adventureRaceOptionsRepository.saveAndFlush(adventureRaceOptions);

        int databaseSizeBeforeUpdate = adventureRaceOptionsRepository.findAll().size();

        // Update the adventureRaceOptions
        AdventureRaceOptions updatedAdventureRaceOptions = adventureRaceOptionsRepository.findById(adventureRaceOptions.getId()).get();
        // Disconnect from session so that the updates on updatedAdventureRaceOptions are not directly saved in db
        em.detach(updatedAdventureRaceOptions);
        updatedAdventureRaceOptions
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT);
        AdventureRaceOptionsDTO adventureRaceOptionsDTO = adventureRaceOptionsMapper.toDto(updatedAdventureRaceOptions);

        restAdventureRaceOptionsMockMvc.perform(put("/api/adventure-race-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureRaceOptionsDTO)))
            .andExpect(status().isOk());

        // Validate the AdventureRaceOptions in the database
        List<AdventureRaceOptions> adventureRaceOptionsList = adventureRaceOptionsRepository.findAll();
        assertThat(adventureRaceOptionsList).hasSize(databaseSizeBeforeUpdate);
        AdventureRaceOptions testAdventureRaceOptions = adventureRaceOptionsList.get(adventureRaceOptionsList.size() - 1);
        assertThat(testAdventureRaceOptions.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testAdventureRaceOptions.getWeight()).isEqualTo(UPDATED_WEIGHT);

        // Validate the AdventureRaceOptions in Elasticsearch
        verify(mockAdventureRaceOptionsSearchRepository, times(1)).save(testAdventureRaceOptions);
    }

    @Test
    @Transactional
    public void updateNonExistingAdventureRaceOptions() throws Exception {
        int databaseSizeBeforeUpdate = adventureRaceOptionsRepository.findAll().size();

        // Create the AdventureRaceOptions
        AdventureRaceOptionsDTO adventureRaceOptionsDTO = adventureRaceOptionsMapper.toDto(adventureRaceOptions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdventureRaceOptionsMockMvc.perform(put("/api/adventure-race-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureRaceOptionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdventureRaceOptions in the database
        List<AdventureRaceOptions> adventureRaceOptionsList = adventureRaceOptionsRepository.findAll();
        assertThat(adventureRaceOptionsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AdventureRaceOptions in Elasticsearch
        verify(mockAdventureRaceOptionsSearchRepository, times(0)).save(adventureRaceOptions);
    }

    @Test
    @Transactional
    public void deleteAdventureRaceOptions() throws Exception {
        // Initialize the database
        adventureRaceOptionsRepository.saveAndFlush(adventureRaceOptions);

        int databaseSizeBeforeDelete = adventureRaceOptionsRepository.findAll().size();

        // Get the adventureRaceOptions
        restAdventureRaceOptionsMockMvc.perform(delete("/api/adventure-race-options/{id}", adventureRaceOptions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AdventureRaceOptions> adventureRaceOptionsList = adventureRaceOptionsRepository.findAll();
        assertThat(adventureRaceOptionsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AdventureRaceOptions in Elasticsearch
        verify(mockAdventureRaceOptionsSearchRepository, times(1)).deleteById(adventureRaceOptions.getId());
    }

    @Test
    @Transactional
    public void searchAdventureRaceOptions() throws Exception {
        // Initialize the database
        adventureRaceOptionsRepository.saveAndFlush(adventureRaceOptions);
        when(mockAdventureRaceOptionsSearchRepository.search(queryStringQuery("id:" + adventureRaceOptions.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(adventureRaceOptions), PageRequest.of(0, 1), 1));
        // Search the adventureRaceOptions
        restAdventureRaceOptionsMockMvc.perform(get("/api/_search/adventure-race-options?query=id:" + adventureRaceOptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adventureRaceOptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdventureRaceOptions.class);
        AdventureRaceOptions adventureRaceOptions1 = new AdventureRaceOptions();
        adventureRaceOptions1.setId(1L);
        AdventureRaceOptions adventureRaceOptions2 = new AdventureRaceOptions();
        adventureRaceOptions2.setId(adventureRaceOptions1.getId());
        assertThat(adventureRaceOptions1).isEqualTo(adventureRaceOptions2);
        adventureRaceOptions2.setId(2L);
        assertThat(adventureRaceOptions1).isNotEqualTo(adventureRaceOptions2);
        adventureRaceOptions1.setId(null);
        assertThat(adventureRaceOptions1).isNotEqualTo(adventureRaceOptions2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdventureRaceOptionsDTO.class);
        AdventureRaceOptionsDTO adventureRaceOptionsDTO1 = new AdventureRaceOptionsDTO();
        adventureRaceOptionsDTO1.setId(1L);
        AdventureRaceOptionsDTO adventureRaceOptionsDTO2 = new AdventureRaceOptionsDTO();
        assertThat(adventureRaceOptionsDTO1).isNotEqualTo(adventureRaceOptionsDTO2);
        adventureRaceOptionsDTO2.setId(adventureRaceOptionsDTO1.getId());
        assertThat(adventureRaceOptionsDTO1).isEqualTo(adventureRaceOptionsDTO2);
        adventureRaceOptionsDTO2.setId(2L);
        assertThat(adventureRaceOptionsDTO1).isNotEqualTo(adventureRaceOptionsDTO2);
        adventureRaceOptionsDTO1.setId(null);
        assertThat(adventureRaceOptionsDTO1).isNotEqualTo(adventureRaceOptionsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(adventureRaceOptionsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(adventureRaceOptionsMapper.fromId(null)).isNull();
    }
}
