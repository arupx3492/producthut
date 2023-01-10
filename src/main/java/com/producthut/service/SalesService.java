package com.producthut.service;

import com.producthut.entity.*;
import com.producthut.exception.*;
import java.util.List;

public interface SalesService {

	public List<Order> getAllSalesHandler(String adminKey) throws LoginException, AdminException, OrderException;

	public List<Order> getSalesOfTodayHandler(String adminKey) throws LoginException, AdminException, OrderException;

	public List<Order> getSalesOfLastWeekHandler(String adminKey) throws LoginException, AdminException, OrderException;

	public List<Order> getSalesOfLastMonthHandler(String adminKey)
			throws LoginException, AdminException, OrderException;

	public List<Order> getSalesOfYearHandler(String adminKey) throws LoginException, AdminException, OrderException;

	public List<Order> getAllOrderFromDate(String fromDate, String adminKey)
			throws LoginException, AdminException, OrderException;

	public List<Order> getAllOrderBetweenTwoDates(String fromDate, String toDate, String adminKey)
			throws LoginException, AdminException, OrderException;
	
	

}
