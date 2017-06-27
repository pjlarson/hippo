package com.rbc.gti.chorus.choir.hippo.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Entitlement entity.
 */
public class EntitlementDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String appCode;

    private Boolean enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntitlementDTO entitlementDTO = (EntitlementDTO) o;
        if(entitlementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entitlementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntitlementDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", appCode='" + getAppCode() + "'" +
            ", enabled='" + isEnabled() + "'" +
            "}";
    }
}
