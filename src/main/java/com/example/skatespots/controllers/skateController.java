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
@RequestMapping("")
public class skateController {

    static ArrayList<HashMap<String, String>> spots = new ArrayList();

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
    public String processAddSpotForm(HttpServletRequest request) {
        String spotName = request.getParameter("spotName");
        String spotDescription = request.getParameter("spotDescription");
        String spotAddress = request.getParameter("spotAddres");
        String spotSecurity = request.getParameter("security");
        return  "spots/addspot";
    }

    @RequestMapping(value = "addpark", method = RequestMethod.GET)
    public String displayAddParkForm(Model model) {
        return "spots/addpark";
    }

    @RequestMapping(value = "addpark", method = RequestMethod.POST)
    public String processAddParkForm(HttpServletRequest request) {
        String spotName = request.getParameter("spotName");
        String spotDescription = request.getParameter("spotDescription");
        String spotAddress = request.getParameter("spotAddress");

        return  "spots/addpark";
    }

}
