package com.producthut.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.producthut.entity.*;
import com.producthut.exception.LoginException;
import com.producthut.exception.UserException;
import com.producthut.repositiry.CartRepo;
import com.producthut.repositiry.CurrentUserSessionRepo;
import com.producthut.repositiry.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepo uRepo;

	@Autowired
	private CurrentUserSessionRepo csdao;

	@Autowired
	private CartRepo cartRepo;

	@Override
	public Customer saveUser(RegisterUserDto userdto) throws UserException {

		Customer existingUserName = uRepo.findByUserName(userdto.getUserName());
		Customer existingUserEmail = uRepo.findByEmail(userdto.getEmail());

		if (existingUserName != null)
			throw new UserException("Username already exists " + userdto.getUserName());

		if (existingUserEmail != null)
			throw new UserException("User email exists " + userdto.getEmail());

		Cart cart = new Cart();
		cart.setCartValue(0);
		cart.setProducts(new HashMap<Product, Integer>());

		Customer user = new Customer(userdto);

		user.setCart(cart);
		cart.setCustomer(user);
		Customer savedCustomer = uRepo.save(user);

		return savedCustomer;
	}

	@Override
	public Customer updateUser(RegisterUserDto userdto, String customerKey) throws UserException, LoginException {

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

			Customer user = new Customer(userdto);

			user.setUserLoginId(customer.getUserLoginId());
			user.setAddresses(customer.getAddresses());
			user.setCart(customer.getCart());
			user.setOrders(customer.getOrders());
			user.setUserCards(user.getUserCards());

			Customer updatedUser = uRepo.save(user);

			return updatedUser;

		} else {
			throw new UserException("User Not Found");
		}

	}

	@Override
	public Customer deleteUser(String username, String customerKey) throws UserException, LoginException {

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

			uRepo.delete(customer);

			return customer;

		} else {
			throw new UserException("User Not Found");
		}
	}

	@Override
	public Customer findByUserLoginId(Integer userLoginId) throws UserException {

		Optional<Customer> existingUser = uRepo.findById(userLoginId);

		if (existingUser.isPresent())
			return existingUser.get();
		else
			throw new UserException("User does not exists with this userLoginId " + userLoginId);

	}

	@Override
	public Customer findByEmail(String email) throws UserException {

		Customer existingUser = uRepo.findByEmail(email);

		if (existingUser != null)
			return existingUser;
		else
			throw new UserException("User does not exists with this email " + email);

	}

	@Override
	public Customer findByUserName(String userName) throws UserException {
		Customer existingUser = uRepo.findByUserName(userName);

		if (existingUser != null)
			return existingUser;
		else
			throw new UserException("User does not exists with this userName " + userName);
	}

	@Override
	public List<Customer> findAllUsers() throws UserException {

		List<Customer> users = uRepo.findAll();

		if (users.isEmpty())
			throw new UserException("No Users Found");

		return users;
	}

	@Override
	public Customer findByUserNameOrEmail(String userName, String email) throws UserException {

		Customer existingUser = uRepo.findByUserNameOrEmail(userName, email);

		if (existingUser != null)
			return existingUser;
		else
			throw new UserException("User does not exists with this userName or email " + userName + ", " + email);
	}
}
