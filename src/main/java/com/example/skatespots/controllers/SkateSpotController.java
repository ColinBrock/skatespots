package com.example.skatespots.controllers;

import com.example.skatespots.models.Dao.*;
import com.example.skatespots.models.SkateSpot.SkatePark;
import com.example.skatespots.models.SkateSpot.SkateSpot;
import com.example.skatespots.models.SpotType.SpotType;
import com.example.skatespots.models.comment.Comment;
import com.example.skatespots.models.users.userBasic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by chris on 5/23/17.
 */
@Controller
@RequestMapping("")
public class SkateSpotController {

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


    @RequestMapping(value = "spotlist", method = RequestMethod.GET)
    public String spotsLists(Model model) {
        model.addAttribute("spots", skateSpotDao.findAll());
        return "spots/Spot-List";
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

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user = auth.getName();
            userBasic User = userDao.findByUsername(user);

            newSpot.setUserBasic(User);
            List<SkateSpot> spots = User.getSpotsSubmitted();
            spots.add(newSpot);
            User.setSpotsSubmitted(spots);

            for (int spotType : spotTypes) {
                SpotType spot = spotTypeDao.findOne(spotType);
                spot.addItem(newSpot);
            }
            skateSpotDao.save(newSpot);
            userDao.save(User);
            return "redirect:spotlist";
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
            for (int spotType : spotTypes) {
                SpotType spot = spotTypeDao.findOne(spotType);
                spot.addItem(newSpot);
            }

            newSpot.setUserBasic(skateSpotDao.findOne(spotId).getUserBasic());
            newSpot.setImgpath(skateSpotDao.findOne(spotId).getImgpath());
            skateSpotDao.save(newSpot);
            skateSpotDao.delete(spotId);
            return "redirect:/spotlist";
        }
    }

    @RequestMapping(value = "delete/spot/{spotId}", method = RequestMethod.GET)
    public String deleteSpot(Model model, @PathVariable int spotId) {
        skateSpotDao.delete(spotId);
        return "redirect:/spotlist";
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


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        userBasic User = userDao.findByUsername(user);

        if (User == aSpot.getUserBasic()) {
                model.addAttribute("delete", "delete");
            model.addAttribute("deletecomment", "deletecomment");
            }

        if (aSpot.getComments() != null) {
            model.addAttribute("comments", aSpot.getComments());
        }

        model.addAttribute("types", types);
        model.addAttribute("aSpot", aSpot);
        model.addAttribute("address", aSpot.getAddress());
        return "spots/Spot-Detail";
    }

    @RequestMapping(value = "spot/{spotId}", method = RequestMethod.POST)
    public String processSpotComment(Model model, @PathVariable int spotId, String comment) {

        SkateSpot aSpot = skateSpotDao.findOne(spotId);

        List<SpotType> spottypes = aSpot.getSpotTypes();
        StringBuilder types = new StringBuilder();
        for (SpotType type : spottypes) {
            types.append(type.getName());
            types.append(" - ");
        }
        int i = types.lastIndexOf("-");
        types.deleteCharAt(i);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        userBasic User = userDao.findByUsername(user);

        Comment com = new Comment(comment, User);
        com.setSpot(aSpot);
        commentDao.save(com);

        List<Comment> coms = aSpot.getComments();
        coms.add(com);

        if (User == aSpot.getUserBasic()) {
            model.addAttribute("delete", "delete");
        }
        if (aSpot.getComments() != null) {
            model.addAttribute("comments", coms);
        }

        model.addAttribute("types", types);
        model.addAttribute("aSpot", aSpot);
        model.addAttribute("address", aSpot.getAddress());
        return "redirect:{spotId}";
    }

}
