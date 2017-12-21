package com.example.skatespots.controllers;

import com.example.skatespots.GeoApiContextSingleton;
import com.example.skatespots.models.Dao.*;
import com.example.skatespots.models.SkateSpot.SkateSpot;
import com.example.skatespots.models.SpotType.SpotType;
import com.example.skatespots.models.comment.Comment;
import com.example.skatespots.models.users.userBasic;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
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

    GeoApiContextSingleton context = GeoApiContextSingleton.getInstance();

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
                                     @RequestParam(required = false) int[] spotTypes, Model model) throws InterruptedException, ApiException, IOException {


        GeocodingResult[] results = GeocodingApi.geocode(context.context, newSpot.getAddress()).await();
        String[] center = {"38.631238, -90.344870"};
        String[] cords = {results[0].geometry.location.toString()};

        DistanceMatrixApiRequest distanceRequest = DistanceMatrixApi.getDistanceMatrix(context.context, center, cords);
        DistanceMatrix distance = distanceRequest.origins(center)
                .destinations(cords)
                .mode(TravelMode.DRIVING)
                .await();

        if (distance.rows[0].elements[0].distance == null || distance.rows[0].elements[0].distance.inMeters > 48280) {
            model.addAttribute(newSpot);
            model.addAttribute("outOfRange", "This is not a valid address");
            return "spots/Add-Spot";
        }
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
                                      @RequestParam(required = false) int[] spotTypes, Model model) throws InterruptedException, ApiException, IOException {

        GeocodingResult[] results = GeocodingApi.geocode(context.context, newSpot.getAddress()).await();
        String[] center = {"38.631238, -90.344870"};
        String[] cords = {results[0].geometry.location.toString()};

        DistanceMatrixApiRequest distanceRequest = DistanceMatrixApi.getDistanceMatrix(context.context, center, cords);
        DistanceMatrix distance = distanceRequest.origins(center)
                .destinations(cords)
                .mode(TravelMode.DRIVING)
                .await();

        if (distance.rows[0].elements[0].distance == null || distance.rows[0].elements[0].distance.inMeters > 48280) {
            model.addAttribute(newSpot);
            model.addAttribute("outOfRange", "This is not a valid address");
            return "spots/Add-Spot";
        }
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

            SkateSpot spot = skateSpotDao.findOne(spotId);
            List<Comment> comments = spot.getComments();

            if (comments != null) {
                newSpot.setComments(comments);
                spot.setComments(null);
            }

            newSpot.setUserBasic(skateSpotDao.findOne(spotId).getUserBasic());
            newSpot.setImgpath(skateSpotDao.findOne(spotId).getImgpath());

            skateSpotDao.save(newSpot);
            for (Comment comment : comments) {
                comment.setSpot(newSpot);
            }
            commentDao.save(comments);
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
            }
        if (User.getUsername().equals("admin")) {
            model.addAttribute("delete", "delete");
        }

        if (!aSpot.getComments().isEmpty()) {
            model.addAttribute("comments", aSpot.getComments());
        }

        model.addAttribute("user", User.getUsername());
        model.addAttribute("types", types);
        model.addAttribute("aSpot", aSpot);
        model.addAttribute("address", aSpot.getAddress());
        return "spots/Spot-Detail";
    }

    @RequestMapping(value = "spot/{spotId}", method = RequestMethod.POST)
    public String processSpotComment(Model model, @PathVariable int spotId, String comment, @RequestParam(required = false) Integer deletecom) {

        SkateSpot aSpot = skateSpotDao.findOne(spotId);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        userBasic User = userDao.findByUsername(user);

        if (comment != null) {
            Comment com = new Comment(comment, User);
            com.setSpot(aSpot);
            commentDao.save(com);

            List<Comment> coms = aSpot.getComments();
            coms.add(com);
        }
        if (deletecom != null) {
            commentDao.delete(deletecom);
        }
        return "redirect:{spotId}";
    }

}
