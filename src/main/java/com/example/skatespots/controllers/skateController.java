package com.example.skatespots.controllers;

import com.example.skatespots.models.SkateSpot.SkatePark;
import com.example.skatespots.models.SkateSpot.SkateSpot;
import com.example.skatespots.models.SkateSpotData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by chris on 5/23/17.
 */
@Controller
@RequestMapping("")
public class skateController {


    @RequestMapping(value = "spotlist", method = RequestMethod.GET)
    public String spotsLists(Model model) {
        model.addAttribute("title", "Skate Spots");
        model.addAttribute("spots", SkateSpotData.getAll());
        return "spots/Spot-List";
    }


    @RequestMapping(value = "parklist", method = RequestMethod.GET)
    public String parksLists(Model model) {
        model.addAttribute("title", "Skate Parks");
        model.addAttribute("parks", SkateSpotData.getAll());
        return "spots/Park-List";
    }


    @RequestMapping(value = "addspot", method = RequestMethod.GET)
    public String displayAddSpotForm(Model model) {
        return "spots/addspot";
    }
    @RequestMapping(value = "addspot", method = RequestMethod.POST)
    public String processAddSpotForm(@ModelAttribute SkateSpot newSpot) {
        SkateSpotData.add(newSpot);
        return  "redirect:spotlist";
    }


    @RequestMapping(value = "addpark", method = RequestMethod.GET)
    public String displayAddParkForm(Model model) {
        return "spots/Add-Park";
    }
    @RequestMapping(value = "addpark", method = RequestMethod.POST)
    public String processAddParkForm(@ModelAttribute SkatePark newPark) {
        SkateSpotData.add(newPark);
        return  "redirect:parklist";
    }


    @RequestMapping(value = "spot/{spotId}", method = RequestMethod.GET)
    public String spotDetail(Model model, @PathVariable int spotId){
        SkateSpot aSpot = SkateSpotData.getById(spotId);
        model.addAttribute("aSpot", aSpot);
        return "spots/Spot-Detail";
    }

}
