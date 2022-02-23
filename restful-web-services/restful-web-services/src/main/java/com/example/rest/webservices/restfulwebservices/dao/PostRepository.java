package com.example.rest.webservices.restfulwebservices.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rest.webservices.restfulwebservices.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

}
