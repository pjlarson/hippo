package com.rbc.gti.chorus.choir.hippo.repository;

import com.rbc.gti.chorus.choir.hippo.domain.Entitlement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Entitlement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntitlementRepository extends JpaRepository<Entitlement,Long> {
    
}
