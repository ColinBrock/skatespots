package com.example.skatespots.controllers;

import com.example.skatespots.GeoApiContextSingleton;
import com.example.skatespots.models.Dao.*;
import com.example.skatespots.models.SkateSpot.SkatePark;
import com.example.skatespots.models.SkateSpot.SkateSpot;
import com.google.gson.Gson;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

@Controller
@RequestMapping("")
public class AllSpotsController {

    @Autowired
    private SkateSpotDao skateSpotDao;
    @Autowired
    private SkateParkDao skateParkDao;
    @Autowired
    private SpotTypeDao spotTypeDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CommentDao commentDao;

    @RequestMapping(value = "nearme", method = RequestMethod.GET)
    public String nearMe(Model model) {

        return "allspots/All-Spots-Form";
    }


    @RequestMapping(value = "nearme", method = RequestMethod.POST)
    public String processNearMe(Model model, String type, String hidden, int radius) throws InterruptedException, ApiException, IOException {


        String[] locs = hidden.split(",");
        String lat = locs[0];
        String lon = locs[1];
        float latt = Float.parseFloat(lat);
        float lonn = Float.parseFloat(lon);
        float[] location = new float[]{latt, lonn};

        ArrayList<String> stringSpots = new ArrayList<>();
        Gson gson = new Gson();

        GeoApiContextSingleton context = GeoApiContextSingleton.getInstance();

        int miles = 0;
        switch (radius) {
            case 8046:
                miles = 5;
                break;
            case 16093:
                miles = 10;
                break;
            case 32186:
                miles = 20;
                break;
            case 48280:
                miles = 30;
                break;
        }

        if (type.equals("spot")) {

            Iterator<SkateSpot> spots = skateSpotDao.findAll().iterator();
            while (spots.hasNext()) {
                SkateSpot spot = spots.next();
                if (spot.getLat() == null) {
                    GeocodingResult[] results = GeocodingApi.geocode(context.context, spot.getAddress()).await();
                    spot.setLat(results[0].geometry.location.lat);
                    spot.setLng(results[0].geometry.location.lng);
                    skateSpotDao.save(spot);
                }
                SkateSpot y = new SkateSpot(spot.getName(), spot.getDescription(), spot.getAddress(), spot.getLat(), spot.getLng());
                String z = gson.toJson(y);
                stringSpots.add(z);
            }
            model.addAttribute("miles", miles);
            model.addAttribute("type", "Spots");
            model.addAttribute("radius", radius);
            model.addAttribute("location", location);
            model.addAttribute("locations", stringSpots);
            return "allspots/All-Spots";

        } else if (type.equals("park")) {

            Iterator<SkatePark> parks = skateParkDao.findAll().iterator();
            while (parks.hasNext()) {
                SkatePark park = parks.next();
                if (park.getLat() == null) {
                    GeocodingResult[] results = GeocodingApi.geocode(context.context, park.getAddress()).await();
                    park.setLat(results[0].geometry.location.lat);
                    park.setLng(results[0].geometry.location.lng);
                    skateParkDao.save(park);
                }
                SkatePark y = new SkatePark(park.getName(), park.getDescription(), park.getAddress(), park.getLat(), park.getLng());
                String z = gson.toJson(y);
                stringSpots.add(z);
            }
            model.addAttribute("miles", miles);
            model.addAttribute("type", "Parks");
            model.addAttribute("radius", radius);
            model.addAttribute("location", location);
            model.addAttribute("locations", stringSpots);
            return "allspots/All-Spots";
        } else {

            Iterator<SkateSpot> spots = skateSpotDao.findAll().iterator();
            while (spots.hasNext()) {
                SkateSpot spot = spots.next();
                if (spot.getLat() == null) {
                    GeocodingResult[] results = GeocodingApi.geocode(context.context, spot.getAddress()).await();
                    spot.setLat(results[0].geometry.location.lat);
                    spot.setLng(results[0].geometry.location.lng);
                    skateSpotDao.save(spot);
                }
                SkateSpot y = new SkateSpot(spot.getName(), spot.getDescription(), spot.getAddress(), spot.getLat(), spot.getLng());
                String z = gson.toJson(y);
                stringSpots.add(z);
            }
            Iterator<SkatePark> parks = skateParkDao.findAll().iterator();
            while (parks.hasNext()) {
                SkatePark park = parks.next();
                if (park.getLat() == null) {
                    GeocodingResult[] results = GeocodingApi.geocode(context.context, park.getAddress()).await();
                    park.setLat(results[0].geometry.location.lat);
                    park.setLng(results[0].geometry.location.lng);
                    skateParkDao.save(park);
                }
                SkatePark y = new SkatePark(park.getName(), park.getDescription(), park.getAddress(), park.getLat(), park.getLng());
                String z = gson.toJson(y);
                stringSpots.add(z);
            }
            model.addAttribute("miles", miles);
            model.addAttribute("type", "Spots and Parks");
            model.addAttribute("radius", radius);
            model.addAttribute("location", location);
            model.addAttribute("locations", stringSpots);
            return "allspots/All-Spots";
        }
    }
}
