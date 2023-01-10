package com.producthut.controller;

import java.util.List;

import javax.validation.Valid;

import com.producthut.entity.Order;
import com.producthut.exception.*;
import com.producthut.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/orderController")
public class OrderController {

	@Autowired
	private OrderService oservice;

	@PostMapping("/orders")
	public ResponseEntity<Order> placeOrderHandler(@RequestParam String customerKey)
			throws LoginException, UserException, CartException, AddressException {

		Order savedOrder = oservice.placeOrder(customerKey);

		return new ResponseEntity<Order>(savedOrder, HttpStatus.OK);

	}

	@PutMapping("/orders")
	public ResponseEntity<Order> updateOrderHandler(@Valid @RequestBody Order order, @RequestParam Integer orderId,
			@RequestParam String customerKey) throws LoginException, UserException, CartException, OrderException {

		Order savedOrder = oservice.updateOrderByOrderId(order, orderId, customerKey);

		return new ResponseEntity<Order>(savedOrder, HttpStatus.OK);

	}

	@DeleteMapping("/orders")
	public ResponseEntity<Order> deleteOrderHandler(@RequestParam Integer orderId, @RequestParam String customerKey)
			throws LoginException, UserException, CartException, OrderException {

		Order savedOrder = oservice.deleteOrderByOrderId(orderId, customerKey);

		return new ResponseEntity<Order>(savedOrder, HttpStatus.OK);

	}

	@GetMapping("/orders/{orderId}")
	public ResponseEntity<Order> getOrderByIdHandler(@PathVariable("orderId") Integer orderId,
			@RequestParam String customerKey) throws LoginException, UserException, CartException, OrderException {

		Order savedOrder = oservice.viewOrder(orderId, customerKey);

		return new ResponseEntity<Order>(savedOrder, HttpStatus.OK);

	}

	@GetMapping("/orders")
	public ResponseEntity<List<Order>> getOrderByIdHandler(@RequestParam String customerKey)
			throws LoginException, UserException, CartException, OrderException {

		List<Order> savedOrder = oservice.viewAllOrder(customerKey);

		return new ResponseEntity<List<Order>>(savedOrder, HttpStatus.OK);

	}

}
