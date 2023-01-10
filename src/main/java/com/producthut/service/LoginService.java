package com.producthut.service;


import com.producthut.entity.AdminDto;
import com.producthut.entity.UserDto;
import com.producthut.exception.LoginException;

public interface LoginService {

	public String loginAdmin(AdminDto admin) throws LoginException;

	public String loginUser(UserDto user) throws LoginException;

	public String logoutAdmin(String key) throws LoginException;

	public String logoutUser(String key) throws LoginException;

	public String loginSeller(UserDto user) throws LoginException;

	public String logoutSeller(String key) throws LoginException;

}
