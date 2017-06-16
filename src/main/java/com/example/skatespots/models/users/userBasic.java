package com.example.skatespots.models.users;

import com.example.skatespots.models.SkateSpot.SkateSpot;
import sun.plugin2.message.Message;

import javax.validation.constraints.Size;
import java.util.ArrayList;

/**
 * Created by chris on 6/6/17.
 */
public class userBasic {

    @Size(min=3,max=15, message = "Size must be between 3 and 15 Characters")
    private String username;

    @Size(min = 3, max = 15, message = "Size must be between 3 and 15 Characters")
    private String password;

    private ArrayList<SkateSpot> spotsSubmitted;



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



    public ArrayList<SkateSpot> getSpotsSubmitted(){
        return spotsSubmitted;
    }

    public void setSpotsSubmitted(ArrayList<SkateSpot> spotsSubmitted) {
        this.spotsSubmitted = spotsSubmitted;
    }
}
