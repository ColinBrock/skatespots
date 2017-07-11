package com.example.skatespots.models.SkateSpot;

import com.example.skatespots.models.SpotType.SpotType;
import com.example.skatespots.models.users.userBasic;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by chris on 5/24/17.
 */

@Entity
public class SkateSpot {

    @Id
    @GeneratedValue
    private int id;

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

    private String imgpath;

    @ManyToMany(mappedBy = "spots")
    private List<SpotType> spotTypes;

    @ManyToOne
    private userBasic userBasic;

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

    public List<SpotType> getSpotTypes() {
        return spotTypes;
    }

    public void setSpotTypes(List<SpotType> spotTypes) {
        this.spotTypes = spotTypes;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public com.example.skatespots.models.users.userBasic getUserBasic() {
        return userBasic;
    }

    public void setUserBasic(com.example.skatespots.models.users.userBasic userBasic) {
        this.userBasic = userBasic;
    }
}


