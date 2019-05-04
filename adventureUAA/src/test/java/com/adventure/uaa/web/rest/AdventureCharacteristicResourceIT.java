package com.adventure.uaa.web.rest;

import com.adventure.uaa.AdventureUaaApp;
import com.adventure.uaa.config.SecurityBeanOverrideConfiguration;
import com.adventure.uaa.domain.AdventureCharacteristic;
import com.adventure.uaa.repository.AdventureCharacteristicRepository;
import com.adventure.uaa.repository.search.AdventureCharacteristicSearchRepository;
import com.adventure.uaa.service.AdventureCharacteristicService;
import com.adventure.uaa.service.dto.AdventureCharacteristicDTO;
import com.adventure.uaa.service.mapper.AdventureCharacteristicMapper;
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
 * Integration tests for the {@Link AdventureCharacteristicResource} REST controller.
 */
@SpringBootTest(classes = AdventureUaaApp.class)
public class AdventureCharacteristicResourceIT {

    private static final Integer DEFAULT_STRENGTH = 1;
    private static final Integer UPDATED_STRENGTH = 2;

    private static final Integer DEFAULT_AGILITY = 1;
    private static final Integer UPDATED_AGILITY = 2;

    private static final Integer DEFAULT_VITALITY = 1;
    private static final Integer UPDATED_VITALITY = 2;

    private static final Integer DEFAULT_LUCK = 1;
    private static final Integer UPDATED_LUCK = 2;

    private static final Integer DEFAULT_INTELLIGENCE = 1;
    private static final Integer UPDATED_INTELLIGENCE = 2;

    private static final Integer DEFAULT_WISDOM = 1;
    private static final Integer UPDATED_WISDOM = 2;

    private static final Integer DEFAULT_CHARISMA = 1;
    private static final Integer UPDATED_CHARISMA = 2;

    @Autowired
    private AdventureCharacteristicRepository adventureCharacteristicRepository;

    @Autowired
    private AdventureCharacteristicMapper adventureCharacteristicMapper;

    @Autowired
    private AdventureCharacteristicService adventureCharacteristicService;

    /**
     * This repository is mocked in the com.adventure.uaa.repository.search test package.
     *
     * @see com.adventure.uaa.repository.search.AdventureCharacteristicSearchRepositoryMockConfiguration
     */
    @Autowired
    private AdventureCharacteristicSearchRepository mockAdventureCharacteristicSearchRepository;

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

    private MockMvc restAdventureCharacteristicMockMvc;

    private AdventureCharacteristic adventureCharacteristic;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdventureCharacteristicResource adventureCharacteristicResource = new AdventureCharacteristicResource(adventureCharacteristicService);
        this.restAdventureCharacteristicMockMvc = MockMvcBuilders.standaloneSetup(adventureCharacteristicResource)
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
    public static AdventureCharacteristic createEntity(EntityManager em) {
        AdventureCharacteristic adventureCharacteristic = new AdventureCharacteristic()
            .strength(DEFAULT_STRENGTH)
            .agility(DEFAULT_AGILITY)
            .vitality(DEFAULT_VITALITY)
            .luck(DEFAULT_LUCK)
            .intelligence(DEFAULT_INTELLIGENCE)
            .wisdom(DEFAULT_WISDOM)
            .charisma(DEFAULT_CHARISMA);
        return adventureCharacteristic;
    }

