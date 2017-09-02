package com.example.skatespots.security;

import com.example.skatespots.models.Dao.UserDao;
import com.example.skatespots.models.users.userBasic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.ArrayList;
import java.util.Iterator;

import static org.hibernate.criterion.Restrictions.and;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDao userDao;

        
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/home", "/spotlist", "/parklist", "/signup", "/nearme").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        ArrayList<userBasic> usersArray = new ArrayList<>();
        Iterable<userBasic> users = userDao.findAll();
        Iterator<userBasic> usersIterator = users.iterator();
        if (usersIterator.hasNext()) {
            usersArray.add(usersIterator.next());
        }

        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");

    }
}

