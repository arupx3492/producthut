package com.producthut.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.producthut.entity.*;
import com.producthut.exception.LoginException;
import com.producthut.repositiry.AdminRepo;
import com.producthut.repositiry.CurrentUserSessionRepo;
import com.producthut.repositiry.CustomerRepo;
import com.producthut.repositiry.SellerRepo;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private AdminRepo adminRepo;

	@Autowired
	private SellerRepo sellerRepo;

	@Autowired
	private CustomerRepo userRepo;

	@Autowired
	private CurrentUserSessionRepo currUserSession;

	@Override
	public String loginAdmin(AdminDto admin) throws LoginException {

		Admin existingUser = adminRepo.findByAdminUsername(admin.getAdminUsername());

		if (existingUser == null)
			throw new LoginException("Invalid credentials. Username does not exist " + admin.getAdminUsername());

		Optional<CurrentUserSession> validCustomerSessionOpt = currUserSession.findById(existingUser.getAdminId());

		if (validCustomerSessionOpt.isPresent()) {

			throw new LoginException("User already Logged In with this username");

		}

		if (existingUser.getAdminPassword().equals(admin.getAdminPassword())) {

			String key = RandomString.make(6);

			Boolean isAdmin = true;
			Boolean isSeller = false;
			Boolean isCustomer = false;

			CurrentUserSession currentUserSession = new CurrentUserSession(existingUser.getAdminId(), key, isAdmin,
					isSeller, isCustomer, LocalDateTime.now());

			currUserSession.save(currentUserSession);

			return currentUserSession.toString();
		} else
			throw new LoginException("Please Enter a valid password");

	}

	@Override
	public String loginUser(UserDto user) throws LoginException {

		Customer existingUser = userRepo.findByUserName(user.getUserName());

		if (existingUser == null)
			throw new LoginException("Invalid credentials. Username does not exist " + user.getUserName());

		Optional<CurrentUserSession> validCustomerSessionOpt = currUserSession.findById(existingUser.getUserLoginId());

		if (validCustomerSessionOpt.isPresent()) {

			throw new LoginException("User already Logged In with this username");

		}

		if (existingUser.getPassword().equals(user.getPassword())) {

			String key = RandomString.make(6);

			Boolean isAdmin = false;
			Boolean isSeller = false;
			Boolean isCustomer = true;

			CurrentUserSession currentUserSession = new CurrentUserSession(existingUser.getUserLoginId(), key, isAdmin,
					isSeller, isCustomer, LocalDateTime.now());

			currUserSession.save(currentUserSession);

			return currentUserSession.toString();
		} else
			throw new LoginException("Please Enter a valid password");
	}

	@Override
	public String logoutAdmin(String key) throws LoginException {

		CurrentUserSession validCustomerSession = currUserSession.findByUuid(key);

		if (validCustomerSession == null) {
			throw new LoginException("User Not Logged In with this username");

		}

		currUserSession.delete(validCustomerSession);

		return "Logged Out !";
	}

	@Override
	public String logoutUser(String key) throws LoginException {

		CurrentUserSession validCustomerSession = currUserSession.findByUuid(key);

		if (validCustomerSession == null) {
			throw new LoginException("User Not Logged In with this username");

		}

		currUserSession.delete(validCustomerSession);

		return "Logged Out !";
	}

	@Override
	public String loginSeller(UserDto user) throws LoginException {

		Seller existingUser = sellerRepo.findByUserName(user.getUserName());

		if (existingUser == null)
			throw new LoginException("Invalid credentials. Username does not exist " + user.getUserName());

		Optional<CurrentUserSession> validCustomerSessionOpt = currUserSession
				.findById(existingUser.getSellerLoginId());

		if (validCustomerSessionOpt.isPresent()) {

			throw new LoginException("User already Logged In with this username");

		}

		if (existingUser.getPassword().equals(user.getPassword())) {

			String key = RandomString.make(6);

			Boolean isAdmin = false;
			Boolean isSeller = true;
			Boolean isCustomer = false;

			CurrentUserSession currentUserSession = new CurrentUserSession(existingUser.getSellerLoginId(), key,
					isAdmin, isSeller, isCustomer, LocalDateTime.now());

			currUserSession.save(currentUserSession);

			return currentUserSession.toString();
		} else
			throw new LoginException("Please Enter a valid password");
	}

	@Override
	public String logoutSeller(String key) throws LoginException {
		
		CurrentUserSession validCustomerSession = currUserSession.findByUuid(key);

		if (validCustomerSession == null) {
			throw new LoginException("User Not Logged In with this username");

		}

		currUserSession.delete(validCustomerSession);

		return "Logged Out !";
	}

}
