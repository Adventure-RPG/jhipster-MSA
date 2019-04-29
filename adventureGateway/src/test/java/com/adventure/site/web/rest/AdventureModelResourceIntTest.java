package com.adventure.site.web.rest;

import com.adventure.site.AdventureGatewayApp;

import com.adventure.site.config.SecurityBeanOverrideConfiguration;

import com.adventure.site.domain.AdventureModel;
import com.adventure.site.repository.AdventureModelRepository;
import com.adventure.site.repository.search.AdventureModelSearchRepository;
import com.adventure.site.service.AdventureModelService;
import com.adventure.site.service.dto.AdventureModelDTO;
import com.adventure.site.service.mapper.AdventureModelMapper;
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
 * Test class for the AdventureModelResource REST controller.
 *
 * @see AdventureModelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, AdventureGatewayApp.class})
public class AdventureModelResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private AdventureModelRepository adventureModelRepository;

    @Autowired
    private AdventureModelMapper adventureModelMapper;

    @Autowired
    private AdventureModelService adventureModelService;

    /**
     * This repository is mocked in the com.adventure.site.repository.search test package.
     *
     * @see com.adventure.site.repository.search.AdventureModelSearchRepositoryMockConfiguration
     */
    @Autowired
    private AdventureModelSearchRepository mockAdventureModelSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restAdventureModelMockMvc;

    private AdventureModel adventureModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdventureModelResource adventureModelResource = new AdventureModelResource(adventureModelService);
        this.restAdventureModelMockMvc = MockMvcBuilders.standaloneSetup(adventureModelResource)
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
    public static AdventureModel createEntity() {
        AdventureModel adventureModel = new AdventureModel()
            .name(DEFAULT_NAME)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE);
        return adventureModel;
    }

    @Before
    public void initTest() {
        adventureModelRepository.deleteAll();
        adventureModel = createEntity();
    }

    @Test
    public void createAdventureModel() throws Exception {
        int databaseSizeBeforeCreate = adventureModelRepository.findAll().size();

        // Create the AdventureModel
        AdventureModelDTO adventureModelDTO = adventureModelMapper.toDto(adventureModel);
        restAdventureModelMockMvc.perform(post("/api/adventure-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureModelDTO)))
            .andExpect(status().isCreated());

        // Validate the AdventureModel in the database
        List<AdventureModel> adventureModelList = adventureModelRepository.findAll();
        assertThat(adventureModelList).hasSize(databaseSizeBeforeCreate + 1);
        AdventureModel testAdventureModel = adventureModelList.get(adventureModelList.size() - 1);
        assertThat(testAdventureModel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdventureModel.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testAdventureModel.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);

        // Validate the AdventureModel in Elasticsearch
        verify(mockAdventureModelSearchRepository, times(1)).save(testAdventureModel);
    }

    @Test
    public void createAdventureModelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adventureModelRepository.findAll().size();

        // Create the AdventureModel with an existing ID
        adventureModel.setId("existing_id");
        AdventureModelDTO adventureModelDTO = adventureModelMapper.toDto(adventureModel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdventureModelMockMvc.perform(post("/api/adventure-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureModelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdventureModel in the database
        List<AdventureModel> adventureModelList = adventureModelRepository.findAll();
        assertThat(adventureModelList).hasSize(databaseSizeBeforeCreate);

        // Validate the AdventureModel in Elasticsearch
        verify(mockAdventureModelSearchRepository, times(0)).save(adventureModel);
    }

    @Test
    public void getAllAdventureModels() throws Exception {
        // Initialize the database
        adventureModelRepository.save(adventureModel);

        // Get all the adventureModelList
        restAdventureModelMockMvc.perform(get("/api/adventure-models?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adventureModel.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }
    
    @Test
    public void getAdventureModel() throws Exception {
        // Initialize the database
        adventureModelRepository.save(adventureModel);

        // Get the adventureModel
        restAdventureModelMockMvc.perform(get("/api/adventure-models/{id}", adventureModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adventureModel.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)));
    }

    @Test
    public void getNonExistingAdventureModel() throws Exception {
        // Get the adventureModel
        restAdventureModelMockMvc.perform(get("/api/adventure-models/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAdventureModel() throws Exception {
        // Initialize the database
        adventureModelRepository.save(adventureModel);

        int databaseSizeBeforeUpdate = adventureModelRepository.findAll().size();

        // Update the adventureModel
        AdventureModel updatedAdventureModel = adventureModelRepository.findById(adventureModel.getId()).get();
        updatedAdventureModel
            .name(UPDATED_NAME)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        AdventureModelDTO adventureModelDTO = adventureModelMapper.toDto(updatedAdventureModel);

        restAdventureModelMockMvc.perform(put("/api/adventure-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureModelDTO)))
            .andExpect(status().isOk());

        // Validate the AdventureModel in the database
        List<AdventureModel> adventureModelList = adventureModelRepository.findAll();
        assertThat(adventureModelList).hasSize(databaseSizeBeforeUpdate);
        AdventureModel testAdventureModel = adventureModelList.get(adventureModelList.size() - 1);
        assertThat(testAdventureModel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdventureModel.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testAdventureModel.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);

        // Validate the AdventureModel in Elasticsearch
        verify(mockAdventureModelSearchRepository, times(1)).save(testAdventureModel);
    }

    @Test
    public void updateNonExistingAdventureModel() throws Exception {
        int databaseSizeBeforeUpdate = adventureModelRepository.findAll().size();

        // Create the AdventureModel
        AdventureModelDTO adventureModelDTO = adventureModelMapper.toDto(adventureModel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdventureModelMockMvc.perform(put("/api/adventure-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureModelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdventureModel in the database
        List<AdventureModel> adventureModelList = adventureModelRepository.findAll();
        assertThat(adventureModelList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AdventureModel in Elasticsearch
        verify(mockAdventureModelSearchRepository, times(0)).save(adventureModel);
    }

    @Test
    public void deleteAdventureModel() throws Exception {
        // Initialize the database
        adventureModelRepository.save(adventureModel);

        int databaseSizeBeforeDelete = adventureModelRepository.findAll().size();

        // Delete the adventureModel
        restAdventureModelMockMvc.perform(delete("/api/adventure-models/{id}", adventureModel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AdventureModel> adventureModelList = adventureModelRepository.findAll();
        assertThat(adventureModelList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AdventureModel in Elasticsearch
        verify(mockAdventureModelSearchRepository, times(1)).deleteById(adventureModel.getId());
    }

    @Test
    public void searchAdventureModel() throws Exception {
        // Initialize the database
        adventureModelRepository.save(adventureModel);
        when(mockAdventureModelSearchRepository.search(queryStringQuery("id:" + adventureModel.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(adventureModel), PageRequest.of(0, 1), 1));
        // Search the adventureModel
        restAdventureModelMockMvc.perform(get("/api/_search/adventure-models?query=id:" + adventureModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adventureModel.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdventureModel.class);
        AdventureModel adventureModel1 = new AdventureModel();
        adventureModel1.setId("id1");
        AdventureModel adventureModel2 = new AdventureModel();
        adventureModel2.setId(adventureModel1.getId());
        assertThat(adventureModel1).isEqualTo(adventureModel2);
        adventureModel2.setId("id2");
        assertThat(adventureModel1).isNotEqualTo(adventureModel2);
        adventureModel1.setId(null);
        assertThat(adventureModel1).isNotEqualTo(adventureModel2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdventureModelDTO.class);
        AdventureModelDTO adventureModelDTO1 = new AdventureModelDTO();
        adventureModelDTO1.setId("id1");
        AdventureModelDTO adventureModelDTO2 = new AdventureModelDTO();
        assertThat(adventureModelDTO1).isNotEqualTo(adventureModelDTO2);
        adventureModelDTO2.setId(adventureModelDTO1.getId());
        assertThat(adventureModelDTO1).isEqualTo(adventureModelDTO2);
        adventureModelDTO2.setId("id2");
        assertThat(adventureModelDTO1).isNotEqualTo(adventureModelDTO2);
        adventureModelDTO1.setId(null);
        assertThat(adventureModelDTO1).isNotEqualTo(adventureModelDTO2);
    }
}
