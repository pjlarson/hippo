package com.rbc.gti.chorus.choir.hippo.service.impl;

import com.rbc.gti.chorus.choir.hippo.service.EntitlementService;
import com.rbc.gti.chorus.choir.hippo.domain.Entitlement;
import com.rbc.gti.chorus.choir.hippo.repository.EntitlementRepository;
import com.rbc.gti.chorus.choir.hippo.service.dto.EntitlementDTO;
import com.rbc.gti.chorus.choir.hippo.service.mapper.EntitlementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Entitlement.
 */
@Service
@Transactional
public class EntitlementServiceImpl implements EntitlementService{

    private final Logger log = LoggerFactory.getLogger(EntitlementServiceImpl.class);

    private final EntitlementRepository entitlementRepository;

    private final EntitlementMapper entitlementMapper;

    public EntitlementServiceImpl(EntitlementRepository entitlementRepository, EntitlementMapper entitlementMapper) {
        this.entitlementRepository = entitlementRepository;
        this.entitlementMapper = entitlementMapper;
    }

    /**
     * Save a entitlement.
     *
     * @param entitlementDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EntitlementDTO save(EntitlementDTO entitlementDTO) {
        log.debug("Request to save Entitlement : {}", entitlementDTO);
        Entitlement entitlement = entitlementMapper.toEntity(entitlementDTO);
        entitlement = entitlementRepository.save(entitlement);
        return entitlementMapper.toDto(entitlement);
    }

    /**
     *  Get all the entitlements.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EntitlementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Entitlements");
        return entitlementRepository.findAll(pageable)
            .map(entitlementMapper::toDto);
    }

    /**
     *  Get one entitlement by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EntitlementDTO findOne(Long id) {
        log.debug("Request to get Entitlement : {}", id);
        Entitlement entitlement = entitlementRepository.findOne(id);
        return entitlementMapper.toDto(entitlement);
    }

    /**
     *  Delete the  entitlement by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Entitlement : {}", id);
        entitlementRepository.delete(id);
    }
}
