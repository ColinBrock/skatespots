package com.example.skatespots.controllers;

import com.example.skatespots.GeoApiContextSingleton;
import com.example.skatespots.models.Dao.*;
import com.example.skatespots.models.SkateSpot.SkatePark;
import com.example.skatespots.models.comment.Comment;
import com.example.skatespots.models.users.userBasic;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.TravelMode;
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

@Controller
@RequestMapping("")
public class SkateParkController {

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

    @RequestMapping(value = "parklist", method = RequestMethod.GET)
    public String parksLists(Model model) {
        model.addAttribute("parks", skateParkDao.findAll());
        return "parks/Park-List";
    }

    @RequestMapping(value = "addpark", method = RequestMethod.GET)
    public String displayAddParkForm(Model model) {
        model.addAttribute(new SkatePark());
        return "parks/Add-Park";
    }

    @RequestMapping(value = "addpark", method = RequestMethod.POST)
    public String processAddParkForm(@ModelAttribute @Valid SkatePark newPark, Errors errors, Model model) throws InterruptedException, ApiException, IOException {

        GeocodingResult[] results = GeocodingApi.geocode(context.context, newPark.getAddress()).await();
        String[] center = {"38.631238, -90.344870"};
        String[] cords = {results[0].geometry.location.toString()};

        DistanceMatrixApiRequest distanceRequest = DistanceMatrixApi.getDistanceMatrix(context.context, center, cords);
        DistanceMatrix distance = distanceRequest.origins(center)
                .destinations(cords)
                .mode(TravelMode.DRIVING)
                .await();

        if (distance.rows[0].elements[0].distance == null || distance.rows[0].elements[0].distance.inMeters > 48280) {
            model.addAttribute(newPark);
            model.addAttribute("outOfRange", "This is not a valid address");
            return "parks/Add-Park";
        }
        if (errors.hasErrors()) {
            return "parks/Add-Park";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        userBasic User = userDao.findByUsername(user);

        newPark.setUserBasic(User);
        skateParkDao.save(newPark);
        List<SkatePark> parks = User.getParksSubmitted();
        parks.add(newPark);
        User.setParksSubmitted(parks);
        userDao.save(User);

        return "redirect:/parklist";
    }

    @RequestMapping(value = "edit/park/{parkId}", method = RequestMethod.GET)
    public String editParkForm(Model model, @PathVariable int parkId) {
        SkatePark skatePark = skateParkDao.findOne(parkId);
        model.addAttribute(skatePark);
        return "parks/Add-Park";
    }

    @RequestMapping(value = "edit/park/{parkId}", method = RequestMethod.POST)
    public String processEditParkForm(@ModelAttribute @Valid SkatePark newPark, Errors errors, Model model, @PathVariable int parkId) throws InterruptedException, ApiException, IOException {


        GeocodingResult[] results = GeocodingApi.geocode(context.context, newPark.getAddress()).await();
        String[] center = {"38.631238, -90.344870"};
        String[] cords = {results[0].geometry.location.toString()};

        DistanceMatrixApiRequest distanceRequest = DistanceMatrixApi.getDistanceMatrix(context.context, center, cords);
        DistanceMatrix distance = distanceRequest.origins(center)
                .destinations(cords)
                .mode(TravelMode.DRIVING)
                .await();

        if (distance.rows[0].elements[0].distance == null || distance.rows[0].elements[0].distance.inMeters > 48280) {
            model.addAttribute(newPark);
            model.addAttribute("outOfRange", "This is not a valid address");
            return "parks/Add-Park";
        }
        if (errors.hasErrors()) {
            return "parks/Add-Park";
        }
        SkatePark park = skateParkDao.findOne(parkId);
        List<Comment> comments = park.getComments();

        if (comments != null) {
            newPark.setComments(comments);
            park.setComments(null);
        }

        newPark.setUserBasic(skateParkDao.findOne(parkId).getUserBasic());
        newPark.setImgpath(skateParkDao.findOne(parkId).getImgpath());
        skateParkDao.save(newPark);

        for (Comment comment : comments) {
            comment.setPark(newPark);
        }
        commentDao.save(comments);

        skateParkDao.delete(parkId);
        return "redirect:/parklist";
    }

    @RequestMapping(value = "delete/park/{parkId}", method = RequestMethod.GET)
    public String deletePark(Model model, @PathVariable int parkId) {
        skateParkDao.delete(parkId);
        return "redirect:/parklist";
    }

    @RequestMapping(value = "park/{parkId}", method = RequestMethod.GET)
    public String parkDetail(Model model, @PathVariable int parkId) {
        SkatePark aPark = skateParkDao.findOne(parkId);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        userBasic User = userDao.findByUsername(user);

        if (User == aPark.getUserBasic()) {
            model.addAttribute("delete", "delete");
        }
        if (User.getUsername().equals("admin")) {
            model.addAttribute("delete", "delete");
        }


        if (!aPark.getComments().isEmpty()) {
            model.addAttribute("comments", aPark.getComments());
        }

        model.addAttribute("user", User.getUsername());
        model.addAttribute("aPark", aPark);
        model.addAttribute("address", aPark.getAddress());
        return "parks/Park-Detail";
    }

    @RequestMapping(value = "park/{parkId}", method = RequestMethod.POST)
    public String processParkComment(Model model, @PathVariable int parkId, String comment, @RequestParam(required = false) Integer deletecom) {
        SkatePark aPark = skateParkDao.findOne(parkId);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        userBasic User = userDao.findByUsername(user);

        if (comment != null) {

            Comment com = new Comment(comment, User);
            com.setPark(aPark);
            commentDao.save(com);

            List<Comment> coms = aPark.getComments();
            coms.add(com);
        }

        if (deletecom != null) {
            commentDao.delete(deletecom);
        }

        return "redirect:{parkId}";
    }

}
