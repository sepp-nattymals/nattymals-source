package com.sepp.nattymals.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Customer.
 */
@MappedSuperclass
public abstract class Customer extends Actor implements Serializable {

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private Set<PremiumSubscription> premiumSubscriptions = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private Set<Folder> folders = new HashSet<>();

    public Set<PremiumSubscription> getPremiumSubscriptions() {
        return premiumSubscriptions;
    }

    public void setPremiumSubscriptions(Set<PremiumSubscription> premiumSubscriptions) {
        this.premiumSubscriptions = premiumSubscriptions;
    }

    public Set<Folder> getFolders() {
        return folders;
    }

    public void setFolders(Set<Folder> folders) {
        this.folders = folders;
    }

}
