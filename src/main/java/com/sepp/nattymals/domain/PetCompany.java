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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A PetCompany.
 */
@Entity
@Table(name = "pet_company", uniqueConstraints=@UniqueConstraint(columnNames="nif"))
public class PetCompany extends Actor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "bank_account", nullable = false)
    private String bankAccount;
    
    @NotNull
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "nif", nullable = false, unique=true)
    private String nif;
    
    @OneToMany(mappedBy = "petCompany")
    @JsonIgnore
    private Set<Contract> contracts = new HashSet<>();

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

    public String getNif() {
        return nif;
    }
    
    public void setNif(String nif) {
        this.nif = nif;
    }

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PetCompany petCompany = (PetCompany) o;
        if(petCompany.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, petCompany.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PetCompany{" +
            "id=" + id +
            ", bankAccount='" + bankAccount + "'" +
            ", nif='" + nif + "'" +
            '}';
    }
}
