package com.rbc.gti.chorus.choir.hippo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rbc.gti.chorus.choir.hippo.service.EntitlementService;
import com.rbc.gti.chorus.choir.hippo.web.rest.util.HeaderUtil;
import com.rbc.gti.chorus.choir.hippo.web.rest.util.PaginationUtil;
import com.rbc.gti.chorus.choir.hippo.service.dto.EntitlementDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Entitlement.
 */
@RestController
@RequestMapping("/api")
public class EntitlementResource {

    private final Logger log = LoggerFactory.getLogger(EntitlementResource.class);

    private static final String ENTITY_NAME = "entitlement";

    private final EntitlementService entitlementService;

    public EntitlementResource(EntitlementService entitlementService) {
        this.entitlementService = entitlementService;
    }

    /**
     * POST  /entitlements : Create a new entitlement.
     *
     * @param entitlementDTO the entitlementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entitlementDTO, or with status 400 (Bad Request) if the entitlement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entitlements")
    @Timed
    public ResponseEntity<EntitlementDTO> createEntitlement(@Valid @RequestBody EntitlementDTO entitlementDTO) throws URISyntaxException {
        log.debug("REST request to save Entitlement : {}", entitlementDTO);
        if (entitlementDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new entitlement cannot already have an ID")).body(null);
        }
        EntitlementDTO result = entitlementService.save(entitlementDTO);
        return ResponseEntity.created(new URI("/api/entitlements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entitlements : Updates an existing entitlement.
     *
     * @param entitlementDTO the entitlementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entitlementDTO,
     * or with status 400 (Bad Request) if the entitlementDTO is not valid,
     * or with status 500 (Internal Server Error) if the entitlementDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entitlements")
    @Timed
    public ResponseEntity<EntitlementDTO> updateEntitlement(@Valid @RequestBody EntitlementDTO entitlementDTO) throws URISyntaxException {
        log.debug("REST request to update Entitlement : {}", entitlementDTO);
        if (entitlementDTO.getId() == null) {
            return createEntitlement(entitlementDTO);
        }
        EntitlementDTO result = entitlementService.save(entitlementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entitlementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entitlements : get all the entitlements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of entitlements in body
     */
    @GetMapping("/entitlements")
    @Timed
    public ResponseEntity<List<EntitlementDTO>> getAllEntitlements(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Entitlements");
        Page<EntitlementDTO> page = entitlementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/entitlements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /entitlements/:id : get the "id" entitlement.
     *
     * @param id the id of the entitlementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entitlementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/entitlements/{id}")
    @Timed
    public ResponseEntity<EntitlementDTO> getEntitlement(@PathVariable Long id) {
        log.debug("REST request to get Entitlement : {}", id);
        EntitlementDTO entitlementDTO = entitlementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(entitlementDTO));
    }

    /**
     * DELETE  /entitlements/:id : delete the "id" entitlement.
     *
     * @param id the id of the entitlementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entitlements/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntitlement(@PathVariable Long id) {
        log.debug("REST request to delete Entitlement : {}", id);
        entitlementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
