package com.rbc.gti.chorus.choir.hippo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Entitlement.
 */
@Entity
@Table(name = "entitlement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Entitlement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "app_code", nullable = false)
    private String appCode;

    @Column(name = "enabled")
    private Boolean enabled;

    @ManyToMany(mappedBy = "entitlements")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Employee> employees = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Entitlement name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppCode() {
        return appCode;
    }

    public Entitlement appCode(String appCode) {
        this.appCode = appCode;
        return this;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Entitlement enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Entitlement employees(Set<Employee> employees) {
        this.employees = employees;
        return this;
    }

    public Entitlement addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getEntitlements().add(this);
        return this;
    }

    public Entitlement removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.getEntitlements().remove(this);
        return this;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entitlement entitlement = (Entitlement) o;
        if (entitlement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entitlement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Entitlement{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", appCode='" + getAppCode() + "'" +
            ", enabled='" + isEnabled() + "'" +
            "}";
    }
}
