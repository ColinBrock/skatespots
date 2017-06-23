package com.example.skatespots.models.SkateSpot;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by chris on 5/24/17.
 */

@Entity
public class SkateSpot {

    @NotNull
    @Size(min=1, message = "Must have a name.")
    private String name;

    @NotNull
    @Size(min=1, message = "Must have a description")
    private String description;

    @NotNull
    @Size(min = 1, message = "Must have an address.")
    private String address;

    private String security;

    @Id
    @GeneratedValue
    private int id;


    public SkateSpot() {}

    public SkateSpot(String name, String description, String address) {
        this.name = name;
        this.description = description;
        this.address = address;
    }


    public String getName() {
        return name;
    }

    public void setName(String aName) {
        name = aName;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String aDescription) {
        description = aDescription;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String anAddress) {
        address = anAddress;
    }


    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


