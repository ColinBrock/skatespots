package com.example.skatespots.controllers;

import com.example.skatespots.models.Dao.UserDao;
import com.example.skatespots.models.users.userBasic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by chris on 6/14/17.
 */
@Controller
@RequestMapping("")
public class UserController {

        @Autowired
        private UserDao userDao;

    @GetMapping("/login")
    public String login() {
        return "users/index";
    }

    @GetMapping("/")
    public String hello(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        userBasic userBasic = userDao.findByUsername(user);

        model.addAttribute("parks", userBasic.getParksSubmitted());
        model.addAttribute("spots", userBasic.getSpotsSubmitted());

        return "users/Homepage";
    }

        @RequestMapping(value ="signup", method = RequestMethod.GET)
        public String signUpForm(Model model){
            model.addAttribute(new userBasic());
            return "users/Sign-Up";
        }

        @RequestMapping(value = "signup", method = RequestMethod.POST)
        public String processSignUpForm(Model model, @ModelAttribute @Valid userBasic userBasic, Errors errors, String verify) {
            model.addAttribute("userBasic", userBasic);

            if (errors.hasErrors()) {
                model.addAttribute("userBasic", userBasic);
                return "users/Sign-Up";
            }

            ArrayList<userBasic> users = new ArrayList<>();
            Iterator<userBasic> allusers = userDao.findAll().iterator();
            while (allusers.hasNext()){
                userBasic user = allusers.next();
                users.add(user);
            }

            for ( userBasic user : users) {
                if (user.getUsername().equals(userBasic.getUsername())){
                    model.addAttribute("usernametaken", "This username is already taken.");
                    return "users/Sign-Up";
                }
            }

            if (userBasic.getPassword().equals(verify)) {
                model.addAttribute("userBasic", userBasic);
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                userBasic.setPassword(encoder.encode(userBasic.getPassword()));
                userDao.save(userBasic);
                return "redirect:/";
            } else {
                model.addAttribute("userBasic", userBasic);
                model.addAttribute("noMatch", "These passwords do not match");
                userBasic.setPassword("");
                return "users/Sign-Up";
            }

        }
}

