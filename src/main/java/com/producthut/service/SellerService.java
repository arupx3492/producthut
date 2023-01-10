package com.producthut.service;

import java.util.List;

import com.producthut.entity.*;
import com.producthut.exception.*;


public interface SellerService {

	public Seller saveUser(RegisterUserDto user) throws UserException;

	public Seller updateUser(RegisterUserDto user, String key) throws UserException, LoginException;

	public Seller deleteUser(String username) throws UserException;

	public Seller findByUserLoginId(Integer userLoginId) throws UserException;

	public Seller findByEmail(String email) throws UserException;

	public Seller findByUserName(String userName) throws UserException;

	public Seller findByUserNameOrEmail(String userName, String email) throws UserException;

	public List<Seller> findAllUsers() throws UserException;
}
