package com.example.skatespots.models;

import com.example.skatespots.SkateSpot.skateSpot;

import java.util.ArrayList;

/**
 * Created by chris on 6/7/17.
 */
public class SkateSpotData {

    static ArrayList<skateSpot> spots = new ArrayList<>();

    public static ArrayList<skateSpot> getAll(){
        return spots;
    }

    public static void add(skateSpot newSpot) {
        spots.add(newSpot);
    }


    public static void remove(int id){
        skateSpot spotToRemove = getById(id);
        spots.remove(spotToRemove);
    }


    public static skateSpot getById(int id){

        skateSpot theSpot = null;

        for (skateSpot candidateSpot : spots) {
            if (candidateSpot.getSpotId() == id){
                theSpot = candidateSpot;
            }
        }
        return theSpot;
    }

}
