package com.example.skatespots.security;

import com.example.skatespots.models.Dao.UserDao;
import com.example.skatespots.models.Dao.UserRepository;
import com.example.skatespots.models.users.userBasic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        userBasic user = userRepository.findByUsername(username);
        if (username == null) {
            throw new UsernameNotFoundException(username);
        }

        return new MyUserPrincipal(user);
    }
}
