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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Folder.
 */
@Entity
@Table(name = "folder")
public class Folder extends DomainEntity implements Serializable {

    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Pattern(regexp="^inbox|^outbox|^trashbox")
    @Column(name = "name", nullable=false)
    private String name;
    
    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "folder")
    @JsonIgnore
    private Set<Message> messages = new HashSet<>();

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Folder{" +
            "name='" + name + "'" +
            '}';
    }
}
