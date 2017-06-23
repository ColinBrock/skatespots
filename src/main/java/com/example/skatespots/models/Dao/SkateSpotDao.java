package com.example.skatespots.models.Dao;

import com.example.skatespots.models.SkateSpot.SkateSpot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by chris on 6/22/17.
 */

@Repository
@Transactional
public interface SkateSpotDao extends CrudRepository<SkateSpot, Integer>{
}
