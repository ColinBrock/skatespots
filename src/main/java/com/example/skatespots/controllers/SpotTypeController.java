package com.example.skatespots.controllers;

import com.example.skatespots.models.Dao.SkateSpotDao;
import com.example.skatespots.models.Dao.SpotTypeDao;
import com.example.skatespots.models.SkateSpot.SkateSpot;
import com.example.skatespots.models.SpotType.SpotType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 6/23/17.
 */
@Controller
@RequestMapping(value = "spottype")
public class SpotTypeController {

    @Autowired
    private SpotTypeDao spotTypeDao;

    @Autowired
    private SkateSpotDao skateSpotDao;


    @RequestMapping(value = "{spotId}")
    public String viewByType(@PathVariable int spotId, Model model){

        SpotType spotType = spotTypeDao.findOne(spotId);
        List<SkateSpot> spots = spotType.getSpots();
        List<SkateSpot> spotsByType = new ArrayList<>();

        for (SkateSpot spot : spots){
            if (spot.getSpotTypes().contains(spotType)){
                spotsByType.add(spot);
            }
        }
        model.addAttribute("spots", spotsByType);

        return "spots/Spot-List";
    }
}
