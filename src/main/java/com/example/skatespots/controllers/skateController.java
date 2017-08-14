package com.example.skatespots.controllers;

import com.example.skatespots.models.Dao.*;
import com.example.skatespots.models.SkateSpot.SkatePark;
import com.example.skatespots.models.SkateSpot.SkateSpot;
import com.example.skatespots.models.SpotType.SpotType;
import com.example.skatespots.models.users.LoggedInUser;
import com.example.skatespots.models.users.userBasic;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.thymeleaf.util.StringUtils.concat;

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
    @Autowired
    private LoggedInUserDao loggedInUserDao;
    @Autowired
    private UserDao userDao;


    @RequestMapping(value = "spotlist", method = RequestMethod.GET)
    public String spotsLists(Model model) {
        model.addAttribute("spots", skateSpotDao.findAll());
        return "spots/Spot-List";
    }

    @RequestMapping(value = "parklist", method = RequestMethod.GET)
    public String parksLists(Model model) {
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
                                     @RequestParam(required = false) int[] spotTypes, Model model) {

        if (errors.hasErrors()) {
            return "spots/Add-Spot";
        }
        if (spotTypes == null) {
            model.addAttribute(newSpot);
            model.addAttribute("check", "Must Select a Spot Type");
            return "spots/Add-Spot";
        } else {
            List<SpotType> types = new ArrayList<>();
            for (int spotType : spotTypes) {
                SpotType spot = spotTypeDao.findOne(spotType);
                spot.addItem(newSpot);
                types.add(spot);
                newSpot.setSpotTypes(types);
            }
        }
        Iterable<LoggedInUser> loggedInUser = loggedInUserDao.findAll();
        if (loggedInUser.iterator().hasNext()) {
            LoggedInUser currentuser = loggedInUser.iterator().next();
            newSpot.setUserBasic(currentuser.getUser());
            skateSpotDao.save(newSpot);
            List<SkateSpot> spots = currentuser.getUser().getSpotsSubmitted();
            spots.add(newSpot);
            currentuser.getUser().setSpotsSubmitted(spots);
            return "redirect:spotlist";
        } else {
            model.addAttribute("mustlogin", "You must login before submitting a Spot or Park");
            return "spots/Add-Spot";
        }
    }

    @RequestMapping(value = "edit/spot/{spotId}", method = RequestMethod.GET)
    public String editSpotForm(Model model, @PathVariable int spotId){
        SkateSpot skateSpot = skateSpotDao.findOne(spotId);
        model.addAttribute(skateSpot);
        return "spots/Add-Spot";
    }

    @RequestMapping(value = "edit/spot/{spotId}", method = RequestMethod.POST)
    public String processEditSpotForm(@ModelAttribute @Valid SkateSpot newSpot, Errors errors, @PathVariable int spotId,
                                      @RequestParam(required = false) int[] spotTypes, Model model) {

        if (errors.hasErrors()) {
            return "spots/Add-Spot";
        }
        if (spotTypes == null) {
            model.addAttribute(newSpot);
            model.addAttribute("check", "Must Select a Spot Type");
            return "spots/Add-Spot";
        } else {
            List<SpotType> types = new ArrayList<>();
            for (int spotType : spotTypes) {
                SpotType spot = spotTypeDao.findOne(spotType);
                spot.addItem(newSpot);
                types.add(spot);
                newSpot.setSpotTypes(types);
            }
        }
        newSpot.setUserBasic(skateSpotDao.findOne(spotId).getUserBasic());
        newSpot.setImgpath(skateSpotDao.findOne(spotId).getImgpath());
        skateSpotDao.save(newSpot);
        skateSpotDao.delete(spotId);
        return  "redirect:/spotlist";
    }

    @RequestMapping(value = "delete/spot/{spotId}", method = RequestMethod.GET)
    public String deleteSpot(Model model, @PathVariable int spotId) {
        skateSpotDao.delete(spotId);
        return "redirect:/spotlist";
    }

    @RequestMapping(value = "addpark", method = RequestMethod.GET)
    public String displayAddParkForm(Model model) {
        model.addAttribute(new SkatePark());
        return "parks/Add-Park";
    }

    @RequestMapping(value = "addpark", method = RequestMethod.POST)
    public String processAddParkForm(@ModelAttribute @Valid SkatePark newPark, Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "parks/Add-Park";
        }
        Iterable<LoggedInUser> loggedInUser = loggedInUserDao.findAll();

        if (loggedInUser.iterator().hasNext()) {
            LoggedInUser currentuser = loggedInUser.iterator().next();
            newPark.setUserBasic(currentuser.getUser());
            skateParkDao.save(newPark);
            userBasic user = userDao.findOne(currentuser.getUser().getId());
            List<SkatePark> parks = user.getParksSubmitted();
            parks.add(newPark);
            user.setParksSubmitted(parks);
            userDao.save(user);
        } else {
            model.addAttribute("mustlogin", "You must login before submitting a Spot or Park");
            return "parks/Add-Park";
        }
        return "redirect:/parklist";
    }

    @RequestMapping(value = "edit/park/{parkId}", method = RequestMethod.GET)
    public String editParkForm(Model model, @PathVariable int parkId){
        SkatePark skatePark = skateParkDao.findOne(parkId);
        model.addAttribute(skatePark);
        return "parks/Add-Park";
    }

    @RequestMapping(value = "edit/park/{parkId}", method = RequestMethod.POST)
    public String processEditParkForm(@ModelAttribute @Valid SkatePark newPark, Errors errors, @PathVariable int parkId) {

        if (errors.hasErrors()) {
            return "parks/Add-Park";
        }

        newPark.setUserBasic(skateParkDao.findOne(parkId).getUserBasic());
        newPark.setImgpath(skateParkDao.findOne(parkId).getImgpath());
        skateParkDao.save(newPark);
        skateParkDao.delete(parkId);
        return  "redirect:/parklist";
    }

    @RequestMapping(value = "delete/park/{parkId}", method = RequestMethod.GET)
    public String deletePark(Model model, @PathVariable int parkId) {
        skateParkDao.delete(parkId);
        return "redirect:/parklist";
    }


    @RequestMapping(value = "spot/{spotId}", method = RequestMethod.GET)
    public String spotDetail(Model model, @PathVariable int spotId){
        SkateSpot aSpot = skateSpotDao.findOne(spotId);
        List<SpotType> spottypes = aSpot.getSpotTypes();

        StringBuilder types = new StringBuilder();
        for (SpotType type : spottypes) {
            types.append(type.getName());
            types.append(" - ");
        }
        int i = types.lastIndexOf("-");
        types.deleteCharAt(i);

        Iterator<LoggedInUser> loggedin = loggedInUserDao.findAll().iterator();
        if (loggedin.hasNext()) {
            userBasic user = loggedin.next().getUser();
            if (user == aSpot.getUserBasic()) {
                model.addAttribute("delete", "delete");
            }
        }

        model.addAttribute("types", types);
        model.addAttribute("aSpot", aSpot);
        model.addAttribute("address", aSpot.getAddress());
        return "spots/Spot-Detail";
    }

    @RequestMapping(value = "park/{parkId}", method = RequestMethod.GET)
    public String parkDetail(Model model, @PathVariable int parkId){
        SkatePark aPark = skateParkDao.findOne(parkId);

        Iterator<LoggedInUser> loggedin = loggedInUserDao.findAll().iterator();
        if (loggedin.hasNext()) {
            userBasic user = loggedin.next().getUser();
            if (user == aPark.getUserBasic()) {
                model.addAttribute("delete", "delete");
            }
        }
        model.addAttribute("aPark", aPark);
        model.addAttribute("address", aPark.getAddress());
        return "parks/Park-Detail";
    }

    @RequestMapping(value = "nearme", method = RequestMethod.GET)
    public String nearMe(Model model) {
        ArrayList<String> locations = new ArrayList<>();

        ArrayList<SkateSpot> jsonSpots = new ArrayList<>();
        Gson gson = new Gson();


        Iterator<SkateSpot> spots = skateSpotDao.findAll().iterator();
        while (spots.hasNext()) {
            SkateSpot spot = spots.next();
            locations.add(spot.getAddress());
            SkateSpot skatespot = new SkateSpot(spot.getName(), spot.getDescription(), spot.getAddress());
            jsonSpots.add(skatespot);
        }

        String json = gson.toJson(jsonSpots);
        System.out.println(json);
        model.addAttribute("json", json);
        model.addAttribute("locations", locations);
        model.addAttribute("spots", spots);
        return "allspots/All-Spots";
    }

   /* @RequestMapping(value = "nearme", method = RequestMethod.POST)
    public String processNearMe(Model model){
        return "All-Spots";
    }*/

}
