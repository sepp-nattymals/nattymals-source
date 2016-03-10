package com.sepp.nattymals.domain;


import java.io.Serializable;
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

import org.hibernate.validator.constraints.Range;

/**
 * A PetRating.
 */
@Entity
@Table(name = "pet_rating")
public class PetRating extends DomainEntity implements Serializable {

    @NotNull
    @Range(min=1, max=5)
    @Column(name = "rating", nullable = false)
    private Integer rating;
    
    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "pet_owner_id")
    private PetOwner petOwner;

    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public PetOwner getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(PetOwner petOwner) {
        this.petOwner = petOwner;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    @Override
    public String toString() {
        return "PetRating{" +
            "rating='" + rating + "'" +
            '}';
    }
}
