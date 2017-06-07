package com.example.skatespots.users;

import com.example.skatespots.SkateSpot.skateSpot;

import java.util.ArrayList;

/**
 * Created by chris on 6/6/17.
 */
public class userBasic {
    private String username;

    private ArrayList<skateSpot> spotsSubmitted;

    public String getUsername(){
        return username;
    }
    public void setUsername(String aUsername){
        username = aUsername;
    }

    public ArrayList<skateSpot> getSpotsSubmitted(){
        return spotsSubmitted;
    }
    public void setSpotsSubmitted(skateSpot aSpot){
        spotsSubmitted.add(aSpot);
    }
}
