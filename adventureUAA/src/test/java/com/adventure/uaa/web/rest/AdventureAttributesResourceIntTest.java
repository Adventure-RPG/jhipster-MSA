package com.adventure.uaa.web.rest;

import com.adventure.uaa.AdventureUaaApp;

import com.adventure.uaa.config.SecurityBeanOverrideConfiguration;

import com.adventure.uaa.domain.AdventureAttributes;
import com.adventure.uaa.repository.AdventureAttributesRepository;
import com.adventure.uaa.repository.search.AdventureAttributesSearchRepository;
import com.adventure.uaa.service.AdventureAttributesService;
import com.adventure.uaa.service.dto.AdventureAttributesDTO;
import com.adventure.uaa.service.mapper.AdventureAttributesMapper;
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

import com.adventure.uaa.domain.enumeration.AdventureDefenceArmorType;
import com.adventure.uaa.domain.enumeration.AdventureActiveWeaponAttackDamage;
/**
 * Test class for the AdventureAttributesResource REST controller.
 *
 * @see AdventureAttributesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdventureUaaApp.class)
public class AdventureAttributesResourceIntTest {

    private static final Long DEFAULT_DEFENCE = 1L;
    private static final Long UPDATED_DEFENCE = 2L;

    private static final AdventureDefenceArmorType DEFAULT_DEFENCE_ARMOR_TYPE = AdventureDefenceArmorType.BASIC;
    private static final AdventureDefenceArmorType UPDATED_DEFENCE_ARMOR_TYPE = AdventureDefenceArmorType.BASIC;

    private static final Long DEFAULT_FIRE_RESISTANCE = 1L;
    private static final Long UPDATED_FIRE_RESISTANCE = 2L;

    private static final Long DEFAULT_EARTH_RESISTANCE = 1L;
    private static final Long UPDATED_EARTH_RESISTANCE = 2L;

    private static final Long DEFAULT_WATER_RESISTANCE = 1L;
    private static final Long UPDATED_WATER_RESISTANCE = 2L;

    private static final Long DEFAULT_WIND_RESISTANCE = 1L;
    private static final Long UPDATED_WIND_RESISTANCE = 2L;

    private static final AdventureActiveWeaponAttackDamage DEFAULT_ACTIVE_WEAPON_ATTACK_DAMAGE = AdventureActiveWeaponAttackDamage.BLUNT;
    private static final AdventureActiveWeaponAttackDamage UPDATED_ACTIVE_WEAPON_ATTACK_DAMAGE = AdventureActiveWeaponAttackDamage.BLUNT;

    private static final Long DEFAULT_ACTIVE_WEAPON_ATTACK_HIT = 1L;
    private static final Long UPDATED_ACTIVE_WEAPON_ATTACK_HIT = 2L;

    private static final Long DEFAULT_ACTIVE_WEAPON_ATTACK_TYPE = 1L;
    private static final Long UPDATED_ACTIVE_WEAPON_ATTACK_TYPE = 2L;

    private static final Long DEFAULT_SIZE = 1L;
    private static final Long UPDATED_SIZE = 2L;

    @Autowired
    private AdventureAttributesRepository adventureAttributesRepository;

    @Autowired
    private AdventureAttributesMapper adventureAttributesMapper;

    @Autowired
    private AdventureAttributesService adventureAttributesService;

    /**
     * This repository is mocked in the com.adventure.uaa.repository.search test package.
     *
     * @see com.adventure.uaa.repository.search.AdventureAttributesSearchRepositoryMockConfiguration
     */
    @Autowired
    private AdventureAttributesSearchRepository mockAdventureAttributesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdventureAttributesMockMvc;

    private AdventureAttributes adventureAttributes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdventureAttributesResource adventureAttributesResource = new AdventureAttributesResource(adventureAttributesService);
        this.restAdventureAttributesMockMvc = MockMvcBuilders.standaloneSetup(adventureAttributesResource)
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
    public static AdventureAttributes createEntity(EntityManager em) {
        AdventureAttributes adventureAttributes = new AdventureAttributes()
            .defence(DEFAULT_DEFENCE)
            .defenceArmorType(DEFAULT_DEFENCE_ARMOR_TYPE)
            .fireResistance(DEFAULT_FIRE_RESISTANCE)
            .earthResistance(DEFAULT_EARTH_RESISTANCE)
            .waterResistance(DEFAULT_WATER_RESISTANCE)
            .windResistance(DEFAULT_WIND_RESISTANCE)
            .activeWeaponAttackDamage(DEFAULT_ACTIVE_WEAPON_ATTACK_DAMAGE)
            .activeWeaponAttackHit(DEFAULT_ACTIVE_WEAPON_ATTACK_HIT)
            .activeWeaponAttackType(DEFAULT_ACTIVE_WEAPON_ATTACK_TYPE)
            .size(DEFAULT_SIZE);
        return adventureAttributes;
    }

    @Before
    public void initTest() {
        adventureAttributes = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdventureAttributes() throws Exception {
        int databaseSizeBeforeCreate = adventureAttributesRepository.findAll().size();

        // Create the AdventureAttributes
        AdventureAttributesDTO adventureAttributesDTO = adventureAttributesMapper.toDto(adventureAttributes);
        restAdventureAttributesMockMvc.perform(post("/api/adventure-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureAttributesDTO)))
            .andExpect(status().isCreated());

        // Validate the AdventureAttributes in the database
        List<AdventureAttributes> adventureAttributesList = adventureAttributesRepository.findAll();
        assertThat(adventureAttributesList).hasSize(databaseSizeBeforeCreate + 1);
        AdventureAttributes testAdventureAttributes = adventureAttributesList.get(adventureAttributesList.size() - 1);
        assertThat(testAdventureAttributes.getDefence()).isEqualTo(DEFAULT_DEFENCE);
        assertThat(testAdventureAttributes.getDefenceArmorType()).isEqualTo(DEFAULT_DEFENCE_ARMOR_TYPE);
        assertThat(testAdventureAttributes.getFireResistance()).isEqualTo(DEFAULT_FIRE_RESISTANCE);
        assertThat(testAdventureAttributes.getEarthResistance()).isEqualTo(DEFAULT_EARTH_RESISTANCE);
        assertThat(testAdventureAttributes.getWaterResistance()).isEqualTo(DEFAULT_WATER_RESISTANCE);
        assertThat(testAdventureAttributes.getWindResistance()).isEqualTo(DEFAULT_WIND_RESISTANCE);
        assertThat(testAdventureAttributes.getActiveWeaponAttackDamage()).isEqualTo(DEFAULT_ACTIVE_WEAPON_ATTACK_DAMAGE);
        assertThat(testAdventureAttributes.getActiveWeaponAttackHit()).isEqualTo(DEFAULT_ACTIVE_WEAPON_ATTACK_HIT);
        assertThat(testAdventureAttributes.getActiveWeaponAttackType()).isEqualTo(DEFAULT_ACTIVE_WEAPON_ATTACK_TYPE);
        assertThat(testAdventureAttributes.getSize()).isEqualTo(DEFAULT_SIZE);

        // Validate the AdventureAttributes in Elasticsearch
        verify(mockAdventureAttributesSearchRepository, times(1)).save(testAdventureAttributes);
    }

    @Test
    @Transactional
    public void createAdventureAttributesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adventureAttributesRepository.findAll().size();

        // Create the AdventureAttributes with an existing ID
        adventureAttributes.setId(1L);
        AdventureAttributesDTO adventureAttributesDTO = adventureAttributesMapper.toDto(adventureAttributes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdventureAttributesMockMvc.perform(post("/api/adventure-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureAttributesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdventureAttributes in the database
        List<AdventureAttributes> adventureAttributesList = adventureAttributesRepository.findAll();
        assertThat(adventureAttributesList).hasSize(databaseSizeBeforeCreate);

        // Validate the AdventureAttributes in Elasticsearch
        verify(mockAdventureAttributesSearchRepository, times(0)).save(adventureAttributes);
    }

    @Test
    @Transactional
    public void checkDefenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureAttributesRepository.findAll().size();
        // set the field null
        adventureAttributes.setDefence(null);

        // Create the AdventureAttributes, which fails.
        AdventureAttributesDTO adventureAttributesDTO = adventureAttributesMapper.toDto(adventureAttributes);

        restAdventureAttributesMockMvc.perform(post("/api/adventure-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureAttributesDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureAttributes> adventureAttributesList = adventureAttributesRepository.findAll();
        assertThat(adventureAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDefenceArmorTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureAttributesRepository.findAll().size();
        // set the field null
        adventureAttributes.setDefenceArmorType(null);

        // Create the AdventureAttributes, which fails.
        AdventureAttributesDTO adventureAttributesDTO = adventureAttributesMapper.toDto(adventureAttributes);

        restAdventureAttributesMockMvc.perform(post("/api/adventure-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureAttributesDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureAttributes> adventureAttributesList = adventureAttributesRepository.findAll();
        assertThat(adventureAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFireResistanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureAttributesRepository.findAll().size();
        // set the field null
        adventureAttributes.setFireResistance(null);

        // Create the AdventureAttributes, which fails.
        AdventureAttributesDTO adventureAttributesDTO = adventureAttributesMapper.toDto(adventureAttributes);

        restAdventureAttributesMockMvc.perform(post("/api/adventure-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureAttributesDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureAttributes> adventureAttributesList = adventureAttributesRepository.findAll();
        assertThat(adventureAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEarthResistanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureAttributesRepository.findAll().size();
        // set the field null
        adventureAttributes.setEarthResistance(null);

        // Create the AdventureAttributes, which fails.
        AdventureAttributesDTO adventureAttributesDTO = adventureAttributesMapper.toDto(adventureAttributes);

        restAdventureAttributesMockMvc.perform(post("/api/adventure-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureAttributesDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureAttributes> adventureAttributesList = adventureAttributesRepository.findAll();
        assertThat(adventureAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWaterResistanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureAttributesRepository.findAll().size();
        // set the field null
        adventureAttributes.setWaterResistance(null);

        // Create the AdventureAttributes, which fails.
        AdventureAttributesDTO adventureAttributesDTO = adventureAttributesMapper.toDto(adventureAttributes);

        restAdventureAttributesMockMvc.perform(post("/api/adventure-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureAttributesDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureAttributes> adventureAttributesList = adventureAttributesRepository.findAll();
        assertThat(adventureAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWindResistanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureAttributesRepository.findAll().size();
        // set the field null
        adventureAttributes.setWindResistance(null);

        // Create the AdventureAttributes, which fails.
        AdventureAttributesDTO adventureAttributesDTO = adventureAttributesMapper.toDto(adventureAttributes);

        restAdventureAttributesMockMvc.perform(post("/api/adventure-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureAttributesDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureAttributes> adventureAttributesList = adventureAttributesRepository.findAll();
        assertThat(adventureAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveWeaponAttackDamageIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureAttributesRepository.findAll().size();
        // set the field null
        adventureAttributes.setActiveWeaponAttackDamage(null);

        // Create the AdventureAttributes, which fails.
        AdventureAttributesDTO adventureAttributesDTO = adventureAttributesMapper.toDto(adventureAttributes);

        restAdventureAttributesMockMvc.perform(post("/api/adventure-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureAttributesDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureAttributes> adventureAttributesList = adventureAttributesRepository.findAll();
        assertThat(adventureAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveWeaponAttackHitIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureAttributesRepository.findAll().size();
        // set the field null
        adventureAttributes.setActiveWeaponAttackHit(null);

        // Create the AdventureAttributes, which fails.
        AdventureAttributesDTO adventureAttributesDTO = adventureAttributesMapper.toDto(adventureAttributes);

        restAdventureAttributesMockMvc.perform(post("/api/adventure-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureAttributesDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureAttributes> adventureAttributesList = adventureAttributesRepository.findAll();
        assertThat(adventureAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveWeaponAttackTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureAttributesRepository.findAll().size();
        // set the field null
        adventureAttributes.setActiveWeaponAttackType(null);

        // Create the AdventureAttributes, which fails.
        AdventureAttributesDTO adventureAttributesDTO = adventureAttributesMapper.toDto(adventureAttributes);

        restAdventureAttributesMockMvc.perform(post("/api/adventure-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureAttributesDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureAttributes> adventureAttributesList = adventureAttributesRepository.findAll();
        assertThat(adventureAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureAttributesRepository.findAll().size();
        // set the field null
        adventureAttributes.setSize(null);

        // Create the AdventureAttributes, which fails.
        AdventureAttributesDTO adventureAttributesDTO = adventureAttributesMapper.toDto(adventureAttributes);

        restAdventureAttributesMockMvc.perform(post("/api/adventure-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureAttributesDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureAttributes> adventureAttributesList = adventureAttributesRepository.findAll();
        assertThat(adventureAttributesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdventureAttributes() throws Exception {
        // Initialize the database
        adventureAttributesRepository.saveAndFlush(adventureAttributes);

        // Get all the adventureAttributesList
        restAdventureAttributesMockMvc.perform(get("/api/adventure-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adventureAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].defence").value(hasItem(DEFAULT_DEFENCE.intValue())))
            .andExpect(jsonPath("$.[*].defenceArmorType").value(hasItem(DEFAULT_DEFENCE_ARMOR_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fireResistance").value(hasItem(DEFAULT_FIRE_RESISTANCE.intValue())))
            .andExpect(jsonPath("$.[*].earthResistance").value(hasItem(DEFAULT_EARTH_RESISTANCE.intValue())))
            .andExpect(jsonPath("$.[*].waterResistance").value(hasItem(DEFAULT_WATER_RESISTANCE.intValue())))
            .andExpect(jsonPath("$.[*].windResistance").value(hasItem(DEFAULT_WIND_RESISTANCE.intValue())))
            .andExpect(jsonPath("$.[*].activeWeaponAttackDamage").value(hasItem(DEFAULT_ACTIVE_WEAPON_ATTACK_DAMAGE.toString())))
            .andExpect(jsonPath("$.[*].activeWeaponAttackHit").value(hasItem(DEFAULT_ACTIVE_WEAPON_ATTACK_HIT.intValue())))
            .andExpect(jsonPath("$.[*].activeWeaponAttackType").value(hasItem(DEFAULT_ACTIVE_WEAPON_ATTACK_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.intValue())));
    }
    
    @Test
    @Transactional
    public void getAdventureAttributes() throws Exception {
        // Initialize the database
        adventureAttributesRepository.saveAndFlush(adventureAttributes);

        // Get the adventureAttributes
        restAdventureAttributesMockMvc.perform(get("/api/adventure-attributes/{id}", adventureAttributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adventureAttributes.getId().intValue()))
            .andExpect(jsonPath("$.defence").value(DEFAULT_DEFENCE.intValue()))
            .andExpect(jsonPath("$.defenceArmorType").value(DEFAULT_DEFENCE_ARMOR_TYPE.toString()))
            .andExpect(jsonPath("$.fireResistance").value(DEFAULT_FIRE_RESISTANCE.intValue()))
            .andExpect(jsonPath("$.earthResistance").value(DEFAULT_EARTH_RESISTANCE.intValue()))
            .andExpect(jsonPath("$.waterResistance").value(DEFAULT_WATER_RESISTANCE.intValue()))
            .andExpect(jsonPath("$.windResistance").value(DEFAULT_WIND_RESISTANCE.intValue()))
            .andExpect(jsonPath("$.activeWeaponAttackDamage").value(DEFAULT_ACTIVE_WEAPON_ATTACK_DAMAGE.toString()))
            .andExpect(jsonPath("$.activeWeaponAttackHit").value(DEFAULT_ACTIVE_WEAPON_ATTACK_HIT.intValue()))
            .andExpect(jsonPath("$.activeWeaponAttackType").value(DEFAULT_ACTIVE_WEAPON_ATTACK_TYPE.intValue()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAdventureAttributes() throws Exception {
        // Get the adventureAttributes
        restAdventureAttributesMockMvc.perform(get("/api/adventure-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdventureAttributes() throws Exception {
        // Initialize the database
        adventureAttributesRepository.saveAndFlush(adventureAttributes);

        int databaseSizeBeforeUpdate = adventureAttributesRepository.findAll().size();

        // Update the adventureAttributes
        AdventureAttributes updatedAdventureAttributes = adventureAttributesRepository.findById(adventureAttributes.getId()).get();
        // Disconnect from session so that the updates on updatedAdventureAttributes are not directly saved in db
        em.detach(updatedAdventureAttributes);
        updatedAdventureAttributes
            .defence(UPDATED_DEFENCE)
            .defenceArmorType(UPDATED_DEFENCE_ARMOR_TYPE)
            .fireResistance(UPDATED_FIRE_RESISTANCE)
            .earthResistance(UPDATED_EARTH_RESISTANCE)
            .waterResistance(UPDATED_WATER_RESISTANCE)
            .windResistance(UPDATED_WIND_RESISTANCE)
            .activeWeaponAttackDamage(UPDATED_ACTIVE_WEAPON_ATTACK_DAMAGE)
            .activeWeaponAttackHit(UPDATED_ACTIVE_WEAPON_ATTACK_HIT)
            .activeWeaponAttackType(UPDATED_ACTIVE_WEAPON_ATTACK_TYPE)
            .size(UPDATED_SIZE);
        AdventureAttributesDTO adventureAttributesDTO = adventureAttributesMapper.toDto(updatedAdventureAttributes);

        restAdventureAttributesMockMvc.perform(put("/api/adventure-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureAttributesDTO)))
            .andExpect(status().isOk());

        // Validate the AdventureAttributes in the database
        List<AdventureAttributes> adventureAttributesList = adventureAttributesRepository.findAll();
        assertThat(adventureAttributesList).hasSize(databaseSizeBeforeUpdate);
        AdventureAttributes testAdventureAttributes = adventureAttributesList.get(adventureAttributesList.size() - 1);
        assertThat(testAdventureAttributes.getDefence()).isEqualTo(UPDATED_DEFENCE);
        assertThat(testAdventureAttributes.getDefenceArmorType()).isEqualTo(UPDATED_DEFENCE_ARMOR_TYPE);
        assertThat(testAdventureAttributes.getFireResistance()).isEqualTo(UPDATED_FIRE_RESISTANCE);
        assertThat(testAdventureAttributes.getEarthResistance()).isEqualTo(UPDATED_EARTH_RESISTANCE);
        assertThat(testAdventureAttributes.getWaterResistance()).isEqualTo(UPDATED_WATER_RESISTANCE);
        assertThat(testAdventureAttributes.getWindResistance()).isEqualTo(UPDATED_WIND_RESISTANCE);
        assertThat(testAdventureAttributes.getActiveWeaponAttackDamage()).isEqualTo(UPDATED_ACTIVE_WEAPON_ATTACK_DAMAGE);
        assertThat(testAdventureAttributes.getActiveWeaponAttackHit()).isEqualTo(UPDATED_ACTIVE_WEAPON_ATTACK_HIT);
        assertThat(testAdventureAttributes.getActiveWeaponAttackType()).isEqualTo(UPDATED_ACTIVE_WEAPON_ATTACK_TYPE);
        assertThat(testAdventureAttributes.getSize()).isEqualTo(UPDATED_SIZE);

        // Validate the AdventureAttributes in Elasticsearch
        verify(mockAdventureAttributesSearchRepository, times(1)).save(testAdventureAttributes);
    }

    @Test
    @Transactional
    public void updateNonExistingAdventureAttributes() throws Exception {
        int databaseSizeBeforeUpdate = adventureAttributesRepository.findAll().size();

        // Create the AdventureAttributes
        AdventureAttributesDTO adventureAttributesDTO = adventureAttributesMapper.toDto(adventureAttributes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdventureAttributesMockMvc.perform(put("/api/adventure-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureAttributesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdventureAttributes in the database
        List<AdventureAttributes> adventureAttributesList = adventureAttributesRepository.findAll();
        assertThat(adventureAttributesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AdventureAttributes in Elasticsearch
        verify(mockAdventureAttributesSearchRepository, times(0)).save(adventureAttributes);
    }

    @Test
    @Transactional
    public void deleteAdventureAttributes() throws Exception {
        // Initialize the database
        adventureAttributesRepository.saveAndFlush(adventureAttributes);

        int databaseSizeBeforeDelete = adventureAttributesRepository.findAll().size();

        // Get the adventureAttributes
        restAdventureAttributesMockMvc.perform(delete("/api/adventure-attributes/{id}", adventureAttributes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AdventureAttributes> adventureAttributesList = adventureAttributesRepository.findAll();
        assertThat(adventureAttributesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AdventureAttributes in Elasticsearch
        verify(mockAdventureAttributesSearchRepository, times(1)).deleteById(adventureAttributes.getId());
    }

    @Test
    @Transactional
    public void searchAdventureAttributes() throws Exception {
        // Initialize the database
        adventureAttributesRepository.saveAndFlush(adventureAttributes);
        when(mockAdventureAttributesSearchRepository.search(queryStringQuery("id:" + adventureAttributes.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(adventureAttributes), PageRequest.of(0, 1), 1));
        // Search the adventureAttributes
        restAdventureAttributesMockMvc.perform(get("/api/_search/adventure-attributes?query=id:" + adventureAttributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adventureAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].defence").value(hasItem(DEFAULT_DEFENCE.intValue())))
            .andExpect(jsonPath("$.[*].defenceArmorType").value(hasItem(DEFAULT_DEFENCE_ARMOR_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fireResistance").value(hasItem(DEFAULT_FIRE_RESISTANCE.intValue())))
            .andExpect(jsonPath("$.[*].earthResistance").value(hasItem(DEFAULT_EARTH_RESISTANCE.intValue())))
            .andExpect(jsonPath("$.[*].waterResistance").value(hasItem(DEFAULT_WATER_RESISTANCE.intValue())))
            .andExpect(jsonPath("$.[*].windResistance").value(hasItem(DEFAULT_WIND_RESISTANCE.intValue())))
            .andExpect(jsonPath("$.[*].activeWeaponAttackDamage").value(hasItem(DEFAULT_ACTIVE_WEAPON_ATTACK_DAMAGE.toString())))
            .andExpect(jsonPath("$.[*].activeWeaponAttackHit").value(hasItem(DEFAULT_ACTIVE_WEAPON_ATTACK_HIT.intValue())))
            .andExpect(jsonPath("$.[*].activeWeaponAttackType").value(hasItem(DEFAULT_ACTIVE_WEAPON_ATTACK_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdventureAttributes.class);
        AdventureAttributes adventureAttributes1 = new AdventureAttributes();
        adventureAttributes1.setId(1L);
        AdventureAttributes adventureAttributes2 = new AdventureAttributes();
        adventureAttributes2.setId(adventureAttributes1.getId());
        assertThat(adventureAttributes1).isEqualTo(adventureAttributes2);
        adventureAttributes2.setId(2L);
        assertThat(adventureAttributes1).isNotEqualTo(adventureAttributes2);
        adventureAttributes1.setId(null);
        assertThat(adventureAttributes1).isNotEqualTo(adventureAttributes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdventureAttributesDTO.class);
        AdventureAttributesDTO adventureAttributesDTO1 = new AdventureAttributesDTO();
        adventureAttributesDTO1.setId(1L);
        AdventureAttributesDTO adventureAttributesDTO2 = new AdventureAttributesDTO();
        assertThat(adventureAttributesDTO1).isNotEqualTo(adventureAttributesDTO2);
        adventureAttributesDTO2.setId(adventureAttributesDTO1.getId());
        assertThat(adventureAttributesDTO1).isEqualTo(adventureAttributesDTO2);
        adventureAttributesDTO2.setId(2L);
        assertThat(adventureAttributesDTO1).isNotEqualTo(adventureAttributesDTO2);
        adventureAttributesDTO1.setId(null);
        assertThat(adventureAttributesDTO1).isNotEqualTo(adventureAttributesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(adventureAttributesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(adventureAttributesMapper.fromId(null)).isNull();
    }
}
