package com.example.skatespots.controllers;

import com.example.skatespots.models.Dao.*;
import com.example.skatespots.models.SkateSpot.SkatePark;
import com.example.skatespots.models.SkateSpot.SkateSpot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String processNearMe(Model model, String type, String location) {

        ArrayList<String> locations = new ArrayList<>();

        if (type.equals("spot")) {

            Iterator<SkateSpot> spots = skateSpotDao.findAll().iterator();
            while (spots.hasNext()) {
                String address = spots.next().getAddress();
                locations.add(address);
            }
            model.addAttribute("locations", locations);
            return "allspots/All-Spots";

        } else if (type.equals("park")) {

            Iterator<SkatePark> parks = skateParkDao.findAll().iterator();
            while (parks.hasNext()) {
                String address = parks.next().getAddress();
                locations.add(address);
            }
            model.addAttribute("locations", locations);
            return "allspots/All-Spots";

        } else {

            Iterator<SkateSpot> spots = skateSpotDao.findAll().iterator();
            while (spots.hasNext()) {
                String address = spots.next().getAddress();
                locations.add(address);
            }
            Iterator<SkatePark> parks = skateParkDao.findAll().iterator();
            while (parks.hasNext()) {
                String address = parks.next().getAddress();
                locations.add(address);
            }
            model.addAttribute("locations", locations);
            return "allspots/All-Spots";
        }
    }
}
