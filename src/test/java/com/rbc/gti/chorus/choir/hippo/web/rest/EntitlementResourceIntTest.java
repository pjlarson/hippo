package com.rbc.gti.chorus.choir.hippo.web.rest;

import com.rbc.gti.chorus.choir.hippo.HippoApp;

import com.rbc.gti.chorus.choir.hippo.domain.Entitlement;
import com.rbc.gti.chorus.choir.hippo.repository.EntitlementRepository;
import com.rbc.gti.chorus.choir.hippo.service.EntitlementService;
import com.rbc.gti.chorus.choir.hippo.service.dto.EntitlementDTO;
import com.rbc.gti.chorus.choir.hippo.service.mapper.EntitlementMapper;
import com.rbc.gti.chorus.choir.hippo.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EntitlementResource REST controller.
 *
 * @see EntitlementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HippoApp.class)
public class EntitlementResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_APP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_APP_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    @Autowired
    private EntitlementRepository entitlementRepository;

    @Autowired
    private EntitlementMapper entitlementMapper;

    @Autowired
    private EntitlementService entitlementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEntitlementMockMvc;

    private Entitlement entitlement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EntitlementResource entitlementResource = new EntitlementResource(entitlementService);
        this.restEntitlementMockMvc = MockMvcBuilders.standaloneSetup(entitlementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entitlement createEntity(EntityManager em) {
        Entitlement entitlement = new Entitlement()
            .name(DEFAULT_NAME)
            .appCode(DEFAULT_APP_CODE)
            .enabled(DEFAULT_ENABLED);
        return entitlement;
    }

    @Before
    public void initTest() {
        entitlement = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntitlement() throws Exception {
        int databaseSizeBeforeCreate = entitlementRepository.findAll().size();

        // Create the Entitlement
        EntitlementDTO entitlementDTO = entitlementMapper.toDto(entitlement);
        restEntitlementMockMvc.perform(post("/api/entitlements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entitlementDTO)))
            .andExpect(status().isCreated());

        // Validate the Entitlement in the database
        List<Entitlement> entitlementList = entitlementRepository.findAll();
        assertThat(entitlementList).hasSize(databaseSizeBeforeCreate + 1);
        Entitlement testEntitlement = entitlementList.get(entitlementList.size() - 1);
        assertThat(testEntitlement.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEntitlement.getAppCode()).isEqualTo(DEFAULT_APP_CODE);
        assertThat(testEntitlement.isEnabled()).isEqualTo(DEFAULT_ENABLED);
    }

    @Test
    @Transactional
    public void createEntitlementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entitlementRepository.findAll().size();

        // Create the Entitlement with an existing ID
        entitlement.setId(1L);
        EntitlementDTO entitlementDTO = entitlementMapper.toDto(entitlement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntitlementMockMvc.perform(post("/api/entitlements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entitlementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Entitlement> entitlementList = entitlementRepository.findAll();
        assertThat(entitlementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = entitlementRepository.findAll().size();
        // set the field null
        entitlement.setName(null);

        // Create the Entitlement, which fails.
        EntitlementDTO entitlementDTO = entitlementMapper.toDto(entitlement);

        restEntitlementMockMvc.perform(post("/api/entitlements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entitlementDTO)))
            .andExpect(status().isBadRequest());

        List<Entitlement> entitlementList = entitlementRepository.findAll();
        assertThat(entitlementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAppCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = entitlementRepository.findAll().size();
        // set the field null
        entitlement.setAppCode(null);

        // Create the Entitlement, which fails.
        EntitlementDTO entitlementDTO = entitlementMapper.toDto(entitlement);

        restEntitlementMockMvc.perform(post("/api/entitlements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entitlementDTO)))
            .andExpect(status().isBadRequest());

        List<Entitlement> entitlementList = entitlementRepository.findAll();
        assertThat(entitlementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEntitlements() throws Exception {
        // Initialize the database
        entitlementRepository.saveAndFlush(entitlement);

        // Get all the entitlementList
        restEntitlementMockMvc.perform(get("/api/entitlements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entitlement.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].appCode").value(hasItem(DEFAULT_APP_CODE.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }

    @Test
    @Transactional
    public void getEntitlement() throws Exception {
        // Initialize the database
        entitlementRepository.saveAndFlush(entitlement);

        // Get the entitlement
        restEntitlementMockMvc.perform(get("/api/entitlements/{id}", entitlement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entitlement.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.appCode").value(DEFAULT_APP_CODE.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEntitlement() throws Exception {
        // Get the entitlement
        restEntitlementMockMvc.perform(get("/api/entitlements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntitlement() throws Exception {
        // Initialize the database
        entitlementRepository.saveAndFlush(entitlement);
        int databaseSizeBeforeUpdate = entitlementRepository.findAll().size();

        // Update the entitlement
        Entitlement updatedEntitlement = entitlementRepository.findOne(entitlement.getId());
        updatedEntitlement
            .name(UPDATED_NAME)
            .appCode(UPDATED_APP_CODE)
            .enabled(UPDATED_ENABLED);
        EntitlementDTO entitlementDTO = entitlementMapper.toDto(updatedEntitlement);

        restEntitlementMockMvc.perform(put("/api/entitlements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entitlementDTO)))
            .andExpect(status().isOk());

        // Validate the Entitlement in the database
        List<Entitlement> entitlementList = entitlementRepository.findAll();
        assertThat(entitlementList).hasSize(databaseSizeBeforeUpdate);
        Entitlement testEntitlement = entitlementList.get(entitlementList.size() - 1);
        assertThat(testEntitlement.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEntitlement.getAppCode()).isEqualTo(UPDATED_APP_CODE);
        assertThat(testEntitlement.isEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void updateNonExistingEntitlement() throws Exception {
        int databaseSizeBeforeUpdate = entitlementRepository.findAll().size();

        // Create the Entitlement
        EntitlementDTO entitlementDTO = entitlementMapper.toDto(entitlement);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEntitlementMockMvc.perform(put("/api/entitlements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entitlementDTO)))
            .andExpect(status().isCreated());

        // Validate the Entitlement in the database
        List<Entitlement> entitlementList = entitlementRepository.findAll();
        assertThat(entitlementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEntitlement() throws Exception {
        // Initialize the database
        entitlementRepository.saveAndFlush(entitlement);
        int databaseSizeBeforeDelete = entitlementRepository.findAll().size();

        // Get the entitlement
        restEntitlementMockMvc.perform(delete("/api/entitlements/{id}", entitlement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Entitlement> entitlementList = entitlementRepository.findAll();
        assertThat(entitlementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entitlement.class);
        Entitlement entitlement1 = new Entitlement();
        entitlement1.setId(1L);
        Entitlement entitlement2 = new Entitlement();
        entitlement2.setId(entitlement1.getId());
        assertThat(entitlement1).isEqualTo(entitlement2);
        entitlement2.setId(2L);
        assertThat(entitlement1).isNotEqualTo(entitlement2);
        entitlement1.setId(null);
        assertThat(entitlement1).isNotEqualTo(entitlement2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntitlementDTO.class);
        EntitlementDTO entitlementDTO1 = new EntitlementDTO();
        entitlementDTO1.setId(1L);
        EntitlementDTO entitlementDTO2 = new EntitlementDTO();
        assertThat(entitlementDTO1).isNotEqualTo(entitlementDTO2);
        entitlementDTO2.setId(entitlementDTO1.getId());
        assertThat(entitlementDTO1).isEqualTo(entitlementDTO2);
        entitlementDTO2.setId(2L);
        assertThat(entitlementDTO1).isNotEqualTo(entitlementDTO2);
        entitlementDTO1.setId(null);
        assertThat(entitlementDTO1).isNotEqualTo(entitlementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(entitlementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(entitlementMapper.fromId(null)).isNull();
    }
}
