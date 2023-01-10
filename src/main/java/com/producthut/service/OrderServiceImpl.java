package com.producthut.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.producthut.entity.*;
import com.producthut.exception.*;
import com.producthut.repositiry.CurrentUserSessionRepo;
import com.producthut.repositiry.CustomerRepo;
import com.producthut.repositiry.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private CustomerRepo uRepo;

	@Autowired
	private CurrentUserSessionRepo csdao;

	@Override
	public Order viewOrder(Integer orderId, String customerKey) throws LoginException, UserException, OrderException {

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

			Optional<Order> existingOrder = orderRepo.findById(orderId);

			if (existingOrder.isPresent()) {

				Order savedOrder = existingOrder.get();

				if (savedOrder.getCustomer().getUserLoginId() == customer.getUserLoginId()) {
					return savedOrder;
				} else {
					throw new UserException("Invalid User Details for Order Id: " + orderId);
				}

			} else {
				throw new OrderException("No order found with this orderId: " + orderId);
			}

		} else {
			throw new UserException("User Not Found");
		}
	}

	@Override
	public List<Order> viewAllOrder(String customerKey) throws LoginException, UserException, OrderException {

		CurrentUserSession loggedInUser = csdao.findByUuid(customerKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (loggedInUser.getCustomer() == false) {
			throw new UserException("Unauthorized Access! Only Customer can make changes");
		}

		Optional<Customer> existingUser = uRepo.findById(loggedInUser.getUserId());

		if (existingUser.isPresent()) {

			Customer customer = existingUser.get();

			List<Order> list = customer.getOrders();

			if (list.size() > 0) {

				return list;

			} else {
				throw new OrderException("No order found");
			}

		} else {
			throw new UserException("User Not Found");
		}
	}

	@Override
	public Order deleteOrderByOrderId(Integer orderId, String customerKey)
			throws OrderException, UserException, LoginException {

		CurrentUserSession loggedInUser = csdao.findByUuid(customerKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (loggedInUser.getCustomer() == false) {
			throw new UserException("Unauthorized Access! Only Customer can make changes");
		}

		Optional<Customer> existingUser = uRepo.findById(loggedInUser.getUserId());

		if (existingUser.isPresent()) {

			Customer customer = existingUser.get();

			Optional<Order> existingOrder = orderRepo.findById(orderId);

			if (existingOrder.isPresent()) {

				Order savedOrder = existingOrder.get();

				if (savedOrder.getCustomer().getUserLoginId() == customer.getUserLoginId()) {

					orderRepo.delete(savedOrder);

					return savedOrder;

				} else {
					throw new UserException("Invalid User Details for Order Id: " + orderId);
				}

			} else {
				throw new OrderException("No order found with this orderId: " + orderId);
			}

		} else {
			throw new UserException("User Not Found");
		}
	}

	@Override
	public Order updateOrderByOrderId(Order order, Integer orderId, String customerKey)
			throws LoginException, UserException, OrderException {

		CurrentUserSession loggedInUser = csdao.findByUuid(customerKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (loggedInUser.getCustomer() == false) {
			throw new UserException("Unauthorized Access! Only Customer can make changes");
		}

		Optional<Customer> existingUser = uRepo.findById(loggedInUser.getUserId());

		if (existingUser.isPresent()) {

			Customer customer = existingUser.get();

			Optional<Order> existingOrder = orderRepo.findById(orderId);

			if (existingOrder.isPresent()) {

				Order savedOrder = existingOrder.get();

				if (savedOrder.getCustomer().getUserLoginId() == customer.getUserLoginId()) {

					order.setCustomer(customer);

					return orderRepo.save(order);

				} else {
					throw new UserException("Invalid User Details for Order Id: " + orderId);
				}
			} else {
				throw new OrderException("No order found with this orderId: " + orderId);
			}

		} else {
			throw new UserException("User Not Found");
		}
	}

	@Override
	public Order placeOrder(String customerKey) throws LoginException, UserException, CartException, AddressException {

		CurrentUserSession loggedInUser = csdao.findByUuid(customerKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (loggedInUser.getCustomer() == false) {
			throw new UserException("Unauthorized Access! Only Customer can make changes");
		}

		Optional<Customer> existingUser = uRepo.findById(loggedInUser.getUserId());

		if (existingUser.isPresent()) {

			Customer customer = existingUser.get();

			Cart cart = customer.getCart();

			Map<Product, Integer> productMap = cart.getProducts();

			if (productMap.isEmpty()) {
				throw new CartException("Empty cart Found");
			}

			Order newOrder = new Order();

			newOrder.setCustomer(customer);
			newOrder.setOrderDate(LocalDate.now());
			newOrder.setProducts(productMap);
			newOrder.setOrderStatus("Placed");
			Address address = customer.getAddresses();

			if (address == null) {
				throw new AddressException("No Address found for this User.");
			}

			newOrder.setShippingAddress(address);
			newOrder.setOrderAmount(cart.getCartValue());

			customer.getOrders().add(newOrder);
			cart.setProducts(new HashMap<>());
			cart.setCartValue(0);

			Order savedOrder = orderRepo.save(newOrder);

			return savedOrder;

		} else {
			throw new UserException("User Not Found");
		}
	}

	@Override
	public boolean getProductInOrders(Product product) {

		List<Order> ordersList = orderRepo.findAll();

		if (ordersList == null)
			return false;

		List<Order> newList = ordersList.stream().filter(o -> o.getProducts().containsKey(product))
				.collect(Collectors.toList());

		if (newList.size() > 0) {
			return true;
		} else {
			return false;
		}

	}

}
