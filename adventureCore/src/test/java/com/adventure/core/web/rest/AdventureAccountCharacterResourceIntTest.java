package com.adventure.core.web.rest;

import com.adventure.core.AdventureCoreApp;

import com.adventure.core.config.SecurityBeanOverrideConfiguration;

import com.adventure.core.domain.AdventureAccountCharacter;
import com.adventure.core.repository.AdventureAccountCharacterRepository;
import com.adventure.core.repository.search.AdventureAccountCharacterSearchRepository;
import com.adventure.core.service.AdventureAccountCharacterService;
import com.adventure.core.service.dto.AdventureAccountCharacterDTO;
import com.adventure.core.service.mapper.AdventureAccountCharacterMapper;
import com.adventure.core.web.rest.errors.ExceptionTranslator;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static com.adventure.core.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AdventureAccountCharacterResource REST controller.
 *
 * @see AdventureAccountCharacterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, AdventureCoreApp.class})
public class AdventureAccountCharacterResourceIntTest {

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final String DEFAULT_NICKNAME = "AAAAAAAAAA";
    private static final String UPDATED_NICKNAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GENDER = false;
    private static final Boolean UPDATED_GENDER = true;

    @Autowired
    private AdventureAccountCharacterRepository adventureAccountCharacterRepository;

    @Mock
    private AdventureAccountCharacterRepository adventureAccountCharacterRepositoryMock;

    @Autowired
    private AdventureAccountCharacterMapper adventureAccountCharacterMapper;

    @Mock
    private AdventureAccountCharacterService adventureAccountCharacterServiceMock;

    @Autowired
    private AdventureAccountCharacterService adventureAccountCharacterService;

    /**
     * This repository is mocked in the com.adventure.core.repository.search test package.
     *
     * @see com.adventure.core.repository.search.AdventureAccountCharacterSearchRepositoryMockConfiguration
     */
    @Autowired
    private AdventureAccountCharacterSearchRepository mockAdventureAccountCharacterSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restAdventureAccountCharacterMockMvc;

    private AdventureAccountCharacter adventureAccountCharacter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdventureAccountCharacterResource adventureAccountCharacterResource = new AdventureAccountCharacterResource(adventureAccountCharacterService);
        this.restAdventureAccountCharacterMockMvc = MockMvcBuilders.standaloneSetup(adventureAccountCharacterResource)
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
    public static AdventureAccountCharacter createEntity() {
        AdventureAccountCharacter adventureAccountCharacter = new AdventureAccountCharacter()
            .userId(DEFAULT_USER_ID)
            .nickname(DEFAULT_NICKNAME)
            .gender(DEFAULT_GENDER);
        return adventureAccountCharacter;
    }

    @Before
    public void initTest() {
        adventureAccountCharacterRepository.deleteAll();
        adventureAccountCharacter = createEntity();
    }

