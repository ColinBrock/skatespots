package com.example.skatespots.controllers;

import com.example.skatespots.SkateSpot.skateSpot;
import com.example.skatespots.models.SkateSpotData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.skatespots.models.SkateSpotData.add;

/**
 * Created by chris on 5/23/17.
 */
@Controller
@RequestMapping("")
public class skateController {


    @RequestMapping(value = "")
    public String index(Model model) {
        return "spots/index";
    }

    @RequestMapping(value = "spotlist", method = RequestMethod.GET)
    public String spotsLists(Model model) {
        return "spots/spotlist";
    }



    @RequestMapping(value = "addspot", method = RequestMethod.GET)
    public String displayAddSpotForm(Model model) {
        return "spots/addspot";
    }

    @RequestMapping(value = "addspot", method = RequestMethod.POST)
    public String processAddSpotForm(@ModelAttribute skateSpot newSpot) {
        SkateSpotData.add(newSpot);
        return  "spots/addspot";
    }



    @RequestMapping(value = "addpark", method = RequestMethod.GET)
    public String displayAddParkForm(Model model) {
        return "spots/addpark";
    }

    @RequestMapping(value = "addpark", method = RequestMethod.POST)
    public String processAddParkForm(Model model) {
        return  "spots/addpark";
    }

}
