package com.example.skatespots.controllers;

import com.example.skatespots.models.Dao.SkateParkDao;
import com.example.skatespots.models.Dao.SkateSpotDao;
import com.example.skatespots.models.Dao.SpotTypeDao;
import com.example.skatespots.models.SkateSpot.SkatePark;
import com.example.skatespots.models.SkateSpot.SkateSpot;
import com.example.skatespots.models.SpotType.SpotType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 5/23/17.
 */
@Controller
@RequestMapping("")
public class skateController {

    @Autowired
    private SkateSpotDao skateSpotDao;
    @Autowired
    private SkateParkDao skateParkDao;
    @Autowired
    private SpotTypeDao spotTypeDao;


    @RequestMapping(value = "spotlist", method = RequestMethod.GET)
    public String spotsLists(Model model) {

        model.addAttribute("spots", skateSpotDao.findAll());
        return "spots/Spot-List";
    }

    @RequestMapping(value = "parklist", method = RequestMethod.GET)
    public String parksLists(Model model) {

        model.addAttribute("title", "Skate Parks");
        model.addAttribute("parks", skateParkDao.findAll());
        return "parks/Park-List";
    }

    @RequestMapping(value = "addspot", method = RequestMethod.GET)
    public String displayAddSpotForm(Model model) {
        model.addAttribute(new SkateSpot());
        model.addAttribute("spotType", skateSpotDao.findAll());
        return "spots/Add-Spot";
    }

    @RequestMapping(value = "addspot", method = RequestMethod.POST)
    public String processAddSpotForm(@ModelAttribute @Valid SkateSpot newSpot, Errors errors,
                                     @RequestParam int[] spotTypes, Model model) {

        if (errors.hasErrors()) {
            return "spots/Add-Spot";
        }

        List<SpotType> types = new ArrayList<>();

        for (int spotType : spotTypes){
            SpotType spot  = spotTypeDao.findOne(spotType);
            spot.addItem(newSpot);
            types.add(spot);
        }
        newSpot.setSpotTypes(types);
        skateSpotDao.save(newSpot);

        model.addAttribute("types", types);
        return  "redirect:spotlist";
    }

    @RequestMapping(value = "addpark", method = RequestMethod.GET)
    public String displayAddParkForm(Model model) {
        model.addAttribute(new SkatePark());
        return "parks/Add-Park";
    }

    @RequestMapping(value = "addpark", method = RequestMethod.POST)
    public String processAddParkForm(@ModelAttribute @Valid SkatePark newPark, Errors errors) {

        if (errors.hasErrors()) {
            return "parks/Add-Park";
        }

        skateParkDao.save(newPark);
        return  "redirect:parklist";
    }


    @RequestMapping(value = "spot/{spotId}", method = RequestMethod.GET)
    public String spotDetail(Model model, @PathVariable int spotId){
        SkateSpot aSpot = skateSpotDao.findOne(spotId);
        model.addAttribute("aSpot", aSpot);
        return "spots/Spot-Detail";
    }

    @RequestMapping(value = "park/{parkId}", method = RequestMethod.GET)
    public String parkDetail(Model model, @PathVariable int parkId){
        SkatePark aPark = skateParkDao.findOne(parkId);
        model.addAttribute("aPark", aPark);
        return "parks/Park-Detail";
    }

}
