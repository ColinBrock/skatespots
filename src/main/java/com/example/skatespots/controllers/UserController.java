package com.example.skatespots.controllers;

import com.example.skatespots.models.Dao.LoggedInUserDao;
import com.example.skatespots.models.Dao.UserDao;
import com.example.skatespots.models.users.LoggedInUser;
import com.example.skatespots.models.users.userBasic;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

        @Autowired
        private LoggedInUserDao loggedInUserDao;


        @RequestMapping(value ="", method = RequestMethod.GET)
        public String home(Model model){

            Iterable<LoggedInUser> loggedIn = loggedInUserDao.findAll();
                Iterator<LoggedInUser> loggedInUserIterator = loggedIn.iterator();

                if (loggedInUserIterator.hasNext()) {
                    LoggedInUser user = loggedInUserIterator.next();
                    userBasic userBasic = user.getUser();
                    model.addAttribute(userBasic);
                    return "users/Logged-In";

                } else {
                    return "users/Home";
                }
        }

        @RequestMapping(value ="", method = RequestMethod.POST)
        public String homeLoggedIn(Model model, @RequestParam String username,@RequestParam String password ){

            Iterable<userBasic> allusers = userDao.findAll();
            for (userBasic user : allusers) {
                if (user.getUsername().equals(username)) {
                    if (user.getPassword().equals(password)) {

                        LoggedInUser loggedInUser = new LoggedInUser(user);
                        loggedInUserDao.save(loggedInUser);
                        model.addAttribute(user);
                        return "users/Logged-In";

                    } else {
                        return "users/Home";
                    }
                } else {
                    return "users/Home";
                }
            }
            return "users/Home";
        }

        @RequestMapping(value = "signout", method = RequestMethod.GET)
        public String userSignOut(){

            loggedInUserDao.deleteAll();
            return "redirect:/";
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

            if (userBasic.getPassword().equals(verify)) {
                model.addAttribute("userBasic", userBasic);
                userDao.save(userBasic);
                LoggedInUser loggedInUser = new LoggedInUser(userBasic);
                loggedInUserDao.save(loggedInUser);

                return "users/Logged-In";

            } else {
                model.addAttribute("userBasic", userBasic);
                model.addAttribute("noMatch", "These passwords do not match");
                userBasic.setPassword("");
                return "users/Sign-Up";
            }

        }
}

