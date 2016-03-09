package com.sepp.nattymals.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Veterinarian.
 */
@Entity
@Table(name = "veterinarian",uniqueConstraints=@UniqueConstraint(columnNames="referee_number"))
public class Veterinarian extends Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "bank_account", nullable=false)
    private String bankAccount;
    
    @URL
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "web_address")
    private String webAddress;
    
    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "referee_number", nullable=false, unique=true)
    private String refereeNumber;
    
    @OneToMany(mappedBy = "veterinarian")
    @JsonIgnore
    private Set<Clinic> clinics = new HashSet<>();

    @OneToMany(mappedBy = "veterinarian")
    @JsonIgnore
    private Set<VeterinarianRating> veterinarianRatings = new HashSet<>();

    @OneToMany(mappedBy = "veterinarian")
    @JsonIgnore
    private Set<VeterinarianComment> veterinarianComments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankAccount() {
        return bankAccount;
    }
    
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getWebAddress() {
        return webAddress;
    }
    
    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public String getRefereeNumber() {
        return refereeNumber;
    }
    
    public void setRefereeNumber(String refereeNumber) {
        this.refereeNumber = refereeNumber;
    }

    public Set<Clinic> getClinics() {
        return clinics;
    }

    public void setClinics(Set<Clinic> clinics) {
        this.clinics = clinics;
    }

    public Set<VeterinarianRating> getVeterinarianRatings() {
        return veterinarianRatings;
    }

    public void setVeterinarianRatings(Set<VeterinarianRating> veterinarianRatings) {
        this.veterinarianRatings = veterinarianRatings;
    }

    public Set<VeterinarianComment> getVeterinarianComments() {
        return veterinarianComments;
    }

    public void setVeterinarianComments(Set<VeterinarianComment> veterinarianComments) {
        this.veterinarianComments = veterinarianComments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Veterinarian veterinarian = (Veterinarian) o;
        if(veterinarian.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, veterinarian.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Veterinarian{" +
            "id=" + id +
            ", bankAccount='" + bankAccount + "'" +
            ", webAddress='" + webAddress + "'" +
            ", refereeNumber='" + refereeNumber + "'" +
            '}';
    }
}
