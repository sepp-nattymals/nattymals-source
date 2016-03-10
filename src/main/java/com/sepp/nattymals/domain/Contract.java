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
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * A Contract.
 */
@Entity
@Table(name = "contract", uniqueConstraints=@UniqueConstraint(columnNames="reference_code"))
public class Contract extends DomainEntity implements Serializable {

    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "title", nullable = false)
    private String title;
    
    @NotNull
    @Min(value = 0)
    @Column(name = "fee", nullable = false)
    private Double fee;
    
    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "reference_code", nullable = false, unique=true)
    private String referenceCode;
    
    @NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy HH:MM")
    @Past
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;
    
    @NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy HH:MM")
    @Column(name = "termination_date", nullable = false)
    private ZonedDateTime terminationDate;
    
    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "terms", nullable=false)
    private String terms;
    
    @NotNull
    @Column(name = "registered", nullable = false)
    private Boolean registered;
    
    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "pet_company_id")
    private PetCompany petCompany;

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
    public String toString() {
        return "Contract{" +
            "title='" + title + "'" +
            ", fee='" + fee + "'" +
            ", referenceCode='" + referenceCode + "'" +
            ", creationDate='" + creationDate + "'" +
            ", terminationDate='" + terminationDate + "'" +
            ", terms='" + terms + "'" +
            ", registered='" + registered + "'" +
            '}';
    }
}
