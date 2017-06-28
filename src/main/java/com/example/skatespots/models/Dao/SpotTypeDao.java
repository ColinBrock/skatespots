package com.example.skatespots.models.Dao;

import com.example.skatespots.models.SpotType.SpotType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by chris on 6/23/17.
 */

@Repository
@Transactional
public interface SpotTypeDao extends CrudRepository<SpotType, Integer>{
}
