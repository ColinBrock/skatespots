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
    private String notes;
    private Date dateSubmitted;
    private userBasic userSubmitted;

    //pictures;


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


    public String getNotes(){
        return notes;
    }
    public void setNotes(String aNote){
        notes = aNote;
    }

    public Date getDateSubmitted(){
        return dateSubmitted;
    }
    public void setDateSubmitted(Date aDate){
        dateSubmitted = aDate;
    }

    public userBasic getUserSubmitted(){
        return userSubmitted;
    }
    public void setUserSubmitted(userBasic aUser){
        userSubmitted = aUser;
    }



}
