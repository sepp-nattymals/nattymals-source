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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
public class Message extends DomainEntity implements Serializable {

    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "text", nullable=false)
    private String text;
    
    @NotNull
    @Past
    @DateTimeFormat(pattern="dd/MM/yyyy HH:MM")
    @Column(name = "creation_date", nullable=false)
    private ZonedDateTime creationDate;
    
    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "subject", nullable=false)
    private String subject;
    
    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;
    
    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Customer sender;

    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private Customer recipient;

    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }
    
    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Customer getSender() {
        return sender;
    }

    public void setSender(Customer customer) {
        this.sender = customer;
    }

    public Customer getRecipient() {
        return recipient;
    }

    public void setRecipient(Customer customer) {
        this.recipient = customer;
    }

    public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	@Override
    public String toString() {
        return "Message{" +
            "text='" + text + "'" +
            ", creationDate='" + creationDate + "'" +
            ", subject='" + subject + "'" +
            '}';
    }
}
