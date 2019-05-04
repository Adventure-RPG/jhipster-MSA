package com.adventure.uaa.web.rest;

import com.adventure.uaa.AdventureUaaApp;
import com.adventure.uaa.config.SecurityBeanOverrideConfiguration;
import com.adventure.uaa.domain.AdventureModelOptions;
import com.adventure.uaa.repository.AdventureModelOptionsRepository;
import com.adventure.uaa.repository.search.AdventureModelOptionsSearchRepository;
import com.adventure.uaa.service.AdventureModelOptionsService;
import com.adventure.uaa.service.dto.AdventureModelOptionsDTO;
import com.adventure.uaa.service.mapper.AdventureModelOptionsMapper;
import com.adventure.uaa.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
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
 * Integration tests for the {@Link AdventureModelOptionsResource} REST controller.
 */
@SpringBootTest(classes = AdventureUaaApp.class)
public class AdventureModelOptionsResourceIT {

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    @Autowired
    private AdventureModelOptionsRepository adventureModelOptionsRepository;

    @Autowired
    private AdventureModelOptionsMapper adventureModelOptionsMapper;

    @Autowired
    private AdventureModelOptionsService adventureModelOptionsService;

    /**
     * This repository is mocked in the com.adventure.uaa.repository.search test package.
     *
     * @see com.adventure.uaa.repository.search.AdventureModelOptionsSearchRepositoryMockConfiguration
     */
    @Autowired
    private AdventureModelOptionsSearchRepository mockAdventureModelOptionsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAdventureModelOptionsMockMvc;

