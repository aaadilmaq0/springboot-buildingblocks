package com.stacksimplify.restservices.springbootbuildingblocks.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.stacksimplify.restservices.springbootbuildingblocks.entities.User;
import com.stacksimplify.restservices.springbootbuildingblocks.exceptions.UserExistsException;
import com.stacksimplify.restservices.springbootbuildingblocks.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.springbootbuildingblocks.services.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(path = "/users", method = RequestMethod.GET)
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@RequestMapping(path = "/createUser", method= RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder builder) {
		try {
			userService.createUser(user);
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setLocation(builder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
			return new ResponseEntity<Void>(httpHeaders,HttpStatus.CREATED);
		}catch(UserExistsException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@RequestMapping(path = "/users/{id}", method= RequestMethod.GET)
	public Optional<User> getUserById(@PathVariable("id") Long id) {
		try {
			return userService.getUserById(id);
		}
		catch(UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@RequestMapping(path = "/users/{id}", method = RequestMethod.PUT)
	public User updateUserById(@PathVariable("id") Long id, @RequestBody User user) {
		try {
			return userService.updateUserById(id, user);
		}catch(UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@RequestMapping(path = "/users/{id}", method = RequestMethod.DELETE)
	public void deleteUserById(@PathVariable("id") Long id) {
		userService.deleteUserById(id);
	}
	
	@GetMapping("/users/byusername/{username}")
	public User getUserByUsername(@PathVariable("username") String username) {
		return userService.getUserByUsername(username);
	}
}