    @Test
    public void createAdventureAccountCharacter() throws Exception {
        int databaseSizeBeforeCreate = adventureAccountCharacterRepository.findAll().size();

        // Create the AdventureAccountCharacter
        AdventureAccountCharacterDTO adventureAccountCharacterDTO = adventureAccountCharacterMapper.toDto(adventureAccountCharacter);
        restAdventureAccountCharacterMockMvc.perform(post("/api/adventure-account-characters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureAccountCharacterDTO)))
            .andExpect(status().isCreated());

        // Validate the AdventureAccountCharacter in the database
        List<AdventureAccountCharacter> adventureAccountCharacterList = adventureAccountCharacterRepository.findAll();
        assertThat(adventureAccountCharacterList).hasSize(databaseSizeBeforeCreate + 1);
        AdventureAccountCharacter testAdventureAccountCharacter = adventureAccountCharacterList.get(adventureAccountCharacterList.size() - 1);
        assertThat(testAdventureAccountCharacter.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testAdventureAccountCharacter.getNickname()).isEqualTo(DEFAULT_NICKNAME);
        assertThat(testAdventureAccountCharacter.isGender()).isEqualTo(DEFAULT_GENDER);

        // Validate the AdventureAccountCharacter in Elasticsearch
        verify(mockAdventureAccountCharacterSearchRepository, times(1)).save(testAdventureAccountCharacter);
    }

    @Test
    public void createAdventureAccountCharacterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adventureAccountCharacterRepository.findAll().size();

        // Create the AdventureAccountCharacter with an existing ID
        adventureAccountCharacter.setId("existing_id");
        AdventureAccountCharacterDTO adventureAccountCharacterDTO = adventureAccountCharacterMapper.toDto(adventureAccountCharacter);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdventureAccountCharacterMockMvc.perform(post("/api/adventure-account-characters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureAccountCharacterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdventureAccountCharacter in the database
        List<AdventureAccountCharacter> adventureAccountCharacterList = adventureAccountCharacterRepository.findAll();
        assertThat(adventureAccountCharacterList).hasSize(databaseSizeBeforeCreate);

        // Validate the AdventureAccountCharacter in Elasticsearch
        verify(mockAdventureAccountCharacterSearchRepository, times(0)).save(adventureAccountCharacter);
    }

    @Test
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureAccountCharacterRepository.findAll().size();
        // set the field null
        adventureAccountCharacter.setUserId(null);

        // Create the AdventureAccountCharacter, which fails.
        AdventureAccountCharacterDTO adventureAccountCharacterDTO = adventureAccountCharacterMapper.toDto(adventureAccountCharacter);

        restAdventureAccountCharacterMockMvc.perform(post("/api/adventure-account-characters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureAccountCharacterDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureAccountCharacter> adventureAccountCharacterList = adventureAccountCharacterRepository.findAll();
        assertThat(adventureAccountCharacterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNicknameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureAccountCharacterRepository.findAll().size();
        // set the field null
        adventureAccountCharacter.setNickname(null);

        // Create the AdventureAccountCharacter, which fails.
        AdventureAccountCharacterDTO adventureAccountCharacterDTO = adventureAccountCharacterMapper.toDto(adventureAccountCharacter);

        restAdventureAccountCharacterMockMvc.perform(post("/api/adventure-account-characters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureAccountCharacterDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureAccountCharacter> adventureAccountCharacterList = adventureAccountCharacterRepository.findAll();
        assertThat(adventureAccountCharacterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureAccountCharacterRepository.findAll().size();
        // set the field null
        adventureAccountCharacter.setGender(null);

        // Create the AdventureAccountCharacter, which fails.
        AdventureAccountCharacterDTO adventureAccountCharacterDTO = adventureAccountCharacterMapper.toDto(adventureAccountCharacter);

        restAdventureAccountCharacterMockMvc.perform(post("/api/adventure-account-characters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureAccountCharacterDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureAccountCharacter> adventureAccountCharacterList = adventureAccountCharacterRepository.findAll();
        assertThat(adventureAccountCharacterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAdventureAccountCharacters() throws Exception {
        // Initialize the database
        adventureAccountCharacterRepository.save(adventureAccountCharacter);

        // Get all the adventureAccountCharacterList
        restAdventureAccountCharacterMockMvc.perform(get("/api/adventure-account-characters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adventureAccountCharacter.getId())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].nickname").value(hasItem(DEFAULT_NICKNAME.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllAdventureAccountCharactersWithEagerRelationshipsIsEnabled() throws Exception {
        AdventureAccountCharacterResource adventureAccountCharacterResource = new AdventureAccountCharacterResource(adventureAccountCharacterServiceMock);
        when(adventureAccountCharacterServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAdventureAccountCharacterMockMvc = MockMvcBuilders.standaloneSetup(adventureAccountCharacterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAdventureAccountCharacterMockMvc.perform(get("/api/adventure-account-characters?eagerload=true"))
        .andExpect(status().isOk());

        verify(adventureAccountCharacterServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAdventureAccountCharactersWithEagerRelationshipsIsNotEnabled() throws Exception {
        AdventureAccountCharacterResource adventureAccountCharacterResource = new AdventureAccountCharacterResource(adventureAccountCharacterServiceMock);
            when(adventureAccountCharacterServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAdventureAccountCharacterMockMvc = MockMvcBuilders.standaloneSetup(adventureAccountCharacterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAdventureAccountCharacterMockMvc.perform(get("/api/adventure-account-characters?eagerload=true"))
        .andExpect(status().isOk());

            verify(adventureAccountCharacterServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    public void getAdventureAccountCharacter() throws Exception {
        // Initialize the database
        adventureAccountCharacterRepository.save(adventureAccountCharacter);

        // Get the adventureAccountCharacter
        restAdventureAccountCharacterMockMvc.perform(get("/api/adventure-account-characters/{id}", adventureAccountCharacter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adventureAccountCharacter.getId()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.nickname").value(DEFAULT_NICKNAME.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.booleanValue()));
    }

    @Test
    public void getNonExistingAdventureAccountCharacter() throws Exception {
        // Get the adventureAccountCharacter
        restAdventureAccountCharacterMockMvc.perform(get("/api/adventure-account-characters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAdventureAccountCharacter() throws Exception {
        // Initialize the database
        adventureAccountCharacterRepository.save(adventureAccountCharacter);

        int databaseSizeBeforeUpdate = adventureAccountCharacterRepository.findAll().size();

        // Update the adventureAccountCharacter
        AdventureAccountCharacter updatedAdventureAccountCharacter = adventureAccountCharacterRepository.findById(adventureAccountCharacter.getId()).get();
        updatedAdventureAccountCharacter
            .userId(UPDATED_USER_ID)
            .nickname(UPDATED_NICKNAME)
            .gender(UPDATED_GENDER);
        AdventureAccountCharacterDTO adventureAccountCharacterDTO = adventureAccountCharacterMapper.toDto(updatedAdventureAccountCharacter);

        restAdventureAccountCharacterMockMvc.perform(put("/api/adventure-account-characters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureAccountCharacterDTO)))
            .andExpect(status().isOk());

        // Validate the AdventureAccountCharacter in the database
        List<AdventureAccountCharacter> adventureAccountCharacterList = adventureAccountCharacterRepository.findAll();
        assertThat(adventureAccountCharacterList).hasSize(databaseSizeBeforeUpdate);
        AdventureAccountCharacter testAdventureAccountCharacter = adventureAccountCharacterList.get(adventureAccountCharacterList.size() - 1);
        assertThat(testAdventureAccountCharacter.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testAdventureAccountCharacter.getNickname()).isEqualTo(UPDATED_NICKNAME);
        assertThat(testAdventureAccountCharacter.isGender()).isEqualTo(UPDATED_GENDER);

        // Validate the AdventureAccountCharacter in Elasticsearch
        verify(mockAdventureAccountCharacterSearchRepository, times(1)).save(testAdventureAccountCharacter);
    }

    @Test
    public void updateNonExistingAdventureAccountCharacter() throws Exception {
        int databaseSizeBeforeUpdate = adventureAccountCharacterRepository.findAll().size();

        // Create the AdventureAccountCharacter
        AdventureAccountCharacterDTO adventureAccountCharacterDTO = adventureAccountCharacterMapper.toDto(adventureAccountCharacter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdventureAccountCharacterMockMvc.perform(put("/api/adventure-account-characters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureAccountCharacterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdventureAccountCharacter in the database
        List<AdventureAccountCharacter> adventureAccountCharacterList = adventureAccountCharacterRepository.findAll();
        assertThat(adventureAccountCharacterList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AdventureAccountCharacter in Elasticsearch
        verify(mockAdventureAccountCharacterSearchRepository, times(0)).save(adventureAccountCharacter);
    }

    @Test
    public void deleteAdventureAccountCharacter() throws Exception {
        // Initialize the database
        adventureAccountCharacterRepository.save(adventureAccountCharacter);

        int databaseSizeBeforeDelete = adventureAccountCharacterRepository.findAll().size();

        // Get the adventureAccountCharacter
        restAdventureAccountCharacterMockMvc.perform(delete("/api/adventure-account-characters/{id}", adventureAccountCharacter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AdventureAccountCharacter> adventureAccountCharacterList = adventureAccountCharacterRepository.findAll();
        assertThat(adventureAccountCharacterList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AdventureAccountCharacter in Elasticsearch
        verify(mockAdventureAccountCharacterSearchRepository, times(1)).deleteById(adventureAccountCharacter.getId());
    }

    @Test
    public void searchAdventureAccountCharacter() throws Exception {
        // Initialize the database
        adventureAccountCharacterRepository.save(adventureAccountCharacter);
        when(mockAdventureAccountCharacterSearchRepository.search(queryStringQuery("id:" + adventureAccountCharacter.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(adventureAccountCharacter), PageRequest.of(0, 1), 1));
        // Search the adventureAccountCharacter
        restAdventureAccountCharacterMockMvc.perform(get("/api/_search/adventure-account-characters?query=id:" + adventureAccountCharacter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adventureAccountCharacter.getId())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].nickname").value(hasItem(DEFAULT_NICKNAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.booleanValue())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdventureAccountCharacter.class);
        AdventureAccountCharacter adventureAccountCharacter1 = new AdventureAccountCharacter();
        adventureAccountCharacter1.setId("id1");
        AdventureAccountCharacter adventureAccountCharacter2 = new AdventureAccountCharacter();
        adventureAccountCharacter2.setId(adventureAccountCharacter1.getId());
        assertThat(adventureAccountCharacter1).isEqualTo(adventureAccountCharacter2);
        adventureAccountCharacter2.setId("id2");
        assertThat(adventureAccountCharacter1).isNotEqualTo(adventureAccountCharacter2);
        adventureAccountCharacter1.setId(null);
        assertThat(adventureAccountCharacter1).isNotEqualTo(adventureAccountCharacter2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdventureAccountCharacterDTO.class);
        AdventureAccountCharacterDTO adventureAccountCharacterDTO1 = new AdventureAccountCharacterDTO();
        adventureAccountCharacterDTO1.setId("id1");
        AdventureAccountCharacterDTO adventureAccountCharacterDTO2 = new AdventureAccountCharacterDTO();
        assertThat(adventureAccountCharacterDTO1).isNotEqualTo(adventureAccountCharacterDTO2);
        adventureAccountCharacterDTO2.setId(adventureAccountCharacterDTO1.getId());
        assertThat(adventureAccountCharacterDTO1).isEqualTo(adventureAccountCharacterDTO2);
        adventureAccountCharacterDTO2.setId("id2");
        assertThat(adventureAccountCharacterDTO1).isNotEqualTo(adventureAccountCharacterDTO2);
        adventureAccountCharacterDTO1.setId(null);
        assertThat(adventureAccountCharacterDTO1).isNotEqualTo(adventureAccountCharacterDTO2);
    }
}
