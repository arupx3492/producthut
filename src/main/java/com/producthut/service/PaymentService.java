package com.producthut.service;

import com.producthut.entity.Payment;
import com.producthut.exception.*;

import java.util.List;



public interface PaymentService {

	public Payment makePayment(Integer orderId, Integer cardId, String customerKey)
			throws LoginException, UserException, OrderException, CardException;

	public String cancelPayment(Integer orderId, String customerKey)
			throws LoginException, UserException, OrderException;

	public Payment getPaymentDetailsByPaymentId(Integer paymentId, String customerKey)
			throws LoginException, UserException, OrderException, PaymentException;

	public Payment getPaymentDetailsByOrderId(Integer orderId, String customerKey)
			throws OrderException, LoginException, UserException, PaymentException;

	public List<Payment> getAllPaymentOfCustomerByCustomerId(String customerKey) throws UserException, LoginException, PaymentException;

}
