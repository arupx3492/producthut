package com.producthut.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.producthut.entity.*;
import com.producthut.exception.*;
import com.producthut.repositiry.CurrentUserSessionRepo;
import com.producthut.repositiry.CustomerRepo;
import com.producthut.repositiry.OrderRepo;
import com.producthut.repositiry.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepo payRepo;

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private CustomerRepo uRepo;

	@Autowired
	private CardService cardservice;

	@Autowired
	private CurrentUserSessionRepo csdao;

	@Override
	public Payment makePayment(Integer orderId, Integer cardId, String customerKey)
			throws LoginException, UserException, OrderException, CardException {

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

					Card userCard = cardservice.getCardById(cardId, customerKey);

					if (userCard == null)
						throw new CardException("No card found with the card id: " + cardId);

					Payment payment = new Payment();
					payment.setAmount(savedOrder.getOrderAmount());
					payment.setCard(userCard);
					payment.setOrder(savedOrder);
					payment.setPaid(true);
					payment.setPaymentDate(LocalDate.now());

					savedOrder.setPayment(payment);
					savedOrder.setOrderStatus("Paid");

					Payment savedPayment = payRepo.save(payment);

//					savedOrder.setPayment(savedPayment);
//					orderRepo.save(savedOrder);

					return savedPayment;

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
	public String cancelPayment(Integer orderId, String customerKey)
			throws UserException, LoginException, OrderException {

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
					

					Payment payment = new Payment();
					payment.setAmount(savedOrder.getOrderAmount());
					payment.setCard(null);
					payment.setOrder(savedOrder);
					payment.setPaid(false);
					payment.setPaymentDate(LocalDate.now());

					savedOrder.setPayment(payment);
					savedOrder.setOrderStatus("Unpaid");

					Payment savedPayment = payRepo.save(payment);

//					savedOrder.setOrderStatus("Cancelled");
//					orderRepo.save(savedOrder);

					return "Order cancelled";

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
	public Payment getPaymentDetailsByPaymentId(Integer paymentId, String customerKey)
			throws OrderException, LoginException, UserException, PaymentException {

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

			Optional<Payment> existingPayment = payRepo.findById(paymentId);

			if (existingPayment.isPresent()) {

				Payment savedPayment = existingPayment.get();

				Order savedOrder = savedPayment.getOrder();

				boolean flag = customer.getOrders().contains(savedOrder);

				if (flag) {

					return savedPayment;

				} else {
					throw new OrderException("for current User No order found with this paymentId: " + paymentId);
				}

			} else {
				throw new PaymentException("No payment found with this paymentId: " + paymentId);
			}

		} else {
			throw new UserException("User Not Found");
		}
	}

	@Override
	public Payment getPaymentDetailsByOrderId(Integer orderId, String customerKey)
			throws OrderException, LoginException, UserException, PaymentException {

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

					Payment savedPayment = savedOrder.getPayment();

					return savedPayment;

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
	public List<Payment> getAllPaymentOfCustomerByCustomerId(String customerKey)
			throws UserException, LoginException, PaymentException {

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

			List<Payment> payments = payRepo.findByCustomer(customer);

			if (payments != null) {

				return payments;

			} else {
				throw new PaymentException("No Payments Found For this cusomer id: " + customer.getUserLoginId());
			}

		} else {
			throw new UserException("User Not Found");
		}
	}

}
