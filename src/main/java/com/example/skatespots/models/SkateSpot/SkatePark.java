package com.example.skatespots.models.SkateSpot;

/**
 * Created by chris on 6/14/17.
 */
public class SkatePark extends SkateSpot {

    private boolean helmet;
    private boolean isCost;
    private double cost;
    private boolean lights;
    private String hours;
    private int spotId;
    private static int nextId =1;

    public SkatePark(){
        spotId = nextId;
        nextId++;
    }

    public SkatePark(String name, String description, String address, String security, boolean helmet,
                     boolean isCost, double cost, boolean lights, String hours) {
        super(name, description, address, security);
        this.helmet = helmet;
        this.isCost = isCost;
        this.cost = cost;
        this.lights = lights;
        this.hours = hours;
    }

    public boolean isHelmet() {
        return helmet;
    }

    public void setHelmet(boolean helmet) {
        this.helmet = helmet;
    }

    public boolean isCost() {
        return isCost;
    }

    public void setCost(boolean cost) {
        isCost = cost;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isLights() {
        return lights;
    }

    public void setLights(boolean lights) {
        this.lights = lights;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    @Override
    public int getSpotId() {
        return spotId;
    }

    @Override
    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }
}