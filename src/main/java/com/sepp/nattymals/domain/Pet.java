package com.sepp.nattymals.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Pet.
 */
@Entity
@Table(name = "pet")
public class Pet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "type", nullable = false)
    private String type;
    
    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "sex", nullable = false)
    private String sex;
    
    @NotNull
    @Min(value = 0)
    @Column(name = "weight", nullable = false)
    private Double weight;
    
    @NotNull
    @Column(name = "has_pedigree", nullable = false)
    private Boolean hasPedigree;
    
    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "race", nullable = false)
    private String race;
    
    @NotNull
    @Past
    @DateTimeFormat(pattern="dd/MM/yyyy")
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    
    @Lob
    @Column(name = "photo")
    private byte[] photo;
    
    @Column(name = "photo_content_type")        
    private String photoContentType;
    
    @NotNull
    @Column(name = "dating", nullable = false)
    private Boolean dating;
    
    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "pet_owner_id")
    private PetOwner petOwner;

    @OneToMany(mappedBy = "pet")
    @JsonIgnore
    private Set<PetComment> petComments = new HashSet<>();

    @OneToMany(mappedBy = "pet")
    @JsonIgnore
    private Set<Adoption> adoptions = new HashSet<>();

    @OneToMany(mappedBy = "pet")
    @JsonIgnore
    private Set<PetRating> petRatings = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }

    public Double getWeight() {
        return weight;
    }
    
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Boolean getHasPedigree() {
        return hasPedigree;
    }
    
    public void setHasPedigree(Boolean hasPedigree) {
        this.hasPedigree = hasPedigree;
    }

    public String getRace() {
        return race;
    }
    
    public void setRace(String race) {
        this.race = race;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public byte[] getPhoto() {
        return photo;
    }
    
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Boolean getDating() {
        return dating;
    }
    
    public void setDating(Boolean dating) {
        this.dating = dating;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public PetOwner getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(PetOwner petOwner) {
        this.petOwner = petOwner;
    }

    public Set<PetComment> getPetComments() {
        return petComments;
    }

    public void setPetComments(Set<PetComment> petComments) {
        this.petComments = petComments;
    }

    public Set<Adoption> getAdoptions() {
        return adoptions;
    }

    public void setAdoptions(Set<Adoption> adoptions) {
        this.adoptions = adoptions;
    }

    public Set<PetRating> getPetRatings() {
        return petRatings;
    }

    public void setPetRatings(Set<PetRating> petRatings) {
        this.petRatings = petRatings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pet pet = (Pet) o;
        if(pet.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pet{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", sex='" + sex + "'" +
            ", weight='" + weight + "'" +
            ", hasPedigree='" + hasPedigree + "'" +
            ", race='" + race + "'" +
            ", birthDate='" + birthDate + "'" +
            ", photo='" + photo + "'" +
            ", photoContentType='" + photoContentType + "'" +
            ", dating='" + dating + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
