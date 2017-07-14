package com.example.skatespots.models.Dao;

import com.example.skatespots.models.users.LoggedInUser;
import com.example.skatespots.models.users.userBasic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by chris on 7/12/17.
 */

@Repository
@Transactional
public interface LoggedInUserDao extends CrudRepository<LoggedInUser, Integer>{
}
