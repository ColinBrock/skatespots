package com.example.skatespots.models.comment;


import com.example.skatespots.models.SkateSpot.SkatePark;
import com.example.skatespots.models.SkateSpot.SkateSpot;
import com.example.skatespots.models.users.userBasic;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String comment;

    @ManyToOne
    private userBasic user;

    @ManyToOne
    private SkateSpot spot;

    @ManyToOne
    private SkatePark park;

    public Comment() {
    }

    public Comment(String comment, userBasic user) {
        this.comment = comment;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
