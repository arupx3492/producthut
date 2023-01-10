package com.producthut.service;

import com.producthut.entity.Order;
import com.producthut.entity.Product;
import com.producthut.exception.*;

import java.util.List;
import java.util.Optional;



public interface OrderService {

	public Order viewOrder(Integer orderId, String customerKey) throws LoginException, UserException, OrderException;

	public List<Order> viewAllOrder(String customerKey) throws LoginException, UserException, OrderException;

	public Order deleteOrderByOrderId(Integer orderId, String customerKey)
			throws LoginException, UserException, OrderException;

	public Order updateOrderByOrderId(Order order, Integer orderId, String customerKey)
			throws LoginException, UserException, OrderException;

	public Order placeOrder(String customerKey) throws LoginException, UserException, CartException, AddressException;

	public boolean getProductInOrders(Product product);
}
