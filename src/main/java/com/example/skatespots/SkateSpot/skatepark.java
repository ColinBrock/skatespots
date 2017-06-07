package com.example.skatespots.SkateSpot;

/**
 * Created by chris on 6/6/17.
 */
public class skatepark extends skateSpot {

    private boolean helmet;
    private boolean isCost;
    private double cost;
    private boolean lights;
    private String hours;


    public boolean getHelmet(){
        return helmet;
    }
    public void setHelmet(Boolean aHelmet){
        helmet = aHelmet;
    }

    public boolean getIsCost(){
        return isCost;
    }
    public void setIsCost(Boolean aCost){
        isCost = aCost;
    }

    public double getCost(){
        return cost;
    }
    public void setCost(double aCost){
        cost = aCost;
    }

    public boolean getLights(){
        return lights;
    }
    public void setLights(Boolean aLights){
        lights = aLights;
    }

    public String getHours(){
        return hours;
    }
    public void setHours(String aHours){
        hours = aHours;
    }



}


