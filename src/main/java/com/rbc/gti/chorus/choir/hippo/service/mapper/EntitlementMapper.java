package com.rbc.gti.chorus.choir.hippo.service.mapper;

import com.rbc.gti.chorus.choir.hippo.domain.*;
import com.rbc.gti.chorus.choir.hippo.service.dto.EntitlementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Entitlement and its DTO EntitlementDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EntitlementMapper extends EntityMapper <EntitlementDTO, Entitlement> {
    
    @Mapping(target = "employees", ignore = true)
    Entitlement toEntity(EntitlementDTO entitlementDTO); 
    default Entitlement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Entitlement entitlement = new Entitlement();
        entitlement.setId(id);
        return entitlement;
    }
}
