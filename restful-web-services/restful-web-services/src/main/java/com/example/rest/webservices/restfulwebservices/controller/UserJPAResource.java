package com.example.rest.webservices.restfulwebservices.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
import com.example.rest.webservices.restfulwebservices.dao.UserRepository;
import com.example.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.example.rest.webservices.restfulwebservices.model.User;

@RestController
public class UserJPAResource {

	@Autowired
	private UserDaoService service;
	
	@Autowired
	private UserRepository repository;

	@GetMapping("/jpa/users")
	public List<User> retrieveAll() {
		return repository.findAll();

	}

	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = repository.findById(id);
		if(! user.isPresent()) {
			throw new UserNotFoundException("id - "+id);
		}
		
		EntityModel<User> model = EntityModel.of(user.get());
		WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).retrieveAll());
		model.add(linkToUsers.withRel("all-users"));
		
		WebMvcLinkBuilder linkToCreateUsers = linkTo(methodOn(this.getClass()).create(user.get()));
		model.add(linkToCreateUsers.withRel("createUser"));
		return model ;
	}

	@PostMapping("/jpa/users")
	public ResponseEntity<Object> create(@Valid @RequestBody User user) {
		User savedUser = repository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();

	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		repository.deleteById(id);
		
		
		
	}

}
