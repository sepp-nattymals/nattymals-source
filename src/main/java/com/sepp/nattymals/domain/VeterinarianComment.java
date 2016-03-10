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
 * A VeterinarianComment.
 */
@Entity
@Table(name = "veterinarian_comment")
public class VeterinarianComment extends DomainEntity implements Serializable {

    @NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy HH:MM")
    @Past
    @Column(name = "creation_date", nullable=false)
    private ZonedDateTime creationDate;
    
    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "text", nullable=false)
    private String text;
    
    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "veterinarian_id")
    private Veterinarian veterinarian;

    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "pet_owner_id")
    private PetOwner petOwner;

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
    public String toString() {
        return "VeterinarianComment{" +
            "creationDate='" + creationDate + "'" +
            ", text='" + text + "'" +
            '}';
    }
}
