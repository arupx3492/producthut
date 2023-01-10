package com.producthut.service;

import com.producthut.entity.Admin;
import com.producthut.exception.AdminException;
import com.producthut.exception.LoginException;

import java.util.List;



public interface AdminService {

	public Admin saveUser(Admin user) throws AdminException;

	public Admin updateUser(Admin user, String key) throws AdminException, LoginException;

	public Admin deleteUser(String adminUsername) throws AdminException;

	public Admin findByAdminId(Integer adminId) throws AdminException;

	public Admin findByUserName(String adminUserName) throws AdminException;

	public List<Admin> findAllUsers() throws AdminException;
}
