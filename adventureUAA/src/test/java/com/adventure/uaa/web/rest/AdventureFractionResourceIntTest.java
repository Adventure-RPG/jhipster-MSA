package com.adventure.uaa.web.rest;

import com.adventure.uaa.AdventureUaaApp;

import com.adventure.uaa.config.SecurityBeanOverrideConfiguration;

import com.adventure.uaa.domain.AdventureFraction;
import com.adventure.uaa.repository.AdventureFractionRepository;
import com.adventure.uaa.repository.search.AdventureFractionSearchRepository;
import com.adventure.uaa.service.AdventureFractionService;
import com.adventure.uaa.service.dto.AdventureFractionDTO;
import com.adventure.uaa.service.mapper.AdventureFractionMapper;
import com.adventure.uaa.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.springframework.util.Base64Utils;
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
 * Test class for the AdventureFractionResource REST controller.
 *
 * @see AdventureFractionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdventureUaaApp.class)
public class AdventureFractionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private AdventureFractionRepository adventureFractionRepository;

    @Autowired
    private AdventureFractionMapper adventureFractionMapper;

    @Autowired
    private AdventureFractionService adventureFractionService;

    /**
     * This repository is mocked in the com.adventure.uaa.repository.search test package.
     *
     * @see com.adventure.uaa.repository.search.AdventureFractionSearchRepositoryMockConfiguration
     */
    @Autowired
    private AdventureFractionSearchRepository mockAdventureFractionSearchRepository;

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

    private MockMvc restAdventureFractionMockMvc;

    private AdventureFraction adventureFraction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdventureFractionResource adventureFractionResource = new AdventureFractionResource(adventureFractionService);
        this.restAdventureFractionMockMvc = MockMvcBuilders.standaloneSetup(adventureFractionResource)
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
    public static AdventureFraction createEntity(EntityManager em) {
        AdventureFraction adventureFraction = new AdventureFraction()
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return adventureFraction;
    }

    @Before
    public void initTest() {
        adventureFraction = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdventureFraction() throws Exception {
        int databaseSizeBeforeCreate = adventureFractionRepository.findAll().size();

        // Create the AdventureFraction
        AdventureFractionDTO adventureFractionDTO = adventureFractionMapper.toDto(adventureFraction);
        restAdventureFractionMockMvc.perform(post("/api/adventure-fractions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureFractionDTO)))
            .andExpect(status().isCreated());

        // Validate the AdventureFraction in the database
        List<AdventureFraction> adventureFractionList = adventureFractionRepository.findAll();
        assertThat(adventureFractionList).hasSize(databaseSizeBeforeCreate + 1);
        AdventureFraction testAdventureFraction = adventureFractionList.get(adventureFractionList.size() - 1);
        assertThat(testAdventureFraction.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdventureFraction.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testAdventureFraction.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testAdventureFraction.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);

        // Validate the AdventureFraction in Elasticsearch
        verify(mockAdventureFractionSearchRepository, times(1)).save(testAdventureFraction);
    }

    @Test
    @Transactional
    public void createAdventureFractionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adventureFractionRepository.findAll().size();

        // Create the AdventureFraction with an existing ID
        adventureFraction.setId(1L);
        AdventureFractionDTO adventureFractionDTO = adventureFractionMapper.toDto(adventureFraction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdventureFractionMockMvc.perform(post("/api/adventure-fractions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureFractionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdventureFraction in the database
        List<AdventureFraction> adventureFractionList = adventureFractionRepository.findAll();
        assertThat(adventureFractionList).hasSize(databaseSizeBeforeCreate);

        // Validate the AdventureFraction in Elasticsearch
        verify(mockAdventureFractionSearchRepository, times(0)).save(adventureFraction);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureFractionRepository.findAll().size();
        // set the field null
        adventureFraction.setName(null);

        // Create the AdventureFraction, which fails.
        AdventureFractionDTO adventureFractionDTO = adventureFractionMapper.toDto(adventureFraction);

        restAdventureFractionMockMvc.perform(post("/api/adventure-fractions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureFractionDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureFraction> adventureFractionList = adventureFractionRepository.findAll();
        assertThat(adventureFractionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureFractionRepository.findAll().size();
        // set the field null
        adventureFraction.setDesc(null);

        // Create the AdventureFraction, which fails.
        AdventureFractionDTO adventureFractionDTO = adventureFractionMapper.toDto(adventureFraction);

        restAdventureFractionMockMvc.perform(post("/api/adventure-fractions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureFractionDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureFraction> adventureFractionList = adventureFractionRepository.findAll();
        assertThat(adventureFractionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdventureFractions() throws Exception {
        // Initialize the database
        adventureFractionRepository.saveAndFlush(adventureFraction);

        // Get all the adventureFractionList
        restAdventureFractionMockMvc.perform(get("/api/adventure-fractions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adventureFraction.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getAdventureFraction() throws Exception {
        // Initialize the database
        adventureFractionRepository.saveAndFlush(adventureFraction);

        // Get the adventureFraction
        restAdventureFractionMockMvc.perform(get("/api/adventure-fractions/{id}", adventureFraction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adventureFraction.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingAdventureFraction() throws Exception {
        // Get the adventureFraction
        restAdventureFractionMockMvc.perform(get("/api/adventure-fractions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdventureFraction() throws Exception {
        // Initialize the database
        adventureFractionRepository.saveAndFlush(adventureFraction);

        int databaseSizeBeforeUpdate = adventureFractionRepository.findAll().size();

        // Update the adventureFraction
        AdventureFraction updatedAdventureFraction = adventureFractionRepository.findById(adventureFraction.getId()).get();
        // Disconnect from session so that the updates on updatedAdventureFraction are not directly saved in db
        em.detach(updatedAdventureFraction);
        updatedAdventureFraction
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        AdventureFractionDTO adventureFractionDTO = adventureFractionMapper.toDto(updatedAdventureFraction);

        restAdventureFractionMockMvc.perform(put("/api/adventure-fractions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureFractionDTO)))
            .andExpect(status().isOk());

        // Validate the AdventureFraction in the database
        List<AdventureFraction> adventureFractionList = adventureFractionRepository.findAll();
        assertThat(adventureFractionList).hasSize(databaseSizeBeforeUpdate);
        AdventureFraction testAdventureFraction = adventureFractionList.get(adventureFractionList.size() - 1);
        assertThat(testAdventureFraction.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdventureFraction.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testAdventureFraction.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testAdventureFraction.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);

        // Validate the AdventureFraction in Elasticsearch
        verify(mockAdventureFractionSearchRepository, times(1)).save(testAdventureFraction);
    }

    @Test
    @Transactional
    public void updateNonExistingAdventureFraction() throws Exception {
        int databaseSizeBeforeUpdate = adventureFractionRepository.findAll().size();

        // Create the AdventureFraction
        AdventureFractionDTO adventureFractionDTO = adventureFractionMapper.toDto(adventureFraction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdventureFractionMockMvc.perform(put("/api/adventure-fractions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureFractionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdventureFraction in the database
        List<AdventureFraction> adventureFractionList = adventureFractionRepository.findAll();
        assertThat(adventureFractionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AdventureFraction in Elasticsearch
        verify(mockAdventureFractionSearchRepository, times(0)).save(adventureFraction);
    }

    @Test
    @Transactional
    public void deleteAdventureFraction() throws Exception {
        // Initialize the database
        adventureFractionRepository.saveAndFlush(adventureFraction);

        int databaseSizeBeforeDelete = adventureFractionRepository.findAll().size();

        // Delete the adventureFraction
        restAdventureFractionMockMvc.perform(delete("/api/adventure-fractions/{id}", adventureFraction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AdventureFraction> adventureFractionList = adventureFractionRepository.findAll();
        assertThat(adventureFractionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AdventureFraction in Elasticsearch
        verify(mockAdventureFractionSearchRepository, times(1)).deleteById(adventureFraction.getId());
    }

    @Test
    @Transactional
    public void searchAdventureFraction() throws Exception {
        // Initialize the database
        adventureFractionRepository.saveAndFlush(adventureFraction);
        when(mockAdventureFractionSearchRepository.search(queryStringQuery("id:" + adventureFraction.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(adventureFraction), PageRequest.of(0, 1), 1));
        // Search the adventureFraction
        restAdventureFractionMockMvc.perform(get("/api/_search/adventure-fractions?query=id:" + adventureFraction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adventureFraction.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdventureFraction.class);
        AdventureFraction adventureFraction1 = new AdventureFraction();
        adventureFraction1.setId(1L);
        AdventureFraction adventureFraction2 = new AdventureFraction();
        adventureFraction2.setId(adventureFraction1.getId());
        assertThat(adventureFraction1).isEqualTo(adventureFraction2);
        adventureFraction2.setId(2L);
        assertThat(adventureFraction1).isNotEqualTo(adventureFraction2);
        adventureFraction1.setId(null);
        assertThat(adventureFraction1).isNotEqualTo(adventureFraction2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdventureFractionDTO.class);
        AdventureFractionDTO adventureFractionDTO1 = new AdventureFractionDTO();
        adventureFractionDTO1.setId(1L);
        AdventureFractionDTO adventureFractionDTO2 = new AdventureFractionDTO();
        assertThat(adventureFractionDTO1).isNotEqualTo(adventureFractionDTO2);
        adventureFractionDTO2.setId(adventureFractionDTO1.getId());
        assertThat(adventureFractionDTO1).isEqualTo(adventureFractionDTO2);
        adventureFractionDTO2.setId(2L);
        assertThat(adventureFractionDTO1).isNotEqualTo(adventureFractionDTO2);
        adventureFractionDTO1.setId(null);
        assertThat(adventureFractionDTO1).isNotEqualTo(adventureFractionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(adventureFractionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(adventureFractionMapper.fromId(null)).isNull();
    }
}
