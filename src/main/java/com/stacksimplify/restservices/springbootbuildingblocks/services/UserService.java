package com.stacksimplify.restservices.springbootbuildingblocks.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.springbootbuildingblocks.entities.User;
import com.stacksimplify.restservices.springbootbuildingblocks.exceptions.UserExistsException;
import com.stacksimplify.restservices.springbootbuildingblocks.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.springbootbuildingblocks.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public User createUser(User user)throws UserExistsException {
		User user1 = userRepository.findByUsername(user.getUsername());
		if(user1 != null) {
			throw new UserExistsException("User already exists");
		}
		return userRepository.save(user);
	}
	
	public Optional<User> getUserById(Long id)throws UserNotFoundException {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) throw new UserNotFoundException("User not found in the repository");
		return user;
	}
	
	public User updateUserById(Long id, User user)throws UserNotFoundException{
		Optional<User> optionalUser = userRepository.findById(id);
		if(!optionalUser.isPresent()) throw new UserNotFoundException("User not found. Please give correct user id to update");
		user.setId(id);
		return userRepository.save(user);
	}
	
	public void deleteUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Id. User not found");
		userRepository.deleteById(id);
	}
	
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