    @BeforeEach
    public void initTest() {
        adventureCharacteristic = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdventureCharacteristic() throws Exception {
        int databaseSizeBeforeCreate = adventureCharacteristicRepository.findAll().size();

        // Create the AdventureCharacteristic
        AdventureCharacteristicDTO adventureCharacteristicDTO = adventureCharacteristicMapper.toDto(adventureCharacteristic);
        restAdventureCharacteristicMockMvc.perform(post("/api/adventure-characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureCharacteristicDTO)))
            .andExpect(status().isCreated());

        // Validate the AdventureCharacteristic in the database
        List<AdventureCharacteristic> adventureCharacteristicList = adventureCharacteristicRepository.findAll();
        assertThat(adventureCharacteristicList).hasSize(databaseSizeBeforeCreate + 1);
        AdventureCharacteristic testAdventureCharacteristic = adventureCharacteristicList.get(adventureCharacteristicList.size() - 1);
        assertThat(testAdventureCharacteristic.getStrength()).isEqualTo(DEFAULT_STRENGTH);
        assertThat(testAdventureCharacteristic.getAgility()).isEqualTo(DEFAULT_AGILITY);
        assertThat(testAdventureCharacteristic.getVitality()).isEqualTo(DEFAULT_VITALITY);
        assertThat(testAdventureCharacteristic.getLuck()).isEqualTo(DEFAULT_LUCK);
        assertThat(testAdventureCharacteristic.getIntelligence()).isEqualTo(DEFAULT_INTELLIGENCE);
        assertThat(testAdventureCharacteristic.getWisdom()).isEqualTo(DEFAULT_WISDOM);
        assertThat(testAdventureCharacteristic.getCharisma()).isEqualTo(DEFAULT_CHARISMA);

        // Validate the AdventureCharacteristic in Elasticsearch
        verify(mockAdventureCharacteristicSearchRepository, times(1)).save(testAdventureCharacteristic);
    }

    @Test
    @Transactional
    public void createAdventureCharacteristicWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adventureCharacteristicRepository.findAll().size();

        // Create the AdventureCharacteristic with an existing ID
        adventureCharacteristic.setId(1L);
        AdventureCharacteristicDTO adventureCharacteristicDTO = adventureCharacteristicMapper.toDto(adventureCharacteristic);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdventureCharacteristicMockMvc.perform(post("/api/adventure-characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureCharacteristicDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdventureCharacteristic in the database
        List<AdventureCharacteristic> adventureCharacteristicList = adventureCharacteristicRepository.findAll();
        assertThat(adventureCharacteristicList).hasSize(databaseSizeBeforeCreate);

        // Validate the AdventureCharacteristic in Elasticsearch
        verify(mockAdventureCharacteristicSearchRepository, times(0)).save(adventureCharacteristic);
    }


    @Test
    @Transactional
    public void checkStrengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureCharacteristicRepository.findAll().size();
        // set the field null
        adventureCharacteristic.setStrength(null);

        // Create the AdventureCharacteristic, which fails.
        AdventureCharacteristicDTO adventureCharacteristicDTO = adventureCharacteristicMapper.toDto(adventureCharacteristic);

        restAdventureCharacteristicMockMvc.perform(post("/api/adventure-characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureCharacteristicDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureCharacteristic> adventureCharacteristicList = adventureCharacteristicRepository.findAll();
        assertThat(adventureCharacteristicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgilityIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureCharacteristicRepository.findAll().size();
        // set the field null
        adventureCharacteristic.setAgility(null);

        // Create the AdventureCharacteristic, which fails.
        AdventureCharacteristicDTO adventureCharacteristicDTO = adventureCharacteristicMapper.toDto(adventureCharacteristic);

        restAdventureCharacteristicMockMvc.perform(post("/api/adventure-characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureCharacteristicDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureCharacteristic> adventureCharacteristicList = adventureCharacteristicRepository.findAll();
        assertThat(adventureCharacteristicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVitalityIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureCharacteristicRepository.findAll().size();
        // set the field null
        adventureCharacteristic.setVitality(null);

        // Create the AdventureCharacteristic, which fails.
        AdventureCharacteristicDTO adventureCharacteristicDTO = adventureCharacteristicMapper.toDto(adventureCharacteristic);

        restAdventureCharacteristicMockMvc.perform(post("/api/adventure-characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureCharacteristicDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureCharacteristic> adventureCharacteristicList = adventureCharacteristicRepository.findAll();
        assertThat(adventureCharacteristicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLuckIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureCharacteristicRepository.findAll().size();
        // set the field null
        adventureCharacteristic.setLuck(null);

        // Create the AdventureCharacteristic, which fails.
        AdventureCharacteristicDTO adventureCharacteristicDTO = adventureCharacteristicMapper.toDto(adventureCharacteristic);

        restAdventureCharacteristicMockMvc.perform(post("/api/adventure-characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureCharacteristicDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureCharacteristic> adventureCharacteristicList = adventureCharacteristicRepository.findAll();
        assertThat(adventureCharacteristicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIntelligenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureCharacteristicRepository.findAll().size();
        // set the field null
        adventureCharacteristic.setIntelligence(null);

        // Create the AdventureCharacteristic, which fails.
        AdventureCharacteristicDTO adventureCharacteristicDTO = adventureCharacteristicMapper.toDto(adventureCharacteristic);

        restAdventureCharacteristicMockMvc.perform(post("/api/adventure-characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureCharacteristicDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureCharacteristic> adventureCharacteristicList = adventureCharacteristicRepository.findAll();
        assertThat(adventureCharacteristicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWisdomIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureCharacteristicRepository.findAll().size();
        // set the field null
        adventureCharacteristic.setWisdom(null);

        // Create the AdventureCharacteristic, which fails.
        AdventureCharacteristicDTO adventureCharacteristicDTO = adventureCharacteristicMapper.toDto(adventureCharacteristic);

        restAdventureCharacteristicMockMvc.perform(post("/api/adventure-characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureCharacteristicDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureCharacteristic> adventureCharacteristicList = adventureCharacteristicRepository.findAll();
        assertThat(adventureCharacteristicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCharismaIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureCharacteristicRepository.findAll().size();
        // set the field null
        adventureCharacteristic.setCharisma(null);

        // Create the AdventureCharacteristic, which fails.
        AdventureCharacteristicDTO adventureCharacteristicDTO = adventureCharacteristicMapper.toDto(adventureCharacteristic);

        restAdventureCharacteristicMockMvc.perform(post("/api/adventure-characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureCharacteristicDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureCharacteristic> adventureCharacteristicList = adventureCharacteristicRepository.findAll();
        assertThat(adventureCharacteristicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdventureCharacteristics() throws Exception {
        // Initialize the database
        adventureCharacteristicRepository.saveAndFlush(adventureCharacteristic);

        // Get all the adventureCharacteristicList
        restAdventureCharacteristicMockMvc.perform(get("/api/adventure-characteristics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adventureCharacteristic.getId().intValue())))
            .andExpect(jsonPath("$.[*].strength").value(hasItem(DEFAULT_STRENGTH)))
            .andExpect(jsonPath("$.[*].agility").value(hasItem(DEFAULT_AGILITY)))
            .andExpect(jsonPath("$.[*].vitality").value(hasItem(DEFAULT_VITALITY)))
            .andExpect(jsonPath("$.[*].luck").value(hasItem(DEFAULT_LUCK)))
            .andExpect(jsonPath("$.[*].intelligence").value(hasItem(DEFAULT_INTELLIGENCE)))
            .andExpect(jsonPath("$.[*].wisdom").value(hasItem(DEFAULT_WISDOM)))
            .andExpect(jsonPath("$.[*].charisma").value(hasItem(DEFAULT_CHARISMA)));
    }
    
    @Test
    @Transactional
    public void getAdventureCharacteristic() throws Exception {
        // Initialize the database
        adventureCharacteristicRepository.saveAndFlush(adventureCharacteristic);

        // Get the adventureCharacteristic
        restAdventureCharacteristicMockMvc.perform(get("/api/adventure-characteristics/{id}", adventureCharacteristic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adventureCharacteristic.getId().intValue()))
            .andExpect(jsonPath("$.strength").value(DEFAULT_STRENGTH))
            .andExpect(jsonPath("$.agility").value(DEFAULT_AGILITY))
            .andExpect(jsonPath("$.vitality").value(DEFAULT_VITALITY))
            .andExpect(jsonPath("$.luck").value(DEFAULT_LUCK))
            .andExpect(jsonPath("$.intelligence").value(DEFAULT_INTELLIGENCE))
            .andExpect(jsonPath("$.wisdom").value(DEFAULT_WISDOM))
            .andExpect(jsonPath("$.charisma").value(DEFAULT_CHARISMA));
    }

    @Test
    @Transactional
    public void getNonExistingAdventureCharacteristic() throws Exception {
        // Get the adventureCharacteristic
        restAdventureCharacteristicMockMvc.perform(get("/api/adventure-characteristics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdventureCharacteristic() throws Exception {
        // Initialize the database
        adventureCharacteristicRepository.saveAndFlush(adventureCharacteristic);

        int databaseSizeBeforeUpdate = adventureCharacteristicRepository.findAll().size();

        // Update the adventureCharacteristic
        AdventureCharacteristic updatedAdventureCharacteristic = adventureCharacteristicRepository.findById(adventureCharacteristic.getId()).get();
        // Disconnect from session so that the updates on updatedAdventureCharacteristic are not directly saved in db
        em.detach(updatedAdventureCharacteristic);
        updatedAdventureCharacteristic
            .strength(UPDATED_STRENGTH)
            .agility(UPDATED_AGILITY)
            .vitality(UPDATED_VITALITY)
            .luck(UPDATED_LUCK)
            .intelligence(UPDATED_INTELLIGENCE)
            .wisdom(UPDATED_WISDOM)
            .charisma(UPDATED_CHARISMA);
        AdventureCharacteristicDTO adventureCharacteristicDTO = adventureCharacteristicMapper.toDto(updatedAdventureCharacteristic);

        restAdventureCharacteristicMockMvc.perform(put("/api/adventure-characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureCharacteristicDTO)))
            .andExpect(status().isOk());

        // Validate the AdventureCharacteristic in the database
        List<AdventureCharacteristic> adventureCharacteristicList = adventureCharacteristicRepository.findAll();
        assertThat(adventureCharacteristicList).hasSize(databaseSizeBeforeUpdate);
        AdventureCharacteristic testAdventureCharacteristic = adventureCharacteristicList.get(adventureCharacteristicList.size() - 1);
        assertThat(testAdventureCharacteristic.getStrength()).isEqualTo(UPDATED_STRENGTH);
        assertThat(testAdventureCharacteristic.getAgility()).isEqualTo(UPDATED_AGILITY);
        assertThat(testAdventureCharacteristic.getVitality()).isEqualTo(UPDATED_VITALITY);
        assertThat(testAdventureCharacteristic.getLuck()).isEqualTo(UPDATED_LUCK);
        assertThat(testAdventureCharacteristic.getIntelligence()).isEqualTo(UPDATED_INTELLIGENCE);
        assertThat(testAdventureCharacteristic.getWisdom()).isEqualTo(UPDATED_WISDOM);
        assertThat(testAdventureCharacteristic.getCharisma()).isEqualTo(UPDATED_CHARISMA);

        // Validate the AdventureCharacteristic in Elasticsearch
        verify(mockAdventureCharacteristicSearchRepository, times(1)).save(testAdventureCharacteristic);
    }

    @Test
    @Transactional
    public void updateNonExistingAdventureCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = adventureCharacteristicRepository.findAll().size();

        // Create the AdventureCharacteristic
        AdventureCharacteristicDTO adventureCharacteristicDTO = adventureCharacteristicMapper.toDto(adventureCharacteristic);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdventureCharacteristicMockMvc.perform(put("/api/adventure-characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureCharacteristicDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdventureCharacteristic in the database
        List<AdventureCharacteristic> adventureCharacteristicList = adventureCharacteristicRepository.findAll();
        assertThat(adventureCharacteristicList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AdventureCharacteristic in Elasticsearch
        verify(mockAdventureCharacteristicSearchRepository, times(0)).save(adventureCharacteristic);
    }

    @Test
    @Transactional
    public void deleteAdventureCharacteristic() throws Exception {
        // Initialize the database
        adventureCharacteristicRepository.saveAndFlush(adventureCharacteristic);

        int databaseSizeBeforeDelete = adventureCharacteristicRepository.findAll().size();

        // Delete the adventureCharacteristic
        restAdventureCharacteristicMockMvc.perform(delete("/api/adventure-characteristics/{id}", adventureCharacteristic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<AdventureCharacteristic> adventureCharacteristicList = adventureCharacteristicRepository.findAll();
        assertThat(adventureCharacteristicList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AdventureCharacteristic in Elasticsearch
        verify(mockAdventureCharacteristicSearchRepository, times(1)).deleteById(adventureCharacteristic.getId());
    }

    @Test
    @Transactional
    public void searchAdventureCharacteristic() throws Exception {
        // Initialize the database
        adventureCharacteristicRepository.saveAndFlush(adventureCharacteristic);
        when(mockAdventureCharacteristicSearchRepository.search(queryStringQuery("id:" + adventureCharacteristic.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(adventureCharacteristic), PageRequest.of(0, 1), 1));
        // Search the adventureCharacteristic
        restAdventureCharacteristicMockMvc.perform(get("/api/_search/adventure-characteristics?query=id:" + adventureCharacteristic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adventureCharacteristic.getId().intValue())))
            .andExpect(jsonPath("$.[*].strength").value(hasItem(DEFAULT_STRENGTH)))
            .andExpect(jsonPath("$.[*].agility").value(hasItem(DEFAULT_AGILITY)))
            .andExpect(jsonPath("$.[*].vitality").value(hasItem(DEFAULT_VITALITY)))
            .andExpect(jsonPath("$.[*].luck").value(hasItem(DEFAULT_LUCK)))
            .andExpect(jsonPath("$.[*].intelligence").value(hasItem(DEFAULT_INTELLIGENCE)))
            .andExpect(jsonPath("$.[*].wisdom").value(hasItem(DEFAULT_WISDOM)))
            .andExpect(jsonPath("$.[*].charisma").value(hasItem(DEFAULT_CHARISMA)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdventureCharacteristic.class);
        AdventureCharacteristic adventureCharacteristic1 = new AdventureCharacteristic();
        adventureCharacteristic1.setId(1L);
        AdventureCharacteristic adventureCharacteristic2 = new AdventureCharacteristic();
        adventureCharacteristic2.setId(adventureCharacteristic1.getId());
        assertThat(adventureCharacteristic1).isEqualTo(adventureCharacteristic2);
        adventureCharacteristic2.setId(2L);
        assertThat(adventureCharacteristic1).isNotEqualTo(adventureCharacteristic2);
        adventureCharacteristic1.setId(null);
        assertThat(adventureCharacteristic1).isNotEqualTo(adventureCharacteristic2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdventureCharacteristicDTO.class);
        AdventureCharacteristicDTO adventureCharacteristicDTO1 = new AdventureCharacteristicDTO();
        adventureCharacteristicDTO1.setId(1L);
        AdventureCharacteristicDTO adventureCharacteristicDTO2 = new AdventureCharacteristicDTO();
        assertThat(adventureCharacteristicDTO1).isNotEqualTo(adventureCharacteristicDTO2);
        adventureCharacteristicDTO2.setId(adventureCharacteristicDTO1.getId());
        assertThat(adventureCharacteristicDTO1).isEqualTo(adventureCharacteristicDTO2);
        adventureCharacteristicDTO2.setId(2L);
        assertThat(adventureCharacteristicDTO1).isNotEqualTo(adventureCharacteristicDTO2);
        adventureCharacteristicDTO1.setId(null);
        assertThat(adventureCharacteristicDTO1).isNotEqualTo(adventureCharacteristicDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(adventureCharacteristicMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(adventureCharacteristicMapper.fromId(null)).isNull();
    }
}
