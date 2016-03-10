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
 * A PetComment.
 */
@Entity
@Table(name = "pet_comment")
public class PetComment extends DomainEntity implements Serializable {

    @NotNull
    @Past
    @DateTimeFormat(pattern="dd/MM/yyyy HH:MM")
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;
    
    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "text", nullable = false)
    private String text;
    
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
        return "PetComment{" +
            "creationDate='" + creationDate + "'" +
            ", text='" + text + "'" +
            '}';
    }
}
