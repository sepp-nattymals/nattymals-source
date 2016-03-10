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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * A PremiumSubscription.
 */
@Entity
@Table(name = "premium_subscription")
public class PremiumSubscription extends DomainEntity implements Serializable {

    @NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy HH:MM")
    @Column(name = "start_date", nullable=false)
    private ZonedDateTime startDate;
    
    @NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy HH:MM")
    @Column(name = "end_date", nullable=false)
    private ZonedDateTime endDate;
    
    @NotNull
    @Min(0)
    @Column(name = "fee", nullable=false)
    private Double fee;
    
    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public ZonedDateTime getStartDate() {
        return startDate;
    }
    
    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }
    
    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Double getFee() {
        return fee;
    }
    
    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "PremiumSubscription{" +
            "startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", fee='" + fee + "'" +
            '}';
    }
}
