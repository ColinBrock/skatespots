package com.example.skatespots.SkateSpot;

import com.example.skatespots.users.userBasic;

import java.util.Date;

/**
 * Created by chris on 5/24/17.
 */
public class skateSpot {

    private String name;
    private String description;
    private String address;
    private boolean security;
    private int spotId;
    private static int nextId =1;

    //pictures;


    public skateSpot(){
        spotId = nextId;
        nextId++;
    }

    public skateSpot(String name, String description, String address, boolean security) {
        this();
        this.name = name;
        this.description = description;
        this.address = address;
        this.security = security;
    }

    public String getName() {
        return name;
    }
    public void setName(String aName){
        name = aName;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String aDescription){
        description = aDescription;
    }


    public String getAddress(){
        return address;
    }
    public void setAddress(String anAddress){
        address = anAddress;
    }


    public boolean isSecurity() {
        return security;
    }
    public void setSecurity(boolean security) {
        this.security = security;
    }

    public int getSpotId() {
        return spotId;
    }

    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }
}
