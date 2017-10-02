package com.example.skatespots.controllers;

import com.example.skatespots.models.Dao.*;
import com.example.skatespots.models.SkateSpot.SkatePark;
import com.example.skatespots.models.comment.Comment;
import com.example.skatespots.models.users.userBasic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
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
    public String processAddParkForm(@ModelAttribute @Valid SkatePark newPark, Errors errors, Model model) {

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
    public String processEditParkForm(@ModelAttribute @Valid SkatePark newPark, Errors errors, @PathVariable int parkId) {

        if (errors.hasErrors()) {
            return "parks/Add-Park";
        }

        newPark.setUserBasic(skateParkDao.findOne(parkId).getUserBasic());
        newPark.setImgpath(skateParkDao.findOne(parkId).getImgpath());
        skateParkDao.save(newPark);
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

        if (aPark.getComments() != null) {
            model.addAttribute("comments", aPark.getComments());
        }

        model.addAttribute("aPark", aPark);
        model.addAttribute("address", aPark.getAddress());
        return "parks/Park-Detail";
    }

    @RequestMapping(value = "park/{parkId}", method = RequestMethod.POST)
    public String processParkComment(Model model, @PathVariable int parkId, String comment) {
        SkatePark aPark = skateParkDao.findOne(parkId);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        userBasic User = userDao.findByUsername(user);

        Comment com = new Comment(comment, User);
        com.setPark(aPark);
        commentDao.save(com);


        List<Comment> coms = aPark.getComments();
        coms.add(com);

        if (User == aPark.getUserBasic()) {
            model.addAttribute("delete", "delete");
        }
        if (aPark.getComments() != null) {
            model.addAttribute("comments", aPark.getComments());
        }

        model.addAttribute("aPark", aPark);
        model.addAttribute("address", aPark.getAddress());
        return "redirect:{parkId}";
    }

}
