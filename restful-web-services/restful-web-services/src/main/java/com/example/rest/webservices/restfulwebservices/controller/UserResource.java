package com.example.rest.webservices.restfulwebservices.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import static  org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.rest.webservices.restfulwebservices.dao.UserDaoService;
import com.example.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.example.rest.webservices.restfulwebservices.model.User;

@RestController
public class UserResource {

	@Autowired
	private UserDaoService service;

	@GetMapping("/users")
	public List<User> retrieveAll() {
		return service.findAll();

	}

	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		User user = service.findOne(id);
		if(user == null) {
			throw new UserNotFoundException("id - "+id);
		}
		
		EntityModel<User> model = EntityModel.of(user);
		WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).retrieveAll());
		model.add(linkToUsers.withRel("all-users"));
		
		WebMvcLinkBuilder linkToCreateUsers = linkTo(methodOn(this.getClass()).create(user));
		model.add(linkToCreateUsers.withRel("createUser"));
		return model ;
	}

	@PostMapping("/users")
	public ResponseEntity<Object> create(@Valid @RequestBody User user) {
		User savedUser = service.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();

	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = service.deleteById(id);
		if(user == null) {
			throw new UserNotFoundException("id - "+id);
		}
		
		
	}

}
