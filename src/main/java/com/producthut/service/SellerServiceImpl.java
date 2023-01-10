package com.producthut.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.producthut.repositiry.*;

import com.producthut.entity.*;
import com.producthut.exception.*;


@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private SellerRepo uRepo;

	@Autowired
	private CurrentUserSessionRepo csdao;

	@Override
	public Seller saveUser(RegisterUserDto userdto) throws UserException {

		Seller existingUserName = uRepo.findByUserName(userdto.getUserName());
		Seller existingUserEmail = uRepo.findByEmail(userdto.getEmail());

		if (existingUserName != null)
			throw new UserException("Username already exists " + userdto.getUserName());

		if (existingUserEmail != null)
			throw new UserException("User email exists " + userdto.getEmail());

		Seller user = new Seller(userdto);

		return uRepo.save(user);
	}

	@Override
	public Seller updateUser(RegisterUserDto userdto, String key) throws UserException, LoginException {

		CurrentUserSession loggedInUser = csdao.findByUuid(key);

		if (loggedInUser == null) {
			throw new UserException("Please provide a valid key to update a customer");
		}

		if (loggedInUser.getSeller() == false) {
			throw new UserException("Unauthorized Access! Only Seller can make changes");
		}

		Optional<Seller> existingUser = uRepo.findById(loggedInUser.getUserId());

		if (existingUser.isPresent()) {

			Seller user = new Seller(userdto);

			Seller oldseller = existingUser.get();

			user.setSellerLoginId(oldseller.getSellerLoginId());
			user.setProducts(oldseller.getProducts());

			Seller updatedSeller = uRepo.save(user);

			return updatedSeller;

		} else {
			throw new UserException("Seller does not exists with this id " + loggedInUser.getUserId());
		}

	}

	@Override
	public Seller deleteUser(String username) throws UserException {

		Seller existingUser = uRepo.findByUserName(username);

		if (existingUser == null)
			throw new UserException("User does not exists with this username " + username);

		uRepo.delete(existingUser);

		return existingUser;
	}

	@Override
	public Seller findByUserLoginId(Integer userLoginId) throws UserException {

		Optional<Seller> existingUser = uRepo.findById(userLoginId);

		if (existingUser.isPresent())
			return existingUser.get();
		else
			throw new UserException("User does not exists with this userLoginId " + userLoginId);

	}

	@Override
	public Seller findByEmail(String email) throws UserException {

		Seller existingUser = uRepo.findByEmail(email);

		if (existingUser != null)
			return existingUser;
		else
			throw new UserException("User does not exists with this email " + email);

	}

	@Override
	public Seller findByUserName(String userName) throws UserException {
		Seller existingUser = uRepo.findByUserName(userName);

		if (existingUser != null)
			return existingUser;
		else
			throw new UserException("User does not exists with this userName " + userName);
	}

	@Override
	public List<Seller> findAllUsers() throws UserException {

		List<Seller> users = uRepo.findAll();

		if (users.isEmpty())
			throw new UserException("No Users Found");

		return users;
	}

	@Override
	public Seller findByUserNameOrEmail(String userName, String email) throws UserException {

		Seller existingUser = uRepo.findByUserNameOrEmail(userName, email);

		if (existingUser != null)
			return existingUser;
		else
			throw new UserException("User does not exists with this userName or email " + userName + ", " + email);
	}

}
