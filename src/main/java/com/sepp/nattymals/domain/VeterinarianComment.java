package com.sepp.nattymals.domain;

import java.time.ZonedDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A VeterinarianComment.
 */
@Entity
@Table(name = "veterinarian_comment")
public class VeterinarianComment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;
    
    @Column(name = "text")
    private String text;
    
    @ManyToOne
    @JoinColumn(name = "veterinarian_id")
    private Veterinarian veterinarian;

    @ManyToOne
    @JoinColumn(name = "pet_owner_id")
    private PetOwner petOwner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }
    
    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }

    public Veterinarian getVeterinarian() {
        return veterinarian;
    }

    public void setVeterinarian(Veterinarian veterinarian) {
        this.veterinarian = veterinarian;
    }

    public PetOwner getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(PetOwner petOwner) {
        this.petOwner = petOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VeterinarianComment veterinarianComment = (VeterinarianComment) o;
        if(veterinarianComment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, veterinarianComment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "VeterinarianComment{" +
            "id=" + id +
            ", creationDate='" + creationDate + "'" +
            ", text='" + text + "'" +
            '}';
    }
}
