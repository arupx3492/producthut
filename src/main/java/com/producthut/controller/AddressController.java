package com.producthut.controller;

import javax.validation.Valid;

import com.producthut.entity.Address;
import com.producthut.exception.AddressException;
import com.producthut.exception.LoginException;
import com.producthut.exception.UserException;
import com.producthut.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/addressController")
public class AddressController {

	@Autowired
	private AddressService aservice;

	@PostMapping("/address")
	public ResponseEntity<Address> addAddressHandler(@Valid @RequestBody Address address,
													 @RequestParam String customerKey) throws LoginException, UserException, AddressException {

		Address savedAddress = aservice.addAddress(address, customerKey);

		return new ResponseEntity<Address>(savedAddress, HttpStatus.OK);

	}

	@PutMapping("/address")
	public ResponseEntity<Address> updateAddressHandler(@Valid @RequestBody Address address,
			@RequestParam String customerKey) throws LoginException, UserException, AddressException {

		Address savedAddress = aservice.updateAddress(address, customerKey);

		return new ResponseEntity<Address>(savedAddress, HttpStatus.OK);

	}

	@DeleteMapping("/address")
	public ResponseEntity<String> deleteAddressHandler(@RequestParam String customerKey)
			throws LoginException, UserException, AddressException {

		String res = aservice.deleteAddress(customerKey);

		return new ResponseEntity<String>(res, HttpStatus.OK);

	}

	@GetMapping("/address")
	public ResponseEntity<Address> getAddressByCustomerKeyHandler(@RequestParam String customerKey)
			throws LoginException, UserException, AddressException {

		Address savedAddress = aservice.getAddressByCustomerKey(customerKey);

		return new ResponseEntity<Address>(savedAddress, HttpStatus.OK);

	}

}
