package com.producthut.controller;

import java.util.List;

import com.producthut.entity.Payment;
import com.producthut.exception.*;
import com.producthut.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/paymentController")
public class PaymentController {

	@Autowired
	private PaymentService payService;

	@PostMapping("/payment")
	public ResponseEntity<Payment> makePaymenthandler(@RequestParam String customerKey, @RequestParam Integer orderId,
													  @RequestParam Integer cardId) throws LoginException, UserException, OrderException, CardException {

		Payment payment = payService.makePayment(orderId, cardId, customerKey);

		return new ResponseEntity<Payment>(payment, HttpStatus.OK);
	}

	@PutMapping("/payment")
	public ResponseEntity<String> cancelPaymentHandler(@RequestParam String customerKey, @RequestParam Integer orderId)
			throws LoginException, UserException, OrderException {

		String res = payService.cancelPayment(orderId, customerKey);

		return new ResponseEntity<String>(res, HttpStatus.OK);
	}

	@GetMapping("/payment/{paymentId}")
	public ResponseEntity<Payment> getPaymentDetailsByPaymentIdHandler(@RequestParam String customerKey,
			@PathVariable("paymentId") Integer paymentId)
			throws LoginException, UserException, OrderException, PaymentException {

		Payment payment = payService.getPaymentDetailsByPaymentId(paymentId, customerKey);

		return new ResponseEntity<Payment>(payment, HttpStatus.OK);
	}

	@GetMapping("/payment")
	public ResponseEntity<Payment> getPaymentDetailsByOrderIdHandler(@RequestParam String customerKey,
			@RequestParam Integer orderId) throws OrderException, LoginException, UserException, PaymentException {

		Payment payment = payService.getPaymentDetailsByOrderId(orderId, customerKey);

		return new ResponseEntity<Payment>(payment, HttpStatus.OK);
	}

	@GetMapping("/payments")
	public ResponseEntity<List<Payment>> getAllPaymentOfCustomerByCustomerIdHandler(@RequestParam String customerKey)
			throws UserException, LoginException, PaymentException {

		List<Payment> payment = payService.getAllPaymentOfCustomerByCustomerId(customerKey);

		return new ResponseEntity<List<Payment>>(payment, HttpStatus.OK);
	}

}
