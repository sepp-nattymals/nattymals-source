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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * A Announcement.
 */
@Entity
@Table(name = "announcement")
public class Announcement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "title", nullable = false)
    private String title;
    
    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "company_name", nullable = false)
    private String companyName;
    
    @NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy HH:MM")
    @Column(name = "start_date", nullable = false)
    private ZonedDateTime startDate;
    
    @NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy HH:MM")
    @Column(name = "end_date", nullable = false)
    private ZonedDateTime endDate;
    
    @Lob
    @Column(name = "photo")
    private byte[] photo;
    
    @Column(name = "photo_content_type")        
    private String photoContentType;
    
    @NotBlank
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @URL
    @Column(name = "url", nullable=false)
    private String url;
    
    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "administrator_id")
    private Administrator administrator;

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

    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

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

    public byte[] getPhoto() {
        return photo;
    }
    
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }
    
    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Announcement announcement = (Announcement) o;
        if(announcement.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, announcement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Announcement{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", companyName='" + companyName + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", photo='" + photo + "'" +
            ", photoContentType='" + photoContentType + "'" +
            '}';
    }
}
