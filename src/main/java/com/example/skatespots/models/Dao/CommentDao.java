package com.example.skatespots.models.Dao;


import com.example.skatespots.models.comment.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CommentDao extends CrudRepository<Comment, Integer> {
}
