package com.sepp.nattymals.domain;

import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

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
    @Column(name = "informative_text", nullable = false)
    private String informativeText;
    
    @NotNull
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;
    
    @NotNull
    @Column(name = "modification_date", nullable = false)
    private ZonedDateTime modificationDate;
    
    @NotNull
    @Column(name = "is_removed", nullable = false)
    private Boolean isRemoved;
    
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
