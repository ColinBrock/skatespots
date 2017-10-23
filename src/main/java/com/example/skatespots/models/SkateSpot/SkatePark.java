package com.example.skatespots.models.SkateSpot;


import com.example.skatespots.models.comment.Comment;
import com.example.skatespots.models.users.userBasic;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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

    private String imgpath;

    @ManyToOne
    private userBasic userBasic;

    private String helmet;

    private String cost;

    private String lights;

    private double price;

    private String indoors;

    private String hours;

    @OneToMany(mappedBy = "park")
    private List<Comment> comments;

    private Double lat;
    private Double lng;

    public SkatePark(){
    }

    public SkatePark(String name, String description, String address, Double lat, Double lng) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
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

    public String getHelmet() {
        return helmet;
    }

    public void setHelmet(String helmet) {
        this.helmet = helmet;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getLights() {
        return lights;
    }

    public void setLights(String lights) {
        this.lights = lights;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIndoors() {
        return indoors;
    }

    public void setIndoors(String indoors) {
        this.indoors = indoors;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
