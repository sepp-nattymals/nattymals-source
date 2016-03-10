package com.sepp.nattymals.domain;


import java.io.Serializable;
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

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

/**
 * A Clinic.
 */
@Entity
@Table(name = "clinic")
public class Clinic extends DomainEntity implements Serializable {

    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "address", nullable=false)
    private String address;
    
    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "city", nullable=false)
    private String city;
    
    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "province", nullable=false)
    private String province;
    
    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "schedule", nullable=false)
    private String schedule;
    
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "veterinarian_id")
    private Veterinarian veterinarian;

    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }
    
    public void setProvince(String province) {
        this.province = province;
    }

    public String getSchedule() {
        return schedule;
    }
    
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Veterinarian getVeterinarian() {
        return veterinarian;
    }

    public void setVeterinarian(Veterinarian veterinarian) {
        this.veterinarian = veterinarian;
    }

    @Override
    public String toString() {
        return "Clinic{" +
            "address='" + address + "'" +
            ", city='" + city + "'" +
            ", province='" + province + "'" +
            ", schedule='" + schedule + "'" +
            ", phoneNumber='" + phoneNumber + "'" +
            '}';
    }
}
