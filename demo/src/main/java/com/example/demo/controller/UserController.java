package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {

	Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserRepository userRepository;

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		log.info("Request received to fetch all users");
		try {
			List<User> users = new ArrayList<User>();
			userRepository.findAll().forEach(users::add);

			if (users.isEmpty()) {
				log.info("There are no users currently in the database");
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			
			log.info("List of all users in the database: - {}", users);
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			log.info("There was an error in fetching the user list {}", e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
		
		log.info("Request received to fetch a user with id: {}", id);
		Optional<User> userData = userRepository.findById(id);

		if (userData.isPresent()) {
			log.info("The details for user with id {} : {}",id, userData);
			return new ResponseEntity<>(userData.get(), HttpStatus.OK);
		} else {
			log.info("The user with id {} does not exist", id);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		log.info("Request received to add a new user");
		try {
			User userobj = userRepository.save(user);
			log.info("User with id {} is successfully added", userobj.getId());
			return new ResponseEntity<>(userobj, HttpStatus.CREATED);
		} catch (Exception e) {
			log.info("There was an error in adding the user {}", e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		log.info("Request received to modify user with id {}", id);
		Optional<User> userData = userRepository.findById(id);

		if (userData.isPresent()) {
			User userobj = userData.get();
			if (user.getName()!=null) {
				log.info("Trying to update user's name from {} to {}", userobj.getName(), user.getName());
				userobj.setName(user.getName());
				log.info("user's name is updated successfully to {}", userobj.getName());
			}

			if (user.getAge() != 0) {
				log.info("Trying to update user's age from {} to {}", userobj.getAge(), user.getAge());
				userobj.setAge(user.getAge());
				log.info("user's age is updated successfully to {}", userobj.getAge());
			}

			if (user.getGender()!=null) {
				log.info("Trying to update user's gender from {} to {}", userobj.getGender(), user.getGender());
				userobj.setGender(user.getGender());
				log.info("user's gender is updated successfully to {}", userobj.getGender());
			}

			if (user.getCity()!=null) {
				log.info("Trying to update user's city from {} to {}", userobj.getCity(), user.getCity());
				userobj.setCity(user.getCity());
				log.info("user's city is updated successfully to {}", userobj.getCity());
			}
			return new ResponseEntity<>(userRepository.save(userobj), HttpStatus.OK);
		} else {
			log.info("The user with id {} does not exist", id);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
		
		log.info("Request received to delete user with id {}", id);
		try {
			Optional<User> userData = userRepository.findById(id);

			if (userData.isPresent()) {
				userRepository.deleteById(id);
				log.info("User with id {} is deleted", id);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				log.info("The user with id {} does not exist", id);
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			log.info("There was an error in deleting the user {}", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/users")
	public ResponseEntity<HttpStatus> deleteAllUsers() {
		log.info("Request received to delete all the users");
		try {
			userRepository.deleteAll();
			log.info("All the users have been deleted successfully");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			log.info("There was an error in deleting all the users {}", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@Scheduled(cron = "0/15 * * * * *")
	public void fetchDBJob() {
		List<User> users = userRepository.findAll();
		System.out.println("fetch service call in " + new Date().toString());
		System.out.println("no of record fetched : " + users.size());
		log.info("users : {}", users);
	}

}
