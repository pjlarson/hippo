package com.rbc.gti.chorus.choir.hippo.service.mapper;

import com.rbc.gti.chorus.choir.hippo.domain.*;
import com.rbc.gti.chorus.choir.hippo.service.dto.EmployeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Employee and its DTO EmployeeDTO.
 */
@Mapper(componentModel = "spring", uses = {EntitlementMapper.class, })
public interface EmployeeMapper extends EntityMapper <EmployeeDTO, Employee> {
    
    
    default Employee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }
}
