package com.producthut.service;

import com.producthut.entity.Address;
import com.producthut.exception.AddressException;
import com.producthut.exception.LoginException;
import com.producthut.exception.UserException;


import java.util.List;



public interface AddressService {

	public Address addAddress(Address address, String customerKey)
			throws LoginException, UserException, AddressException;

	public String deleteAddress(String customerKey) throws LoginException, UserException, AddressException;

	public Address updateAddress(Address address, String customerKey)
			throws LoginException, UserException, AddressException;

	public Address getAddressByCustomerKey(String customerKey) throws LoginException, UserException, AddressException;

//	public List<Address> getAllAddressOfCustomer(String customerKey)
//			throws LoginException, UserException, AddressException;

}
