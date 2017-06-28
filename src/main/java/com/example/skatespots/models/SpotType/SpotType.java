package com.example.skatespots.models.SpotType;

import com.example.skatespots.models.SkateSpot.SkateSpot;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 6/23/17.
 */

@Entity
public class SpotType {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;

    @ManyToMany
    private List<SkateSpot> spots = new ArrayList<>();

    public SpotType(){}

    public SpotType(String name) {
        this.name = name;
    }

    public void addItem(SkateSpot item){
        spots.add(item);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SkateSpot> getSpots() {
        return spots;
    }

    public int getId() {
        return id;
    }
}
