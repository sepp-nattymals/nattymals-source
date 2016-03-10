package com.sepp.nattymals.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Actor.
 */
@Entity
@Table(name = "actor")
public abstract class Actor extends DomainEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "address", nullable = false)
    private String address;
    
	@NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "phone", nullable = false)
    private String phone;
    
    @OneToMany(mappedBy = "actor")
    @JsonIgnore
    private Set<Payment> payments = new HashSet<>();

    @NotNull
    @Valid
    @OneToOne(optional=false)
    private User user;

	public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

 
    @Override
    public String toString() {
        return "Actor{" +
            "address='" + address + "'" +
            ", phone='" + phone + "'" +
            '}';
    }
}
