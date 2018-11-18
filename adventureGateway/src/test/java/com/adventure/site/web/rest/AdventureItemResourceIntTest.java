package com.adventure.site.web.rest;

import com.adventure.site.AdventureGatewayApp;

import com.adventure.site.config.SecurityBeanOverrideConfiguration;

import com.adventure.site.domain.AdventureItem;
import com.adventure.site.repository.AdventureItemRepository;
import com.adventure.site.repository.search.AdventureItemSearchRepository;
import com.adventure.site.service.AdventureItemService;
import com.adventure.site.service.dto.AdventureItemDTO;
import com.adventure.site.service.mapper.AdventureItemMapper;
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

import com.adventure.site.domain.enumeration.AdventureEquipmentSlot;
/**
 * Test class for the AdventureItemResource REST controller.
 *
 * @see AdventureItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, AdventureGatewayApp.class})
public class AdventureItemResourceIntTest {

    private static final Boolean DEFAULT_IS_EQUIPMENT = false;
    private static final Boolean UPDATED_IS_EQUIPMENT = true;

    private static final AdventureEquipmentSlot DEFAULT_EQUIPMENT_SLOT = AdventureEquipmentSlot.WEAPON1;
    private static final AdventureEquipmentSlot UPDATED_EQUIPMENT_SLOT = AdventureEquipmentSlot.WEAPON2;

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;

    private static final Long DEFAULT_WEIGHT = 1L;
    private static final Long UPDATED_WEIGHT = 2L;

    @Autowired
    private AdventureItemRepository adventureItemRepository;

    @Autowired
    private AdventureItemMapper adventureItemMapper;

    @Autowired
    private AdventureItemService adventureItemService;

    /**
     * This repository is mocked in the com.adventure.site.repository.search test package.
     *
     * @see com.adventure.site.repository.search.AdventureItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private AdventureItemSearchRepository mockAdventureItemSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restAdventureItemMockMvc;

    private AdventureItem adventureItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdventureItemResource adventureItemResource = new AdventureItemResource(adventureItemService);
        this.restAdventureItemMockMvc = MockMvcBuilders.standaloneSetup(adventureItemResource)
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
    public static AdventureItem createEntity() {
        AdventureItem adventureItem = new AdventureItem()
            .isEquipment(DEFAULT_IS_EQUIPMENT)
            .equipmentSlot(DEFAULT_EQUIPMENT_SLOT)
            .price(DEFAULT_PRICE)
            .weight(DEFAULT_WEIGHT);
        return adventureItem;
    }

    @Before
    public void initTest() {
        adventureItem = createEntity();
    }

    @Test
    public void createAdventureItem() throws Exception {
        int databaseSizeBeforeCreate = adventureItemRepository.findAll().size();

        // Create the AdventureItem
        AdventureItemDTO adventureItemDTO = adventureItemMapper.toDto(adventureItem);
        restAdventureItemMockMvc.perform(post("/api/adventure-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureItemDTO)))
            .andExpect(status().isCreated());

        // Validate the AdventureItem in the database
        List<AdventureItem> adventureItemList = adventureItemRepository.findAll();
        assertThat(adventureItemList).hasSize(databaseSizeBeforeCreate + 1);
        AdventureItem testAdventureItem = adventureItemList.get(adventureItemList.size() - 1);
        assertThat(testAdventureItem.isIsEquipment()).isEqualTo(DEFAULT_IS_EQUIPMENT);
        assertThat(testAdventureItem.getEquipmentSlot()).isEqualTo(DEFAULT_EQUIPMENT_SLOT);
        assertThat(testAdventureItem.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testAdventureItem.getWeight()).isEqualTo(DEFAULT_WEIGHT);

        // Validate the AdventureItem in Elasticsearch
        verify(mockAdventureItemSearchRepository, times(1)).save(testAdventureItem);
    }

    @Test
    public void createAdventureItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adventureItemRepository.findAll().size();

        // Create the AdventureItem with an existing ID
        adventureItem.setId();
        AdventureItemDTO adventureItemDTO = adventureItemMapper.toDto(adventureItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdventureItemMockMvc.perform(post("/api/adventure-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdventureItem in the database
        List<AdventureItem> adventureItemList = adventureItemRepository.findAll();
        assertThat(adventureItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the AdventureItem in Elasticsearch
        verify(mockAdventureItemSearchRepository, times(0)).save(adventureItem);
    }

    @Test
    public void getAllAdventureItems() throws Exception {
        // Initialize the database
        adventureItemRepository.save(adventureItem);

        // Get all the adventureItemList
        restAdventureItemMockMvc.perform(get("/api/adventure-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].isEquipment").value(hasItem(DEFAULT_IS_EQUIPMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].equipmentSlot").value(hasItem(DEFAULT_EQUIPMENT_SLOT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())));
    }
    
    @Test
    public void getAdventureItem() throws Exception {
        // Initialize the database
        adventureItemRepository.save(adventureItem);

        // Get the adventureItem
        restAdventureItemMockMvc.perform(get("/api/adventure-items/{id}", adventureItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.isEquipment").value(DEFAULT_IS_EQUIPMENT.booleanValue()))
            .andExpect(jsonPath("$.equipmentSlot").value(DEFAULT_EQUIPMENT_SLOT.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.intValue()));
    }

    @Test
    public void getNonExistingAdventureItem() throws Exception {
        // Get the adventureItem
        restAdventureItemMockMvc.perform(get("/api/adventure-items/{id}", ))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAdventureItem() throws Exception {
        // Initialize the database
        adventureItemRepository.save(adventureItem);

        int databaseSizeBeforeUpdate = adventureItemRepository.findAll().size();

        // Update the adventureItem
        AdventureItem updatedAdventureItem = adventureItemRepository.findById(adventureItem.getId()).get();
        updatedAdventureItem
            .isEquipment(UPDATED_IS_EQUIPMENT)
            .equipmentSlot(UPDATED_EQUIPMENT_SLOT)
            .price(UPDATED_PRICE)
            .weight(UPDATED_WEIGHT);
        AdventureItemDTO adventureItemDTO = adventureItemMapper.toDto(updatedAdventureItem);

        restAdventureItemMockMvc.perform(put("/api/adventure-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureItemDTO)))
            .andExpect(status().isOk());

        // Validate the AdventureItem in the database
        List<AdventureItem> adventureItemList = adventureItemRepository.findAll();
        assertThat(adventureItemList).hasSize(databaseSizeBeforeUpdate);
        AdventureItem testAdventureItem = adventureItemList.get(adventureItemList.size() - 1);
        assertThat(testAdventureItem.isIsEquipment()).isEqualTo(UPDATED_IS_EQUIPMENT);
        assertThat(testAdventureItem.getEquipmentSlot()).isEqualTo(UPDATED_EQUIPMENT_SLOT);
        assertThat(testAdventureItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testAdventureItem.getWeight()).isEqualTo(UPDATED_WEIGHT);

        // Validate the AdventureItem in Elasticsearch
        verify(mockAdventureItemSearchRepository, times(1)).save(testAdventureItem);
    }

    @Test
    public void updateNonExistingAdventureItem() throws Exception {
        int databaseSizeBeforeUpdate = adventureItemRepository.findAll().size();

        // Create the AdventureItem
        AdventureItemDTO adventureItemDTO = adventureItemMapper.toDto(adventureItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdventureItemMockMvc.perform(put("/api/adventure-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adventureItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdventureItem in the database
        List<AdventureItem> adventureItemList = adventureItemRepository.findAll();
        assertThat(adventureItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AdventureItem in Elasticsearch
        verify(mockAdventureItemSearchRepository, times(0)).save(adventureItem);
    }

    @Test
    public void deleteAdventureItem() throws Exception {
        // Initialize the database
        adventureItemRepository.save(adventureItem);

        int databaseSizeBeforeDelete = adventureItemRepository.findAll().size();

        // Get the adventureItem
        restAdventureItemMockMvc.perform(delete("/api/adventure-items/{id}", adventureItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AdventureItem> adventureItemList = adventureItemRepository.findAll();
        assertThat(adventureItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AdventureItem in Elasticsearch
        verify(mockAdventureItemSearchRepository, times(1)).deleteById(adventureItem.getId());
    }

    @Test
    public void searchAdventureItem() throws Exception {
        // Initialize the database
        adventureItemRepository.save(adventureItem);
        when(mockAdventureItemSearchRepository.search(queryStringQuery("id:" + adventureItem.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(adventureItem), PageRequest.of(0, 1), 1));
        // Search the adventureItem
        restAdventureItemMockMvc.perform(get("/api/_search/adventure-items?query=id:" + adventureItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].isEquipment").value(hasItem(DEFAULT_IS_EQUIPMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].equipmentSlot").value(hasItem(DEFAULT_EQUIPMENT_SLOT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdventureItem.class);
        AdventureItem adventureItem1 = new AdventureItem();
        adventureItem1.setId();
        AdventureItem adventureItem2 = new AdventureItem();
        adventureItem2.setId(adventureItem1.getId());
        assertThat(adventureItem1).isEqualTo(adventureItem2);
        adventureItem2.setId();
        assertThat(adventureItem1).isNotEqualTo(adventureItem2);
        adventureItem1.setId(null);
        assertThat(adventureItem1).isNotEqualTo(adventureItem2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdventureItemDTO.class);
        AdventureItemDTO adventureItemDTO1 = new AdventureItemDTO();
        adventureItemDTO1.setId();
        AdventureItemDTO adventureItemDTO2 = new AdventureItemDTO();
        assertThat(adventureItemDTO1).isNotEqualTo(adventureItemDTO2);
        adventureItemDTO2.setId(adventureItemDTO1.getId());
        assertThat(adventureItemDTO1).isEqualTo(adventureItemDTO2);
        adventureItemDTO2.setId();
        assertThat(adventureItemDTO1).isNotEqualTo(adventureItemDTO2);
        adventureItemDTO1.setId(null);
        assertThat(adventureItemDTO1).isNotEqualTo(adventureItemDTO2);
    }
}
