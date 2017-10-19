package com.example.skatespots.controllers;

import com.example.skatespots.models.Dao.*;
import com.example.skatespots.models.SkateSpot.SkatePark;
import com.example.skatespots.models.SkateSpot.SkateSpot;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

@Controller
@RequestMapping("")
public class NearMeController {

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
    public String processNearMe(Model model, String type, String hidden, int radius) {

        ArrayList<String> locations = new ArrayList<>();

        String[] locs = hidden.split(",");
        String lat = locs[0];
        String lon = locs[1];
        float latt = Float.parseFloat(lat);
        float lonn = Float.parseFloat(lon);
        float[] location = new float[2];
        location = new float[]{latt, lonn};

        ArrayList<String> stringSpots = new ArrayList<>();
        Gson gson = new Gson();

        if (type.equals("spot")) {

            Iterator<SkateSpot> spots = skateSpotDao.findAll().iterator();
            while (spots.hasNext()) {

                SkateSpot x = spots.next();
                SkateSpot y = new SkateSpot(x.getName(), x.getDescription(), x.getAddress());
                String z = gson.toJson(y);
                stringSpots.add(z);
            }

            model.addAttribute("radius", radius);
            model.addAttribute("location", location);
            model.addAttribute("locations", stringSpots);
            return "allspots/All-Spots";

        } else if (type.equals("park")) {

            Iterator<SkatePark> parks = skateParkDao.findAll().iterator();
            while (parks.hasNext()) {
                SkatePark x = parks.next();
                SkatePark y = new SkatePark(x.getName(), x.getDescription(), x.getAddress());
                String z = gson.toJson(y);
                stringSpots.add(z);
            }

            model.addAttribute("radius", radius);
            model.addAttribute("location", location);
            model.addAttribute("locations", stringSpots);
            return "allspots/All-Spots";

        } else {

            Iterator<SkateSpot> spots = skateSpotDao.findAll().iterator();
            while (spots.hasNext()) {
                SkateSpot x = spots.next();
                SkateSpot y = new SkateSpot(x.getName(), x.getDescription(), x.getAddress());
                String z = gson.toJson(y);
                stringSpots.add(z);
            }
            Iterator<SkatePark> parks = skateParkDao.findAll().iterator();
            while (parks.hasNext()) {
                SkatePark x = parks.next();
                SkatePark y = new SkatePark(x.getName(), x.getDescription(), x.getAddress());
                String z = gson.toJson(y);
                stringSpots.add(z);
            }

            model.addAttribute("radius", radius);
            model.addAttribute("location", location);
            model.addAttribute("locations", stringSpots);
            return "allspots/All-Spots";
        }
    }
}
