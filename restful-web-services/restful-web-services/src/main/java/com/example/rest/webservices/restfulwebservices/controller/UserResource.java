package com.example.rest.webservices.restfulwebservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest.webservices.restfulwebservices.dao.UserDaoService;
import com.example.rest.webservices.restfulwebservices.model.User;

@RestController
public class UserResource {

	@Autowired
	private UserDaoService service;
	
	
	@GetMapping("/users")
	public List<User> retrieveAll(){
		return service.findAll();
		
	}
	
	@GetMapping("/users/{id}")
	public User retrieveUser(@PathVariable int id) {
		return service.findOne(id);
	}
	
}
