package com.example.skatespots.models.users;

import com.example.skatespots.models.SkateSpot.SkatePark;
import com.example.skatespots.models.SkateSpot.SkateSpot;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 6/6/17.
 */

@Entity
public class userBasic {

    @Id
    @GeneratedValue
    private int id;

    @Size(min=3,max=15, message = "Size must be between 3 and 15 Characters")
    private String username;

    @Size(min = 3, max = 15, message = "Size must be between 3 and 15 Characters")
    private String password;


    @OneToMany
    @JoinColumn(name = "user_basic_id")
    private List<SkateSpot> spotsSubmitted = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_basic_id")
    private List<SkatePark> parksSubmitted = new ArrayList<>();

    public userBasic(){}

    public userBasic(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String aUsername){
        username = aUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<SkateSpot> getSpotsSubmitted() {
        return spotsSubmitted;
    }

    public void setSpotsSubmitted(List<SkateSpot> spotsSubmitted) {
        this.spotsSubmitted = spotsSubmitted;
    }

    public List<SkatePark> getParksSubmitted() {
        return parksSubmitted;
    }

    public void setParksSubmitted(List<SkatePark> parksSubmitted) {
        this.parksSubmitted = parksSubmitted;
    }
}
