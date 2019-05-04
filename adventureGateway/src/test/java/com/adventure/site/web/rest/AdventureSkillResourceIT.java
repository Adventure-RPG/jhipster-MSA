package com.adventure.site.web.rest;

import com.adventure.site.AdventureGatewayApp;
import com.adventure.site.config.SecurityBeanOverrideConfiguration;
import com.adventure.site.domain.AdventureSkill;
import com.adventure.site.repository.AdventureSkillRepository;
import com.adventure.site.repository.search.AdventureSkillSearchRepository;
import com.adventure.site.service.AdventureSkillService;
import com.adventure.site.service.dto.AdventureSkillDTO;
import com.adventure.site.service.mapper.AdventureSkillMapper;
import com.adventure.site.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;


import java.util.Collections;
import java.util.List;

import static com.adventure.site.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link AdventureSkillResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, AdventureGatewayApp.class})
public class AdventureSkillResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_POSITION = false;
    private static final Boolean UPDATED_POSITION = true;

    @Autowired
    private AdventureSkillRepository adventureSkillRepository;

    @Autowired
    private AdventureSkillMapper adventureSkillMapper;

    @Autowired
    private AdventureSkillService adventureSkillService;

    /**
     * This repository is mocked in the com.adventure.site.repository.search test package.
     *
     * @see com.adventure.site.repository.search.AdventureSkillSearchRepositoryMockConfiguration
     */
    @Autowired
    private AdventureSkillSearchRepository mockAdventureSkillSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restAdventureSkillMockMvc;

    private AdventureSkill adventureSkill;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdventureSkillResource adventureSkillResource = new AdventureSkillResource(adventureSkillService);
        this.restAdventureSkillMockMvc = MockMvcBuilders.standaloneSetup(adventureSkillResource)
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
    public static AdventureSkill createEntity() {
        AdventureSkill adventureSkill = new AdventureSkill()
            .name(DEFAULT_NAME)
            .position(DEFAULT_POSITION);
        return adventureSkill;
    }

    @BeforeEach
    public void initTest() {
        adventureSkillRepository.deleteAll();
        adventureSkill = createEntity();
    }

    @Test
    public void createAdventureSkill() throws Exception {
        int databaseSizeBeforeCreate = adventureSkillRepository.findAll().size();

        // Create the AdventureSkill
        AdventureSkillDTO adventureSkillDTO = adventureSkillMapper.toDto(adventureSkill);
        restAdventureSkillMockMvc.perform(post("/api/adventure-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureSkillDTO)))
            .andExpect(status().isCreated());

        // Validate the AdventureSkill in the database
        List<AdventureSkill> adventureSkillList = adventureSkillRepository.findAll();
        assertThat(adventureSkillList).hasSize(databaseSizeBeforeCreate + 1);
        AdventureSkill testAdventureSkill = adventureSkillList.get(adventureSkillList.size() - 1);
        assertThat(testAdventureSkill.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdventureSkill.isPosition()).isEqualTo(DEFAULT_POSITION);

        // Validate the AdventureSkill in Elasticsearch
        verify(mockAdventureSkillSearchRepository, times(1)).save(testAdventureSkill);
    }

    @Test
    public void createAdventureSkillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adventureSkillRepository.findAll().size();

        // Create the AdventureSkill with an existing ID
        adventureSkill.setId("existing_id");
        AdventureSkillDTO adventureSkillDTO = adventureSkillMapper.toDto(adventureSkill);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdventureSkillMockMvc.perform(post("/api/adventure-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureSkillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdventureSkill in the database
        List<AdventureSkill> adventureSkillList = adventureSkillRepository.findAll();
        assertThat(adventureSkillList).hasSize(databaseSizeBeforeCreate);

        // Validate the AdventureSkill in Elasticsearch
        verify(mockAdventureSkillSearchRepository, times(0)).save(adventureSkill);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureSkillRepository.findAll().size();
        // set the field null
        adventureSkill.setName(null);

        // Create the AdventureSkill, which fails.
        AdventureSkillDTO adventureSkillDTO = adventureSkillMapper.toDto(adventureSkill);

        restAdventureSkillMockMvc.perform(post("/api/adventure-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureSkillDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureSkill> adventureSkillList = adventureSkillRepository.findAll();
        assertThat(adventureSkillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAdventureSkills() throws Exception {
        // Initialize the database
        adventureSkillRepository.save(adventureSkill);

        // Get all the adventureSkillList
        restAdventureSkillMockMvc.perform(get("/api/adventure-skills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adventureSkill.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.booleanValue())));
    }
    
    @Test
    public void getAdventureSkill() throws Exception {
        // Initialize the database
        adventureSkillRepository.save(adventureSkill);

        // Get the adventureSkill
        restAdventureSkillMockMvc.perform(get("/api/adventure-skills/{id}", adventureSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adventureSkill.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.booleanValue()));
    }

    @Test
    public void getNonExistingAdventureSkill() throws Exception {
        // Get the adventureSkill
        restAdventureSkillMockMvc.perform(get("/api/adventure-skills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAdventureSkill() throws Exception {
        // Initialize the database
        adventureSkillRepository.save(adventureSkill);

        int databaseSizeBeforeUpdate = adventureSkillRepository.findAll().size();

        // Update the adventureSkill
        AdventureSkill updatedAdventureSkill = adventureSkillRepository.findById(adventureSkill.getId()).get();
        updatedAdventureSkill
            .name(UPDATED_NAME)
            .position(UPDATED_POSITION);
        AdventureSkillDTO adventureSkillDTO = adventureSkillMapper.toDto(updatedAdventureSkill);

        restAdventureSkillMockMvc.perform(put("/api/adventure-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureSkillDTO)))
            .andExpect(status().isOk());

        // Validate the AdventureSkill in the database
        List<AdventureSkill> adventureSkillList = adventureSkillRepository.findAll();
        assertThat(adventureSkillList).hasSize(databaseSizeBeforeUpdate);
        AdventureSkill testAdventureSkill = adventureSkillList.get(adventureSkillList.size() - 1);
        assertThat(testAdventureSkill.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdventureSkill.isPosition()).isEqualTo(UPDATED_POSITION);

        // Validate the AdventureSkill in Elasticsearch
        verify(mockAdventureSkillSearchRepository, times(1)).save(testAdventureSkill);
    }

    @Test
    public void updateNonExistingAdventureSkill() throws Exception {
        int databaseSizeBeforeUpdate = adventureSkillRepository.findAll().size();

        // Create the AdventureSkill
        AdventureSkillDTO adventureSkillDTO = adventureSkillMapper.toDto(adventureSkill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdventureSkillMockMvc.perform(put("/api/adventure-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureSkillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdventureSkill in the database
        List<AdventureSkill> adventureSkillList = adventureSkillRepository.findAll();
        assertThat(adventureSkillList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AdventureSkill in Elasticsearch
        verify(mockAdventureSkillSearchRepository, times(0)).save(adventureSkill);
    }

    @Test
    public void deleteAdventureSkill() throws Exception {
        // Initialize the database
        adventureSkillRepository.save(adventureSkill);

        int databaseSizeBeforeDelete = adventureSkillRepository.findAll().size();

        // Delete the adventureSkill
        restAdventureSkillMockMvc.perform(delete("/api/adventure-skills/{id}", adventureSkill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<AdventureSkill> adventureSkillList = adventureSkillRepository.findAll();
        assertThat(adventureSkillList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AdventureSkill in Elasticsearch
        verify(mockAdventureSkillSearchRepository, times(1)).deleteById(adventureSkill.getId());
    }

    @Test
    public void searchAdventureSkill() throws Exception {
        // Initialize the database
        adventureSkillRepository.save(adventureSkill);
        when(mockAdventureSkillSearchRepository.search(queryStringQuery("id:" + adventureSkill.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(adventureSkill), PageRequest.of(0, 1), 1));
        // Search the adventureSkill
        restAdventureSkillMockMvc.perform(get("/api/_search/adventure-skills?query=id:" + adventureSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adventureSkill.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.booleanValue())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdventureSkill.class);
        AdventureSkill adventureSkill1 = new AdventureSkill();
        adventureSkill1.setId("id1");
        AdventureSkill adventureSkill2 = new AdventureSkill();
        adventureSkill2.setId(adventureSkill1.getId());
        assertThat(adventureSkill1).isEqualTo(adventureSkill2);
        adventureSkill2.setId("id2");
        assertThat(adventureSkill1).isNotEqualTo(adventureSkill2);
        adventureSkill1.setId(null);
        assertThat(adventureSkill1).isNotEqualTo(adventureSkill2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdventureSkillDTO.class);
        AdventureSkillDTO adventureSkillDTO1 = new AdventureSkillDTO();
        adventureSkillDTO1.setId("id1");
        AdventureSkillDTO adventureSkillDTO2 = new AdventureSkillDTO();
        assertThat(adventureSkillDTO1).isNotEqualTo(adventureSkillDTO2);
        adventureSkillDTO2.setId(adventureSkillDTO1.getId());
        assertThat(adventureSkillDTO1).isEqualTo(adventureSkillDTO2);
        adventureSkillDTO2.setId("id2");
        assertThat(adventureSkillDTO1).isNotEqualTo(adventureSkillDTO2);
        adventureSkillDTO1.setId(null);
        assertThat(adventureSkillDTO1).isNotEqualTo(adventureSkillDTO2);
    }
}
