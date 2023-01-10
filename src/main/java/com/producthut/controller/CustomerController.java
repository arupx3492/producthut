package com.producthut.controller;

import java.util.List;

import javax.validation.Valid;

import com.producthut.entity.Customer;
import com.producthut.entity.RegisterUserDto;
import com.producthut.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.producthut.exception.AdminException;
import com.producthut.exception.CartException;
import com.producthut.exception.LoginException;
import com.producthut.exception.ProductException;
import com.producthut.exception.UserException;
import com.producthut.entity.Product;
import com.producthut.service.CartService;

@RestController
@RequestMapping("/customerController")
public class CustomerController {

	@Autowired
	private CustomerService userService;

	@GetMapping("/customers/{username}")
	public ResponseEntity<Customer> getCustomerByUsernameHandler(@PathVariable("username") String username) throws UserException {

		Customer existingUser = userService.findByUserName(username);

		return new ResponseEntity<Customer>(existingUser, HttpStatus.OK);

	}

	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAllCustomersDetailsHandler() throws UserException {

		List<Customer> userList = userService.findAllUsers();

		return new ResponseEntity<List<Customer>>(userList, HttpStatus.OK);

	}

	@PostMapping("/customers")
	public ResponseEntity<Customer> registerCustomerHandler(@Valid @RequestBody RegisterUserDto user) throws UserException {

		Customer savedUser = userService.saveUser(user);

		return new ResponseEntity<Customer>(savedUser, HttpStatus.OK);

	}

	@PutMapping("/users")
	public ResponseEntity<Customer> updateCustomerHandler(@Valid @RequestBody RegisterUserDto user,
			@RequestParam("key") String key) throws UserException, LoginException {

		Customer updatedUser = userService.updateUser(user, key);

		return new ResponseEntity<Customer>(updatedUser, HttpStatus.OK);

	}

	@DeleteMapping("/customers/{username}")
	public ResponseEntity<Customer> deleteCustomerHandler(@PathVariable("username") String username,
			@RequestParam String customerKey) throws UserException, LoginException {

		Customer updatedUser = userService.deleteUser(username, customerKey);

		return new ResponseEntity<Customer>(updatedUser, HttpStatus.OK);

	}

}
