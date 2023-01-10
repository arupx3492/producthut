package com.producthut.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.producthut.repositiry.*;

import com.producthut.entity.*;
import com.producthut.exception.*;

@Service
public class SalesServiceImpl implements SalesService {

	@Autowired
	private CurrentUserSessionRepo csdao;

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private OrderService orderService;

	@Override
	public List<Order> getAllSalesHandler(String adminKey) throws LoginException, AdminException, OrderException {

		CurrentUserSession loggedInUser = csdao.findByUuid(adminKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (loggedInUser.getAdmin() == false) {
			throw new AdminException("Unauthorized Access! Only Admin can make changes");
		}

		List<Order> orders = orderRepo.findAll();

		if (orders == null || orders.isEmpty())
			throw new OrderException("No Orders found.");

		return orders;
	}

	@Override
	public List<Order> getSalesOfTodayHandler(String adminKey) throws LoginException, AdminException, OrderException {

		CurrentUserSession loggedInUser = csdao.findByUuid(adminKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (loggedInUser.getAdmin() == false) {
			throw new AdminException("Unauthorized Access! Only Admin can make changes");
		}

		List<Order> orders = orderRepo.findByOrderDate(LocalDate.now());

		if (orders == null || orders.isEmpty())
			throw new OrderException("No Orders found.");

		return orders;
	}

	@Override
	public List<Order> getSalesOfLastWeekHandler(String adminKey)
			throws LoginException, AdminException, OrderException {

		CurrentUserSession loggedInUser = csdao.findByUuid(adminKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (loggedInUser.getAdmin() == false) {
			throw new AdminException("Unauthorized Access! Only Admin can make changes");
		}

		List<Order> orders = orderRepo.findByOrderDateGreaterThanEqual(LocalDate.now().minusDays(7));

		if (orders == null || orders.isEmpty())
			throw new OrderException("No Orders found.");

		return orders;
	}

	@Override
	public List<Order> getSalesOfLastMonthHandler(String adminKey)
			throws LoginException, AdminException, OrderException {

		CurrentUserSession loggedInUser = csdao.findByUuid(adminKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (loggedInUser.getAdmin() == false) {
			throw new AdminException("Unauthorized Access! Only Admin can make changes");
		}

		List<Order> orders = orderRepo.findByOrderDateGreaterThanEqual(LocalDate.now().minusMonths(1));

		if (orders == null)
			throw new OrderException("No Orders found.");

		return orders;

	}

	@Override
	public List<Order> getSalesOfYearHandler(String adminKey) throws LoginException, AdminException, OrderException {

		CurrentUserSession loggedInUser = csdao.findByUuid(adminKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (loggedInUser.getAdmin() == false) {
			throw new AdminException("Unauthorized Access! Only Admin can make changes");
		}

		List<Order> orders = orderRepo.findByOrderDateGreaterThanEqual(LocalDate.now().minusYears(1));

		if (orders == null || orders.isEmpty())
			throw new OrderException("No Orders found.");

		return orders;

	}

	@Override
	public List<Order> getAllOrderFromDate(String fdate, String adminKey)
			throws LoginException, AdminException, OrderException {
		CurrentUserSession loggedInUser = csdao.findByUuid(adminKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (loggedInUser.getAdmin() == false) {
			throw new AdminException("Unauthorized Access! Only Admin can make changes");
		}
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate fromDate = LocalDate.parse(fdate, dtf);
		
		
		List<Order> orders = orderRepo.findByOrderDateGreaterThanEqual(fromDate);

		if (orders == null || orders.isEmpty())
			throw new OrderException("No Orders found.");

		return orders;
	}

	@Override
	public List<Order> getAllOrderBetweenTwoDates(String fdate, String tdate, String adminKey)
			throws LoginException, AdminException, OrderException {

		CurrentUserSession loggedInUser = csdao.findByUuid(adminKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (loggedInUser.getAdmin() == false) {
			throw new AdminException("Unauthorized Access! Only Admin can make changes");
		}

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate fromDate = LocalDate.parse(fdate, dtf);

		LocalDate toDate = LocalDate.parse(tdate, dtf);

		List<Order> orders = orderRepo.findByOrderDateBetween(fromDate, toDate);

		if (orders == null || orders.isEmpty())
			throw new OrderException("No Orders found.");

		return orders;
	}

}
