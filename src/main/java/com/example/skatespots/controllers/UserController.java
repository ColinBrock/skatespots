package com.example.skatespots.controllers;

import com.example.skatespots.models.users.userBasic;
import org.apache.tomcat.jni.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by chris on 6/14/17.
 */
@Controller
@RequestMapping("")
public class UserController {


        @RequestMapping(value ="", method = RequestMethod.GET)
        public String home(){
            return "spots/Home";
        }


        @RequestMapping(value ="signup", method = RequestMethod.GET)
        public String add(Model model){
            model.addAttribute(new userBasic());
            return "spots/Sign-Up";
        }

        @RequestMapping(value = "signup", method = RequestMethod.POST)
        public String add(Model model, @ModelAttribute @Valid userBasic user, Errors errors, String verify) {
            model.addAttribute("user", user);

            if (errors.hasErrors()) {
                model.addAttribute("user", user);
                return "spots/Sign-Up";
            }

            if (user.getPassword().equals(verify)) {
                model.addAttribute("user", user);
                return "spots/Logged-In";
            } else {
                model.addAttribute(user);
                model.addAttribute("noMatch", "These passwords do not match");
                user.setPassword("");
                return "spots/Sign-Up";
            }

        }


    }

