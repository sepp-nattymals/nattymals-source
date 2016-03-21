package com.sepp.nattymals.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * A Actor.
 */
@Entity
@Table(name = "domain")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class DomainEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	 // Equality ---------------------------------------------------------------
	
//	@Override
//	public int hashCode() {
//	        return this.getId().intValue();
//	}
	
	@Override
	public boolean equals(Object other) {
	        boolean result;
	        if (this == other)
	                result = true;
	        else if (other == null)
	                result = false;
	        else if (other instanceof Integer)
	                result = (this.getId() == (Long) other);
	        else if (!this.getClass().isInstance(other))
	                result = false;
	        else
	                result = (this.getId() == ((DomainEntity) other).getId());
	        return result;
	}
}
