package com.example.skatespots.models;

import com.example.skatespots.models.SkateSpot.SkateSpot;

import java.util.ArrayList;

/**
 * Created by chris on 6/7/17.
 */
public class SkateSpotData {

    static ArrayList<SkateSpot> spots = new ArrayList<>();

    public static ArrayList<SkateSpot> getAll(){
        return spots;
    }

    public static void add(SkateSpot newSpot) {
        spots.add(newSpot);
    }


    public static void remove(int id){
        SkateSpot spotToRemove = getById(id);
        spots.remove(spotToRemove);
    }


    public static SkateSpot getById(int id){

        SkateSpot theSpot = null;

        for (SkateSpot candidateSpot : spots) {
            if (candidateSpot.getSpotId() == id){
                theSpot = candidateSpot;
            }
        }
        return theSpot;
    }

}
