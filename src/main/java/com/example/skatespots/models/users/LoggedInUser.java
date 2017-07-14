package com.example.skatespots.models.users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by chris on 7/13/17.
 */

@Entity
public class LoggedInUser {

    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    private userBasic user;

    public LoggedInUser(){

    }

    public LoggedInUser(userBasic user) {
        this.user = user;
    }

    public userBasic getUser() {
        return user;
    }

    public void setUser(userBasic user) {
        this.user = user;
    }
}
