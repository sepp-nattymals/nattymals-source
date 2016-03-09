package com.sepp.nattymals.domain;

import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

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
    @Column(name = "company_name", nullable = false)
    private String companyName;
    
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "description")
    private String description;
    
    @NotNull
    @Column(name = "code", nullable = false)
    private String code;
    
    @NotNull
    @Min(value = 0)
    @Column(name = "discount_rate", nullable = false)
    private Integer discountRate;
    
    @NotNull
    @Column(name = "start_date", nullable = false)
    private ZonedDateTime startDate;
    
    @NotNull
    @Column(name = "end_date", nullable = false)
    private ZonedDateTime endDate;
    
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
