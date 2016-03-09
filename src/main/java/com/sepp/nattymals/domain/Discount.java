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

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * A Discount.
 */
@Entity
@Table(name = "discount")
public class Discount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "company_name", nullable = false)
    private String companyName;
    
    @NotNull
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "title", nullable = false)
    private String title;
    
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "description")
    private String description;
    
    @NotNull
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "code", nullable = false)
    private String code;
    
    @NotNull
    @Min(value = 0)
    @Column(name = "discount_rate", nullable = false)
    private Integer discountRate;
    
    @NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy HH:MM")
    @Column(name = "start_date", nullable = false)
    private ZonedDateTime startDate;
    
    @NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy HH:MM")
    @Column(name = "end_date", nullable = false)
    private ZonedDateTime endDate;
    
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

    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDiscountRate() {
        return discountRate;
    }
    
    public void setDiscountRate(Integer discountRate) {
        this.discountRate = discountRate;
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
        Discount discount = (Discount) o;
        if(discount.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, discount.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Discount{" +
            "id=" + id +
            ", companyName='" + companyName + "'" +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            ", code='" + code + "'" +
            ", discountRate='" + discountRate + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            '}';
    }
}
