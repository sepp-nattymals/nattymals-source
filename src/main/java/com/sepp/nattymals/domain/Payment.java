package com.sepp.nattymals.domain;

import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "concept", nullable = false)
    private String concept;
    
    @NotNull
    @Min(value = 0)
    @Column(name = "amount", nullable = false)
    private Double amount;
    
    @NotNull
    @Column(name = "payment_date", nullable = false)
    private ZonedDateTime paymentDate;
    
    @NotNull
    @Column(name = "comment", nullable = false)
    private String comment;
    
    @ManyToOne
    @JoinColumn(name = "actor_id")
    private Actor actor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Payment payment = (Payment) o;
        if(payment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Payment{" +
            "id=" + id +
            ", concept='" + concept + "'" +
            ", amount='" + amount + "'" +
            ", paymentDate='" + paymentDate + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
