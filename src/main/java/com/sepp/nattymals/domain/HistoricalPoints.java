package com.sepp.nattymals.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A HistoricalPoints.
 */
@Entity
@Table(name = "historical_points")
public class HistoricalPoints implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;
    
    @NotNull
    @Min(value = 0)
    @Column(name = "operation_points", nullable = false)
    private Integer operationPoints;
    
    @ManyToOne
    @JoinColumn(name = "pet_owner_id")
    private PetOwner petOwner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOperationPoints() {
        return operationPoints;
    }
    
    public void setOperationPoints(Integer operationPoints) {
        this.operationPoints = operationPoints;
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
        HistoricalPoints historicalPoints = (HistoricalPoints) o;
        if(historicalPoints.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, historicalPoints.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HistoricalPoints{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", operationPoints='" + operationPoints + "'" +
            '}';
    }
}
