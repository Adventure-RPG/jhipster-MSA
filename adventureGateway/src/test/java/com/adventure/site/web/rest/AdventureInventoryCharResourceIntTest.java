package com.adventure.site.web.rest;

import com.adventure.site.AdventureGatewayApp;

import com.adventure.site.config.SecurityBeanOverrideConfiguration;

import com.adventure.site.domain.AdventureInventoryChar;
import com.adventure.site.repository.AdventureInventoryCharRepository;
import com.adventure.site.repository.search.AdventureInventoryCharSearchRepository;
import com.adventure.site.service.AdventureInventoryCharService;
import com.adventure.site.service.dto.AdventureInventoryCharDTO;
import com.adventure.site.service.mapper.AdventureInventoryCharMapper;
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
 * Test class for the AdventureInventoryCharResource REST controller.
 *
 * @see AdventureInventoryCharResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, AdventureGatewayApp.class})
public class AdventureInventoryCharResourceIntTest {

    @Autowired
    private AdventureInventoryCharRepository adventureInventoryCharRepository;

    @Autowired
    private AdventureInventoryCharMapper adventureInventoryCharMapper;

    @Autowired
    private AdventureInventoryCharService adventureInventoryCharService;

    /**
     * This repository is mocked in the com.adventure.site.repository.search test package.
     *
     * @see com.adventure.site.repository.search.AdventureInventoryCharSearchRepositoryMockConfiguration
     */
    @Autowired
    private AdventureInventoryCharSearchRepository mockAdventureInventoryCharSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restAdventureInventoryCharMockMvc;

    private AdventureInventoryChar adventureInventoryChar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdventureInventoryCharResource adventureInventoryCharResource = new AdventureInventoryCharResource(adventureInventoryCharService);
        this.restAdventureInventoryCharMockMvc = MockMvcBuilders.standaloneSetup(adventureInventoryCharResource)
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
    public static AdventureInventoryChar createEntity() {
        AdventureInventoryChar adventureInventoryChar = new AdventureInventoryChar();
        return adventureInventoryChar;
    }

    @Before
    public void initTest() {
        adventureInventoryChar = createEntity();
    }

