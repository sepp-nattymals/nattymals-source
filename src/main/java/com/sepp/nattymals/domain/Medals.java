package com.sepp.nattymals.domain;


import java.io.Serializable;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

/**
 * A Medals.
 */
@Entity
@Table(name = "medals")
public class Medals implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotNull
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "code", nullable = false)
    private String code;
    
    @NotNull
    @SafeHtml(whitelistType=WhiteListType.NONE)
    @Column(name = "description", nullable = false)
    private String description;
    
    @NotNull
    @Min(value = 0)
    @Column(name = "points", nullable = false)
    private Integer points;
    
    @NotNull
    @Lob
    @Column(name = "icon", nullable = false)
    private byte[] icon;
    
    @Column(name = "icon_content_type", nullable = false)        private String iconContentType;
    
    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "pet_owner_id")
    private PetOwner petOwner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPoints() {
        return points;
    }
    
    public void setPoints(Integer points) {
        this.points = points;
    }

    public byte[] getIcon() {
        return icon;
    }
    
    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public String getIconContentType() {
        return iconContentType;
    }

    public void setIconContentType(String iconContentType) {
        this.iconContentType = iconContentType;
    }

    public PetOwner getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(PetOwner petOwner) {
        this.petOwner = petOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Medals medals = (Medals) o;
        if(medals.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, medals.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Medals{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", code='" + code + "'" +
            ", description='" + description + "'" +
            ", points='" + points + "'" +
            ", icon='" + icon + "'" +
            ", iconContentType='" + iconContentType + "'" +
            '}';
    }
}