    private AdventureModelOptions adventureModelOptions;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdventureModelOptionsResource adventureModelOptionsResource = new AdventureModelOptionsResource(adventureModelOptionsService);
        this.restAdventureModelOptionsMockMvc = MockMvcBuilders.standaloneSetup(adventureModelOptionsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdventureModelOptions createEntity(EntityManager em) {
        AdventureModelOptions adventureModelOptions = new AdventureModelOptions()
            .color(DEFAULT_COLOR);
        return adventureModelOptions;
    }

    @BeforeEach
    public void initTest() {
        adventureModelOptions = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdventureModelOptions() throws Exception {
        int databaseSizeBeforeCreate = adventureModelOptionsRepository.findAll().size();

        // Create the AdventureModelOptions
        AdventureModelOptionsDTO adventureModelOptionsDTO = adventureModelOptionsMapper.toDto(adventureModelOptions);
        restAdventureModelOptionsMockMvc.perform(post("/api/adventure-model-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureModelOptionsDTO)))
            .andExpect(status().isCreated());

        // Validate the AdventureModelOptions in the database
        List<AdventureModelOptions> adventureModelOptionsList = adventureModelOptionsRepository.findAll();
        assertThat(adventureModelOptionsList).hasSize(databaseSizeBeforeCreate + 1);
        AdventureModelOptions testAdventureModelOptions = adventureModelOptionsList.get(adventureModelOptionsList.size() - 1);
        assertThat(testAdventureModelOptions.getColor()).isEqualTo(DEFAULT_COLOR);

        // Validate the AdventureModelOptions in Elasticsearch
        verify(mockAdventureModelOptionsSearchRepository, times(1)).save(testAdventureModelOptions);
    }

    @Test
    @Transactional
    public void createAdventureModelOptionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adventureModelOptionsRepository.findAll().size();

        // Create the AdventureModelOptions with an existing ID
        adventureModelOptions.setId(1L);
        AdventureModelOptionsDTO adventureModelOptionsDTO = adventureModelOptionsMapper.toDto(adventureModelOptions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdventureModelOptionsMockMvc.perform(post("/api/adventure-model-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureModelOptionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdventureModelOptions in the database
        List<AdventureModelOptions> adventureModelOptionsList = adventureModelOptionsRepository.findAll();
        assertThat(adventureModelOptionsList).hasSize(databaseSizeBeforeCreate);

        // Validate the AdventureModelOptions in Elasticsearch
        verify(mockAdventureModelOptionsSearchRepository, times(0)).save(adventureModelOptions);
    }


    @Test
    @Transactional
    public void getAllAdventureModelOptions() throws Exception {
        // Initialize the database
        adventureModelOptionsRepository.saveAndFlush(adventureModelOptions);

        // Get all the adventureModelOptionsList
        restAdventureModelOptionsMockMvc.perform(get("/api/adventure-model-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adventureModelOptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())));
    }
    
    @Test
    @Transactional
    public void getAdventureModelOptions() throws Exception {
        // Initialize the database
        adventureModelOptionsRepository.saveAndFlush(adventureModelOptions);

        // Get the adventureModelOptions
        restAdventureModelOptionsMockMvc.perform(get("/api/adventure-model-options/{id}", adventureModelOptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adventureModelOptions.getId().intValue()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAdventureModelOptions() throws Exception {
        // Get the adventureModelOptions
        restAdventureModelOptionsMockMvc.perform(get("/api/adventure-model-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdventureModelOptions() throws Exception {
        // Initialize the database
        adventureModelOptionsRepository.saveAndFlush(adventureModelOptions);

        int databaseSizeBeforeUpdate = adventureModelOptionsRepository.findAll().size();

        // Update the adventureModelOptions
        AdventureModelOptions updatedAdventureModelOptions = adventureModelOptionsRepository.findById(adventureModelOptions.getId()).get();
        // Disconnect from session so that the updates on updatedAdventureModelOptions are not directly saved in db
        em.detach(updatedAdventureModelOptions);
        updatedAdventureModelOptions
            .color(UPDATED_COLOR);
        AdventureModelOptionsDTO adventureModelOptionsDTO = adventureModelOptionsMapper.toDto(updatedAdventureModelOptions);

        restAdventureModelOptionsMockMvc.perform(put("/api/adventure-model-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureModelOptionsDTO)))
            .andExpect(status().isOk());

        // Validate the AdventureModelOptions in the database
        List<AdventureModelOptions> adventureModelOptionsList = adventureModelOptionsRepository.findAll();
        assertThat(adventureModelOptionsList).hasSize(databaseSizeBeforeUpdate);
        AdventureModelOptions testAdventureModelOptions = adventureModelOptionsList.get(adventureModelOptionsList.size() - 1);
        assertThat(testAdventureModelOptions.getColor()).isEqualTo(UPDATED_COLOR);

        // Validate the AdventureModelOptions in Elasticsearch
        verify(mockAdventureModelOptionsSearchRepository, times(1)).save(testAdventureModelOptions);
    }

    @Test
    @Transactional
    public void updateNonExistingAdventureModelOptions() throws Exception {
        int databaseSizeBeforeUpdate = adventureModelOptionsRepository.findAll().size();

        // Create the AdventureModelOptions
        AdventureModelOptionsDTO adventureModelOptionsDTO = adventureModelOptionsMapper.toDto(adventureModelOptions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdventureModelOptionsMockMvc.perform(put("/api/adventure-model-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureModelOptionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdventureModelOptions in the database
        List<AdventureModelOptions> adventureModelOptionsList = adventureModelOptionsRepository.findAll();
        assertThat(adventureModelOptionsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AdventureModelOptions in Elasticsearch
        verify(mockAdventureModelOptionsSearchRepository, times(0)).save(adventureModelOptions);
    }

    @Test
    @Transactional
    public void deleteAdventureModelOptions() throws Exception {
        // Initialize the database
        adventureModelOptionsRepository.saveAndFlush(adventureModelOptions);

        int databaseSizeBeforeDelete = adventureModelOptionsRepository.findAll().size();

        // Delete the adventureModelOptions
        restAdventureModelOptionsMockMvc.perform(delete("/api/adventure-model-options/{id}", adventureModelOptions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<AdventureModelOptions> adventureModelOptionsList = adventureModelOptionsRepository.findAll();
        assertThat(adventureModelOptionsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AdventureModelOptions in Elasticsearch
        verify(mockAdventureModelOptionsSearchRepository, times(1)).deleteById(adventureModelOptions.getId());
    }

    @Test
    @Transactional
    public void searchAdventureModelOptions() throws Exception {
        // Initialize the database
        adventureModelOptionsRepository.saveAndFlush(adventureModelOptions);
        when(mockAdventureModelOptionsSearchRepository.search(queryStringQuery("id:" + adventureModelOptions.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(adventureModelOptions), PageRequest.of(0, 1), 1));
        // Search the adventureModelOptions
        restAdventureModelOptionsMockMvc.perform(get("/api/_search/adventure-model-options?query=id:" + adventureModelOptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adventureModelOptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdventureModelOptions.class);
        AdventureModelOptions adventureModelOptions1 = new AdventureModelOptions();
        adventureModelOptions1.setId(1L);
        AdventureModelOptions adventureModelOptions2 = new AdventureModelOptions();
        adventureModelOptions2.setId(adventureModelOptions1.getId());
        assertThat(adventureModelOptions1).isEqualTo(adventureModelOptions2);
        adventureModelOptions2.setId(2L);
        assertThat(adventureModelOptions1).isNotEqualTo(adventureModelOptions2);
        adventureModelOptions1.setId(null);
        assertThat(adventureModelOptions1).isNotEqualTo(adventureModelOptions2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdventureModelOptionsDTO.class);
        AdventureModelOptionsDTO adventureModelOptionsDTO1 = new AdventureModelOptionsDTO();
        adventureModelOptionsDTO1.setId(1L);
        AdventureModelOptionsDTO adventureModelOptionsDTO2 = new AdventureModelOptionsDTO();
        assertThat(adventureModelOptionsDTO1).isNotEqualTo(adventureModelOptionsDTO2);
        adventureModelOptionsDTO2.setId(adventureModelOptionsDTO1.getId());
        assertThat(adventureModelOptionsDTO1).isEqualTo(adventureModelOptionsDTO2);
        adventureModelOptionsDTO2.setId(2L);
        assertThat(adventureModelOptionsDTO1).isNotEqualTo(adventureModelOptionsDTO2);
        adventureModelOptionsDTO1.setId(null);
        assertThat(adventureModelOptionsDTO1).isNotEqualTo(adventureModelOptionsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(adventureModelOptionsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(adventureModelOptionsMapper.fromId(null)).isNull();
    }
}
