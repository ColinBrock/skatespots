package com.example.skatespots.models.SkateSpot;

/**
 * Created by chris on 5/24/17.
 */
public class SkateSpot {

    private String name;
    private String description;
    private String address;
    private String security;
    private int spotId;
    private static int nextId =1;

    //pictures;


    public SkateSpot(){
        spotId = nextId;
        nextId++;
    }

    public SkateSpot(String name, String description, String address, String security) {
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


    public String getSecurity() {
        return security;
    }
    public void setSecurity(String security) {
        this.security = security;
    }

    public int getSpotId() {
        return spotId;
    }
    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }
}
