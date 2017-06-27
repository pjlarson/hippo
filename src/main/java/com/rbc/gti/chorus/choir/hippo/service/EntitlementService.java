package com.rbc.gti.chorus.choir.hippo.service;

import com.rbc.gti.chorus.choir.hippo.service.dto.EntitlementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Entitlement.
 */
public interface EntitlementService {

    /**
     * Save a entitlement.
     *
     * @param entitlementDTO the entity to save
     * @return the persisted entity
     */
    EntitlementDTO save(EntitlementDTO entitlementDTO);

    /**
     *  Get all the entitlements.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EntitlementDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" entitlement.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EntitlementDTO findOne(Long id);

    /**
     *  Delete the "id" entitlement.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
