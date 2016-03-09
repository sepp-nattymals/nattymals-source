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

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * A Adoption.
 */
@Entity
@Table(name = "adoption")
public class Adoption implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Adoption adoption = (Adoption) o;
        if(adoption.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, adoption.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Adoption{" +
            "id=" + id +
            ", informativeText='" + informativeText + "'" +
            ", creationDate='" + creationDate + "'" +
            ", modificationDate='" + modificationDate + "'" +
            ", isRemoved='" + isRemoved + "'" +
            '}';
    }
}
