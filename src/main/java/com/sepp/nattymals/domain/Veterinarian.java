package com.sepp.nattymals.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Veterinarian.
 */
@Entity
@Table(name = "veterinarian")
public class Veterinarian implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "bank_account")
    private String bankAccount;
    
    @Column(name = "web_address")
    private String webAddress;
    
    @Column(name = "referee_number")
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
