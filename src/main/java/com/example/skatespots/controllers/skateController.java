package com.example.skatespots.controllers;

import com.example.skatespots.models.Dao.*;
import com.example.skatespots.models.SkateSpot.SkatePark;
import com.example.skatespots.models.SkateSpot.SkateSpot;
import com.example.skatespots.models.SpotType.SpotType;
import com.example.skatespots.models.users.LoggedInUser;
import com.example.skatespots.models.users.userBasic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Array;
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
        Iterable<LoggedInUser> loggedInUser = loggedInUserDao.findAll();
        LoggedInUser currentuser = loggedInUser.iterator().next();
        newSpot.setUserBasic(currentuser.getUser());
        skateSpotDao.save(newSpot);

        model.addAttribute("types", types);
        return  "redirect:spotlist";
    }

    @RequestMapping(value = "edit/spot/{spotId}", method = RequestMethod.GET)
    public String editSpotForm(Model model, @PathVariable int spotId){
        SkateSpot skateSpot = skateSpotDao.findOne(spotId);

        model.addAttribute(skateSpot);

        return "spots/Add-Spot";
    }

    @RequestMapping(value = "edit/spot/{spotId}", method = RequestMethod.POST)
    public String processEditSpotForm(@ModelAttribute @Valid SkateSpot newSpot, Errors errors, @PathVariable int spotId,
                                      @RequestParam int[] spotTypes) {

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

        newSpot.setUserBasic(skateSpotDao.findOne(spotId).getUserBasic());
        newSpot.setImgpath(skateSpotDao.findOne(spotId).getImgpath());
        skateSpotDao.save(newSpot);
        skateSpotDao.delete(spotId);

        return  "redirect:/spotlist";
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
        Iterable<LoggedInUser> loggedInUser = loggedInUserDao.findAll();
        LoggedInUser currentuser = loggedInUser.iterator().next();
        newPark.setUserBasic(currentuser.getUser());
        skateParkDao.save(newPark);
        userBasic user = userDao.findOne(currentuser.getUser().getId());
        List parks = user.getParksSubmitted();
        parks.add(newPark);
        userDao.save(user);

        return  "redirect:parklist";
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
        skateParkDao.save(newPark);
        skateParkDao.delete(parkId);

        return  "redirect:/parklist";
    }


    @RequestMapping(value = "spot/{spotId}", method = RequestMethod.GET)
    public String spotDetail(Model model, @PathVariable int spotId){
        SkateSpot aSpot = skateSpotDao.findOne(spotId);
        model.addAttribute("aSpot", aSpot);
        model.addAttribute("address", aSpot.getAddress());
        return "spots/Spot-Detail";
    }

    @RequestMapping(value = "park/{parkId}", method = RequestMethod.GET)
    public String parkDetail(Model model, @PathVariable int parkId){
        SkatePark aPark = skateParkDao.findOne(parkId);
        model.addAttribute("aPark", aPark);
        model.addAttribute("address", aPark.getAddress());

        return "parks/Park-Detail";
    }

}
