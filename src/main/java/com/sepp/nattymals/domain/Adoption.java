package com.sepp.nattymals.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * A Adoption.
 */
@Entity
@Table(name = "adoption")
public class Adoption extends DomainEntity implements Serializable {

    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "informative_text", nullable = false)
    private String informativeText;
    
    @NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy HH:MM")
    @Past
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;
    
    @DateTimeFormat(pattern="dd/MM/yyyy HH:MM")
    @Column(name = "modification_date")
    private ZonedDateTime modificationDate;
    
    @NotNull
    @Column(name = "is_removed", nullable = false)
    private Boolean isRemoved;
    
    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public String getInformativeText() {
        return informativeText;
    }
    
    public void setInformativeText(String informativeText) {
        this.informativeText = informativeText;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }
    
    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ZonedDateTime getModificationDate() {
        return modificationDate;
    }
    
    public void setModificationDate(ZonedDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Boolean getIsRemoved() {
        return isRemoved;
    }
    
    public void setIsRemoved(Boolean isRemoved) {
        this.isRemoved = isRemoved;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    @Override
    public String toString() {
        return "Adoption{" +
            "informativeText='" + informativeText + "'" +
            ", creationDate='" + creationDate + "'" +
            ", modificationDate='" + modificationDate + "'" +
            ", isRemoved='" + isRemoved + "'" +
            '}';
    }
}