    @Test
    public void createAdventureInventoryChar() throws Exception {
        int databaseSizeBeforeCreate = adventureInventoryCharRepository.findAll().size();

        // Create the AdventureInventoryChar
        AdventureInventoryCharDTO adventureInventoryCharDTO = adventureInventoryCharMapper.toDto(adventureInventoryChar);
        restAdventureInventoryCharMockMvc.perform(post("/api/adventure-inventory-chars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureInventoryCharDTO)))
            .andExpect(status().isCreated());

        // Validate the AdventureInventoryChar in the database
        List<AdventureInventoryChar> adventureInventoryCharList = adventureInventoryCharRepository.findAll();
        assertThat(adventureInventoryCharList).hasSize(databaseSizeBeforeCreate + 1);
        AdventureInventoryChar testAdventureInventoryChar = adventureInventoryCharList.get(adventureInventoryCharList.size() - 1);

        // Validate the AdventureInventoryChar in Elasticsearch
        verify(mockAdventureInventoryCharSearchRepository, times(1)).save(testAdventureInventoryChar);
    }

    @Test
    public void createAdventureInventoryCharWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adventureInventoryCharRepository.findAll().size();

        // Create the AdventureInventoryChar with an existing ID
        adventureInventoryChar.setId();
        AdventureInventoryCharDTO adventureInventoryCharDTO = adventureInventoryCharMapper.toDto(adventureInventoryChar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdventureInventoryCharMockMvc.perform(post("/api/adventure-inventory-chars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureInventoryCharDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdventureInventoryChar in the database
        List<AdventureInventoryChar> adventureInventoryCharList = adventureInventoryCharRepository.findAll();
        assertThat(adventureInventoryCharList).hasSize(databaseSizeBeforeCreate);

        // Validate the AdventureInventoryChar in Elasticsearch
        verify(mockAdventureInventoryCharSearchRepository, times(0)).save(adventureInventoryChar);
    }

    @Test
    public void getAllAdventureInventoryChars() throws Exception {
        // Initialize the database
        adventureInventoryCharRepository.save(adventureInventoryChar);

        // Get all the adventureInventoryCharList
        restAdventureInventoryCharMockMvc.perform(get("/api/adventure-inventory-chars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }
    
    @Test
    public void getAdventureInventoryChar() throws Exception {
        // Initialize the database
        adventureInventoryCharRepository.save(adventureInventoryChar);

        // Get the adventureInventoryChar
        restAdventureInventoryCharMockMvc.perform(get("/api/adventure-inventory-chars/{id}", adventureInventoryChar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void getNonExistingAdventureInventoryChar() throws Exception {
        // Get the adventureInventoryChar
        restAdventureInventoryCharMockMvc.perform(get("/api/adventure-inventory-chars/{id}", ))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAdventureInventoryChar() throws Exception {
        // Initialize the database
        adventureInventoryCharRepository.save(adventureInventoryChar);

        int databaseSizeBeforeUpdate = adventureInventoryCharRepository.findAll().size();

        // Update the adventureInventoryChar
        AdventureInventoryChar updatedAdventureInventoryChar = adventureInventoryCharRepository.findById(adventureInventoryChar.getId()).get();
        AdventureInventoryCharDTO adventureInventoryCharDTO = adventureInventoryCharMapper.toDto(updatedAdventureInventoryChar);

        restAdventureInventoryCharMockMvc.perform(put("/api/adventure-inventory-chars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureInventoryCharDTO)))
            .andExpect(status().isOk());

        // Validate the AdventureInventoryChar in the database
        List<AdventureInventoryChar> adventureInventoryCharList = adventureInventoryCharRepository.findAll();
        assertThat(adventureInventoryCharList).hasSize(databaseSizeBeforeUpdate);
        AdventureInventoryChar testAdventureInventoryChar = adventureInventoryCharList.get(adventureInventoryCharList.size() - 1);

        // Validate the AdventureInventoryChar in Elasticsearch
        verify(mockAdventureInventoryCharSearchRepository, times(1)).save(testAdventureInventoryChar);
    }

    @Test
    public void updateNonExistingAdventureInventoryChar() throws Exception {
        int databaseSizeBeforeUpdate = adventureInventoryCharRepository.findAll().size();

        // Create the AdventureInventoryChar
        AdventureInventoryCharDTO adventureInventoryCharDTO = adventureInventoryCharMapper.toDto(adventureInventoryChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdventureInventoryCharMockMvc.perform(put("/api/adventure-inventory-chars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureInventoryCharDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdventureInventoryChar in the database
        List<AdventureInventoryChar> adventureInventoryCharList = adventureInventoryCharRepository.findAll();
        assertThat(adventureInventoryCharList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AdventureInventoryChar in Elasticsearch
        verify(mockAdventureInventoryCharSearchRepository, times(0)).save(adventureInventoryChar);
    }

    @Test
    public void deleteAdventureInventoryChar() throws Exception {
        // Initialize the database
        adventureInventoryCharRepository.save(adventureInventoryChar);

        int databaseSizeBeforeDelete = adventureInventoryCharRepository.findAll().size();

        // Get the adventureInventoryChar
        restAdventureInventoryCharMockMvc.perform(delete("/api/adventure-inventory-chars/{id}", adventureInventoryChar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AdventureInventoryChar> adventureInventoryCharList = adventureInventoryCharRepository.findAll();
        assertThat(adventureInventoryCharList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AdventureInventoryChar in Elasticsearch
        verify(mockAdventureInventoryCharSearchRepository, times(1)).deleteById(adventureInventoryChar.getId());
    }

    @Test
    public void searchAdventureInventoryChar() throws Exception {
        // Initialize the database
        adventureInventoryCharRepository.save(adventureInventoryChar);
        when(mockAdventureInventoryCharSearchRepository.search(queryStringQuery("id:" + adventureInventoryChar.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(adventureInventoryChar), PageRequest.of(0, 1), 1));
        // Search the adventureInventoryChar
        restAdventureInventoryCharMockMvc.perform(get("/api/_search/adventure-inventory-chars?query=id:" + adventureInventoryChar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdventureInventoryChar.class);
        AdventureInventoryChar adventureInventoryChar1 = new AdventureInventoryChar();
        adventureInventoryChar1.setId();
        AdventureInventoryChar adventureInventoryChar2 = new AdventureInventoryChar();
        adventureInventoryChar2.setId(adventureInventoryChar1.getId());
        assertThat(adventureInventoryChar1).isEqualTo(adventureInventoryChar2);
        adventureInventoryChar2.setId();
        assertThat(adventureInventoryChar1).isNotEqualTo(adventureInventoryChar2);
        adventureInventoryChar1.setId(null);
        assertThat(adventureInventoryChar1).isNotEqualTo(adventureInventoryChar2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdventureInventoryCharDTO.class);
        AdventureInventoryCharDTO adventureInventoryCharDTO1 = new AdventureInventoryCharDTO();
        adventureInventoryCharDTO1.setId();
        AdventureInventoryCharDTO adventureInventoryCharDTO2 = new AdventureInventoryCharDTO();
        assertThat(adventureInventoryCharDTO1).isNotEqualTo(adventureInventoryCharDTO2);
        adventureInventoryCharDTO2.setId(adventureInventoryCharDTO1.getId());
        assertThat(adventureInventoryCharDTO1).isEqualTo(adventureInventoryCharDTO2);
        adventureInventoryCharDTO2.setId();
        assertThat(adventureInventoryCharDTO1).isNotEqualTo(adventureInventoryCharDTO2);
        adventureInventoryCharDTO1.setId(null);
        assertThat(adventureInventoryCharDTO1).isNotEqualTo(adventureInventoryCharDTO2);
    }
}
