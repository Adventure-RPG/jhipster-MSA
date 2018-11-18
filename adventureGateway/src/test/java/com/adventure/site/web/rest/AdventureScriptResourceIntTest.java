package com.adventure.site.web.rest;

import com.adventure.site.AdventureGatewayApp;

import com.adventure.site.config.SecurityBeanOverrideConfiguration;

import com.adventure.site.domain.AdventureScript;
import com.adventure.site.repository.AdventureScriptRepository;
import com.adventure.site.repository.search.AdventureScriptSearchRepository;
import com.adventure.site.service.AdventureScriptService;
import com.adventure.site.service.dto.AdventureScriptDTO;
import com.adventure.site.service.mapper.AdventureScriptMapper;
import com.adventure.site.web.rest.errors.ExceptionTranslator;

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
import org.springframework.util.Base64Utils;

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
 * Test class for the AdventureScriptResource REST controller.
 *
 * @see AdventureScriptResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, AdventureGatewayApp.class})
public class AdventureScriptResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ARGUMENTS_SCRIPT = "AAAAAAAAAA";
    private static final String UPDATED_ARGUMENTS_SCRIPT = "BBBBBBBBBB";

    @Autowired
    private AdventureScriptRepository adventureScriptRepository;

    @Autowired
    private AdventureScriptMapper adventureScriptMapper;

    @Autowired
    private AdventureScriptService adventureScriptService;

    /**
     * This repository is mocked in the com.adventure.site.repository.search test package.
     *
     * @see com.adventure.site.repository.search.AdventureScriptSearchRepositoryMockConfiguration
     */
    @Autowired
    private AdventureScriptSearchRepository mockAdventureScriptSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restAdventureScriptMockMvc;

    private AdventureScript adventureScript;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdventureScriptResource adventureScriptResource = new AdventureScriptResource(adventureScriptService);
        this.restAdventureScriptMockMvc = MockMvcBuilders.standaloneSetup(adventureScriptResource)
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
    public static AdventureScript createEntity() {
        AdventureScript adventureScript = new AdventureScript()
            .name(DEFAULT_NAME)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE)
            .argumentsScript(DEFAULT_ARGUMENTS_SCRIPT);
        return adventureScript;
    }

    @Before
    public void initTest() {
        adventureScript = createEntity();
    }

    @Test
    public void createAdventureScript() throws Exception {
        int databaseSizeBeforeCreate = adventureScriptRepository.findAll().size();

        // Create the AdventureScript
        AdventureScriptDTO adventureScriptDTO = adventureScriptMapper.toDto(adventureScript);
        restAdventureScriptMockMvc.perform(post("/api/adventure-scripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureScriptDTO)))
            .andExpect(status().isCreated());

        // Validate the AdventureScript in the database
        List<AdventureScript> adventureScriptList = adventureScriptRepository.findAll();
        assertThat(adventureScriptList).hasSize(databaseSizeBeforeCreate + 1);
        AdventureScript testAdventureScript = adventureScriptList.get(adventureScriptList.size() - 1);
        assertThat(testAdventureScript.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdventureScript.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testAdventureScript.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
        assertThat(testAdventureScript.getArgumentsScript()).isEqualTo(DEFAULT_ARGUMENTS_SCRIPT);

        // Validate the AdventureScript in Elasticsearch
        verify(mockAdventureScriptSearchRepository, times(1)).save(testAdventureScript);
    }

    @Test
    public void createAdventureScriptWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adventureScriptRepository.findAll().size();

        // Create the AdventureScript with an existing ID
        adventureScript.setId();
        AdventureScriptDTO adventureScriptDTO = adventureScriptMapper.toDto(adventureScript);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdventureScriptMockMvc.perform(post("/api/adventure-scripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureScriptDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdventureScript in the database
        List<AdventureScript> adventureScriptList = adventureScriptRepository.findAll();
        assertThat(adventureScriptList).hasSize(databaseSizeBeforeCreate);

        // Validate the AdventureScript in Elasticsearch
        verify(mockAdventureScriptSearchRepository, times(0)).save(adventureScript);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adventureScriptRepository.findAll().size();
        // set the field null
        adventureScript.setName(null);

        // Create the AdventureScript, which fails.
        AdventureScriptDTO adventureScriptDTO = adventureScriptMapper.toDto(adventureScript);

        restAdventureScriptMockMvc.perform(post("/api/adventure-scripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureScriptDTO)))
            .andExpect(status().isBadRequest());

        List<AdventureScript> adventureScriptList = adventureScriptRepository.findAll();
        assertThat(adventureScriptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAdventureScripts() throws Exception {
        // Initialize the database
        adventureScriptRepository.save(adventureScript);

        // Get all the adventureScriptList
        restAdventureScriptMockMvc.perform(get("/api/adventure-scripts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].argumentsScript").value(hasItem(DEFAULT_ARGUMENTS_SCRIPT.toString())));
    }
    
    @Test
    public void getAdventureScript() throws Exception {
        // Initialize the database
        adventureScriptRepository.save(adventureScript);

        // Get the adventureScript
        restAdventureScriptMockMvc.perform(get("/api/adventure-scripts/{id}", adventureScript.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)))
            .andExpect(jsonPath("$.argumentsScript").value(DEFAULT_ARGUMENTS_SCRIPT.toString()));
    }

    @Test
    public void getNonExistingAdventureScript() throws Exception {
        // Get the adventureScript
        restAdventureScriptMockMvc.perform(get("/api/adventure-scripts/{id}", ))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAdventureScript() throws Exception {
        // Initialize the database
        adventureScriptRepository.save(adventureScript);

        int databaseSizeBeforeUpdate = adventureScriptRepository.findAll().size();

        // Update the adventureScript
        AdventureScript updatedAdventureScript = adventureScriptRepository.findById(adventureScript.getId()).get();
        updatedAdventureScript
            .name(UPDATED_NAME)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .argumentsScript(UPDATED_ARGUMENTS_SCRIPT);
        AdventureScriptDTO adventureScriptDTO = adventureScriptMapper.toDto(updatedAdventureScript);

        restAdventureScriptMockMvc.perform(put("/api/adventure-scripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureScriptDTO)))
            .andExpect(status().isOk());

        // Validate the AdventureScript in the database
        List<AdventureScript> adventureScriptList = adventureScriptRepository.findAll();
        assertThat(adventureScriptList).hasSize(databaseSizeBeforeUpdate);
        AdventureScript testAdventureScript = adventureScriptList.get(adventureScriptList.size() - 1);
        assertThat(testAdventureScript.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdventureScript.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testAdventureScript.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testAdventureScript.getArgumentsScript()).isEqualTo(UPDATED_ARGUMENTS_SCRIPT);

        // Validate the AdventureScript in Elasticsearch
        verify(mockAdventureScriptSearchRepository, times(1)).save(testAdventureScript);
    }

    @Test
    public void updateNonExistingAdventureScript() throws Exception {
        int databaseSizeBeforeUpdate = adventureScriptRepository.findAll().size();

        // Create the AdventureScript
        AdventureScriptDTO adventureScriptDTO = adventureScriptMapper.toDto(adventureScript);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdventureScriptMockMvc.perform(put("/api/adventure-scripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureScriptDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdventureScript in the database
        List<AdventureScript> adventureScriptList = adventureScriptRepository.findAll();
        assertThat(adventureScriptList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AdventureScript in Elasticsearch
        verify(mockAdventureScriptSearchRepository, times(0)).save(adventureScript);
    }

    @Test
    public void deleteAdventureScript() throws Exception {
        // Initialize the database
        adventureScriptRepository.save(adventureScript);

        int databaseSizeBeforeDelete = adventureScriptRepository.findAll().size();

        // Get the adventureScript
        restAdventureScriptMockMvc.perform(delete("/api/adventure-scripts/{id}", adventureScript.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AdventureScript> adventureScriptList = adventureScriptRepository.findAll();
        assertThat(adventureScriptList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AdventureScript in Elasticsearch
        verify(mockAdventureScriptSearchRepository, times(1)).deleteById(adventureScript.getId());
    }

    @Test
    public void searchAdventureScript() throws Exception {
        // Initialize the database
        adventureScriptRepository.save(adventureScript);
        when(mockAdventureScriptSearchRepository.search(queryStringQuery("id:" + adventureScript.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(adventureScript), PageRequest.of(0, 1), 1));
        // Search the adventureScript
        restAdventureScriptMockMvc.perform(get("/api/_search/adventure-scripts?query=id:" + adventureScript.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].argumentsScript").value(hasItem(DEFAULT_ARGUMENTS_SCRIPT)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdventureScript.class);
        AdventureScript adventureScript1 = new AdventureScript();
        adventureScript1.setId();
        AdventureScript adventureScript2 = new AdventureScript();
        adventureScript2.setId(adventureScript1.getId());
        assertThat(adventureScript1).isEqualTo(adventureScript2);
        adventureScript2.setId();
        assertThat(adventureScript1).isNotEqualTo(adventureScript2);
        adventureScript1.setId(null);
        assertThat(adventureScript1).isNotEqualTo(adventureScript2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdventureScriptDTO.class);
        AdventureScriptDTO adventureScriptDTO1 = new AdventureScriptDTO();
        adventureScriptDTO1.setId();
        AdventureScriptDTO adventureScriptDTO2 = new AdventureScriptDTO();
        assertThat(adventureScriptDTO1).isNotEqualTo(adventureScriptDTO2);
        adventureScriptDTO2.setId(adventureScriptDTO1.getId());
        assertThat(adventureScriptDTO1).isEqualTo(adventureScriptDTO2);
        adventureScriptDTO2.setId();
        assertThat(adventureScriptDTO1).isNotEqualTo(adventureScriptDTO2);
        adventureScriptDTO1.setId(null);
        assertThat(adventureScriptDTO1).isNotEqualTo(adventureScriptDTO2);
    }
}
