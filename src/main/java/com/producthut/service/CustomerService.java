package com.producthut.service;

import com.producthut.entity.Customer;
import com.producthut.entity.RegisterUserDto;
import com.producthut.exception.LoginException;
import com.producthut.exception.UserException;

import java.util.List;



public interface CustomerService {

	public Customer saveUser(RegisterUserDto user) throws UserException;

	public Customer updateUser(RegisterUserDto user, String key) throws UserException, LoginException;

	public Customer deleteUser(String username, String customerKey) throws UserException, LoginException;

	public Customer findByUserLoginId(Integer userLoginId) throws UserException;

	public Customer findByEmail(String email) throws UserException;

	public Customer findByUserName(String userName) throws UserException;

	public Customer findByUserNameOrEmail(String userName, String email) throws UserException;

	public List<Customer> findAllUsers() throws UserException;
}
