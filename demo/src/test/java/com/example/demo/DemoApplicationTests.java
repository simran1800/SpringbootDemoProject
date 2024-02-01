package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import com.example.demo.controller.UserController;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private UserController controller;

	@MockBean
	private UserRepository repository;


	@Test
	public void createUserTest() {
		User user = new User(1L, "Simi", 21,"female", "Pune");
		when(repository.save(user)).thenReturn(user);
		User result = controller.createUser(user).getBody();
		assertEquals(user, result);
	}

	@Test
	public void getUserByIdTest() {
		Optional<User> user = Optional.of(new User(1L, "Simi", 21,"female", "Pune"));
		when(repository.findById(1L)).thenReturn(user);
		String name_expected = "Simi";
		ResponseEntity<User> result = controller.getUserById(1L);
		assertEquals(name_expected, result.getBody().getName());
	}





}

