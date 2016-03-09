package com.sepp.nattymals.domain;

import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Contract.
 */
@Entity
@Table(name = "contract")
public class Contract implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;
    
    @NotNull
    @Min(value = 0)
    @Column(name = "fee", nullable = false)
    private Double fee;
    
    @NotNull
    @Column(name = "reference_code", nullable = false)
    private String referenceCode;
    
    @NotNull
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;
    
    @NotNull
    @Column(name = "termination_date", nullable = false)
    private ZonedDateTime terminationDate;
    
    @Column(name = "terms")
    private String terms;
    
    @NotNull
    @Column(name = "registered", nullable = false)
    private Boolean registered;
    
    @ManyToOne
    @JoinColumn(name = "pet_company_id")
    private PetCompany petCompany;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public Double getFee() {
        return fee;
    }
    
    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getReferenceCode() {
        return referenceCode;
    }
    
    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }
    
    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ZonedDateTime getTerminationDate() {
        return terminationDate;
    }
    
    public void setTerminationDate(ZonedDateTime terminationDate) {
        this.terminationDate = terminationDate;
    }

    public String getTerms() {
        return terms;
    }
    
    public void setTerms(String terms) {
        this.terms = terms;
    }

    public Boolean getRegistered() {
        return registered;
    }
    
    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }

    public PetCompany getPetCompany() {
        return petCompany;
    }

    public void setPetCompany(PetCompany petCompany) {
        this.petCompany = petCompany;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contract contract = (Contract) o;
        if(contract.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, contract.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Contract{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", fee='" + fee + "'" +
            ", referenceCode='" + referenceCode + "'" +
            ", creationDate='" + creationDate + "'" +
            ", terminationDate='" + terminationDate + "'" +
            ", terms='" + terms + "'" +
            ", registered='" + registered + "'" +
            '}';
    }
}
