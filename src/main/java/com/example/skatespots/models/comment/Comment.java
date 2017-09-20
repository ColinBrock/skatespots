package com.example.skatespots.models.comment;


import com.example.skatespots.models.SkateSpot.SkatePark;
import com.example.skatespots.models.SkateSpot.SkateSpot;
import com.example.skatespots.models.users.userBasic;

import javax.persistence.Entity;

@Entity
public class Comment {

    private String comment;

    private userBasic user;

    private SkateSpot spot;

    private SkatePark park;

    public Comment() {
    }

    public Comment(String comment, userBasic user) {
        this.comment = comment;
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public userBasic getUser() {
        return user;
    }

    public void setUser(userBasic user) {
        this.user = user;
    }

    public SkateSpot getSpot() {
        return spot;
    }

    public void setSpot(SkateSpot spot) {
        this.spot = spot;
    }

    public SkatePark getPark() {
        return park;
    }

    public void setPark(SkatePark park) {
        this.park = park;
    }
}
