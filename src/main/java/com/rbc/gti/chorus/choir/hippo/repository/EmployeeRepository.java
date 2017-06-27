package com.rbc.gti.chorus.choir.hippo.repository;

import com.rbc.gti.chorus.choir.hippo.domain.Employee;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    
    @Query("select distinct employee from Employee employee left join fetch employee.entitlements")
    List<Employee> findAllWithEagerRelationships();

    @Query("select employee from Employee employee left join fetch employee.entitlements where employee.id =:id")
    Employee findOneWithEagerRelationships(@Param("id") Long id);
    
}
