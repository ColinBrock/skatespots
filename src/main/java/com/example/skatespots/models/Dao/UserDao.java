package com.example.skatespots.models.Dao;

import com.example.skatespots.models.users.userBasic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by chris on 6/27/17.
 */

@Repository
@Transactional
public interface UserDao extends CrudRepository<userBasic, Integer>{

    userBasic findByUsername(String username);
}
