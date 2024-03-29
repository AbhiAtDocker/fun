package com.example.test.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import net.bytebuddy.implementation.attribute.AnnotationAppender.Target.OnMethod;

@RestController
public class UserController {

	@Autowired
	private UserDaoService userService;
	
	
	@GetMapping(path="/users")
	public List<User> retrieveAllUser(){
		return userService.findAll(); 
	}
	
	@GetMapping(path="/users/{id}")
	public EntityModel<User> returnUser(@PathVariable Integer id) {
		 User user = userService.findOne(id);
	      if(user==null) {
	    	  throw new UserNotFoundException("id: " +id);
	      }
	      
	      EntityModel model = EntityModel.of(user);
	
	      WebMvcLinkBuilder linkToUsers	 = linkTo(methodOn(this.getClass()).retrieveAllUser());
	      model.add(linkToUsers.withRel("all-users"));
	      
	     return model;
	}
	
	@PostMapping("/users")	
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User saved = userService.save(user);
	
	   URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId()).toUri();
	
	   return ResponseEntity.created(location).build();
	   
	}
	
	
    @DeleteMapping("/users/{id}")
	public User deleteUser(@PathVariable Integer id) {
		
		User user = userService.deleteById(id);
		if(user == null)
			throw new UserNotFoundException("id: " +  id);
		
		return user;
	}
}
