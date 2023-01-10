package com.producthut.service;

import java.util.List;
import java.util.Optional;

import com.producthut.entity.Address;
import com.producthut.entity.CurrentUserSession;
import com.producthut.entity.Customer;
import com.producthut.exception.AddressException;
import com.producthut.exception.LoginException;
import com.producthut.exception.UserException;
import com.producthut.repositiry.AddressRepo;
import com.producthut.repositiry.CurrentUserSessionRepo;
import com.producthut.repositiry.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private CustomerRepo uRepo;

	@Autowired
	private CurrentUserSessionRepo csdao;

	@Override
	public Address addAddress(Address address, String customerKey)
			throws LoginException, UserException, AddressException {

		CurrentUserSession loggedInUser = csdao.findByUuid(customerKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (!loggedInUser.getCustomer()) {
			throw new UserException("Unauthorized Access! Only Customer can make changes");
		}

		Optional<Customer> existingUser = uRepo.findById(loggedInUser.getUserId());

		if (existingUser.isPresent()) {
			Customer customer = existingUser.get();

			customer.setAddresses(address);

			Address savedAdress = addressRepo.save(address);

			return savedAdress;

		} else {
			throw new UserException("User Not Found");
		}
	}

	@Override
	public String deleteAddress(String customerKey) throws LoginException, UserException, AddressException {

		CurrentUserSession loggedInUser = csdao.findByUuid(customerKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (!loggedInUser.getCustomer()) {
			throw new UserException("Unauthorized Access! Only Customer can make changes");
		}

		Optional<Customer> existingUser = uRepo.findById(loggedInUser.getUserId());

		if (existingUser.isPresent()) {
			Customer customer = existingUser.get();

			Address address = customer.getAddresses();

			if (address != null) {

				addressRepo.delete(address);

				return "Card Deleted Succesfully!";
			} else {
				throw new AddressException("No Address found for this user");
			}
		} else {
			throw new UserException("User Not Found");
		}
	}

	@Override
	public Address updateAddress(Address address, String customerKey)
			throws LoginException, UserException, AddressException {

		CurrentUserSession loggedInUser = csdao.findByUuid(customerKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (!loggedInUser.getCustomer()) {
			throw new UserException("Unauthorized Access! Only Customer can make changes");
		}

		Optional<Customer> existingUser = uRepo.findById(loggedInUser.getUserId());

		if (existingUser.isPresent()) {

			Customer customer = existingUser.get();

			Address existingaddress = customer.getAddresses();

			if (existingaddress != null) {

				customer.setAddresses(address);

				Address savedaddress = addressRepo.save(address);

				return savedaddress;
			} else {
				throw new AddressException("No Address found for this user");
			}

		} else {
			throw new UserException("User Not Found");
		}
	}

	@Override
	public Address getAddressByCustomerKey(String customerKey) throws LoginException, UserException, AddressException {

		CurrentUserSession loggedInUser = csdao.findByUuid(customerKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (!loggedInUser.getCustomer()) {
			throw new UserException("Unauthorized Access! Only Customer can make changes");
		}

		Optional<Customer> existingUser = uRepo.findById(loggedInUser.getUserId());

		if (existingUser.isPresent()) {
			Customer customer = existingUser.get();

			Address existingaddress = customer.getAddresses();

			if (existingaddress != null) {

				return existingaddress;
			} else {
				throw new AddressException("No Address found for this user");
			}

		} else {
			throw new UserException("User Not Found");
		}
	}

}


