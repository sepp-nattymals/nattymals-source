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
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
public class Payment extends DomainEntity implements Serializable {

    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "concept", nullable = false)
    private String concept;
    
    @NotNull
    @Min(value = 0)
    @Column(name = "amount", nullable = false)
    private Double amount;
    
    @NotNull
    @Past
    @DateTimeFormat(pattern="dd/MM/yyyy HH:MM")
    @Column(name = "payment_date", nullable = false)
    private ZonedDateTime paymentDate;
    
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "comment")
    private String comment;
    
    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "actor_id")
    private Actor actor;

    public String getConcept() {
        return concept;
    }
    
    public void setConcept(String concept) {
        this.concept = concept;
    }

    public Double getAmount() {
        return amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ZonedDateTime getPaymentDate() {
        return paymentDate;
    }
    
    public void setPaymentDate(ZonedDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    @Override
    public String toString() {
        return "Payment{" +
            "concept='" + concept + "'" +
            ", amount='" + amount + "'" +
            ", paymentDate='" + paymentDate + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
