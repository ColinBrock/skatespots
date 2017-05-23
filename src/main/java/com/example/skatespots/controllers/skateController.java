package com.example.skatespots.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chris on 5/23/17.
 */
@Controller
@RequestMapping("spots")
public class skateController {

    static ArrayList<HashMap<String, String>> spots = new ArrayList();

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("title", "My Skatespots");
        return "spots/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddSpotForm(Model model) {
        model.addAttribute("title", "Add spot");
        return "spots/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddSpotForm(HttpServletRequest request) {
        String spotName = request.getParameter("spotName");
        String spotDescription = request.getParameter("spotDescription");
        return  "spots/add";
    }

}
