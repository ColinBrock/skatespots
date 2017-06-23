package com.example.skatespots.models.SkateSpot;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by chris on 6/14/17.
 */

@Entity
public class SkatePark {

    @NotNull
    @Size(min=1, message = "Must have a name")
    private String name;

    @NotNull
    @Size(min=1, message = "Must have a description")
    private String description;

    @NotNull
    @Size(min=1, message = "Must have an address")
    private String address;

    @Id
    @GeneratedValue
    private int id;

    private boolean helmet;

    private boolean cost;

    private boolean lights;

    private double price;

    private boolean indoors;

    private String hours;




    public SkatePark(){
    }

    public SkatePark(String name, String description, String address) {
        this.name = name;
        this.description = description;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public boolean isHelmet() {
        return helmet;
    }

    public void setHelmet(boolean helmet) {
        this.helmet = helmet;
    }

    public boolean isCost() {
        return cost;
    }

    public void setCost(boolean cost) {
        this.cost = cost;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isLights() {
        return lights;
    }

    public void setLights(boolean lights) {
        this.lights = lights;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public boolean isIndoors() {
        return indoors;
    }

    public void setIndoors(boolean indoors) {
        this.indoors = indoors;
    }


}