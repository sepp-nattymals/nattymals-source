package com.sepp.nattymals.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Administrator.
 */
@Entity
@Table(name = "administrator")
public class Administrator extends Actor implements Serializable {

    @OneToMany(mappedBy = "administrator")
    @JsonIgnore
    private Set<Discount> discounts = new HashSet<>();

    @OneToMany(mappedBy = "administrator")
    @JsonIgnore
    private Set<Announcement> announcements = new HashSet<>();

    public Set<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Set<Discount> discounts) {
        this.discounts = discounts;
    }

    public Set<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Set<Announcement> announcements) {
        this.announcements = announcements;
    }

}
