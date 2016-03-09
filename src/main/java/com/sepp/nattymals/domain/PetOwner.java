package com.sepp.nattymals.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PetOwner.
 */
@Entity
@Table(name = "pet_owner")
public class PetOwner extends Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "total_points", nullable = false)
    private Integer totalPoints;
    
    @NotNull
    @Column(name = "is_blocked", nullable = false)
    private Boolean isBlocked;
    
    @OneToMany(mappedBy = "petOwner")
    @JsonIgnore
    private Set<HistoricalPoints> historicalPointss = new HashSet<>();

    @OneToMany(mappedBy = "petOwner")
    @JsonIgnore
    private Set<VeterinarianComment> veterinarianComments = new HashSet<>();

    @OneToMany(mappedBy = "petOwner")
    @JsonIgnore
    private Set<PetComment> petComments = new HashSet<>();

    @OneToMany(mappedBy = "petOwner")
    @JsonIgnore
    private Set<Medals> medalss = new HashSet<>();

    @OneToMany(mappedBy = "petOwner")
    @JsonIgnore
    private Set<Pet> pets = new HashSet<>();

    @OneToMany(mappedBy = "petOwner")
    @JsonIgnore
    private Set<PetRating> petRatings = new HashSet<>();

    @OneToMany(mappedBy = "petOwner")
    @JsonIgnore
    private Set<VeterinarianRating> veterinarianRatings = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }
    
    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Boolean getIsBlocked() {
        return isBlocked;
    }
    
    public void setIsBlocked(Boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Set<HistoricalPoints> getHistoricalPointss() {
        return historicalPointss;
    }

    public void setHistoricalPointss(Set<HistoricalPoints> historicalPointss) {
        this.historicalPointss = historicalPointss;
    }

    public Set<VeterinarianComment> getVeterinarianComments() {
        return veterinarianComments;
    }

    public void setVeterinarianComments(Set<VeterinarianComment> veterinarianComments) {
        this.veterinarianComments = veterinarianComments;
    }

    public Set<PetComment> getPetComments() {
        return petComments;
    }

    public void setPetComments(Set<PetComment> petComments) {
        this.petComments = petComments;
    }

    public Set<Medals> getMedalss() {
        return medalss;
    }

    public void setMedalss(Set<Medals> medalss) {
        this.medalss = medalss;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }

    public Set<PetRating> getPetRatings() {
        return petRatings;
    }

    public void setPetRatings(Set<PetRating> petRatings) {
        this.petRatings = petRatings;
    }

    public Set<VeterinarianRating> getVeterinarianRatings() {
        return veterinarianRatings;
    }

    public void setVeterinarianRatings(Set<VeterinarianRating> veterinarianRatings) {
        this.veterinarianRatings = veterinarianRatings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PetOwner petOwner = (PetOwner) o;
        if(petOwner.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, petOwner.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PetOwner{" +
            "id=" + id +
            ", totalPoints='" + totalPoints + "'" +
            ", isBlocked='" + isBlocked + "'" +
            '}';
    }
}
