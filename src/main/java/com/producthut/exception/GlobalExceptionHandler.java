package com.producthut.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(PaymentException.class)
	public ResponseEntity<MyErrorDetails> myPaymentExceptionExceptionHandler(PaymentException e, WebRequest web) {

		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(), web.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AddressException.class)
	public ResponseEntity<MyErrorDetails> myAddressExceptionExceptionHandler(AddressException e, WebRequest web) {

		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(), web.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CardException.class)
	public ResponseEntity<MyErrorDetails> myCardExceptionExceptionHandler(CardException e, WebRequest web) {

		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(), web.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ProductCategoryException.class)
	public ResponseEntity<MyErrorDetails> myProductCategoryExceptionHandler(ProductCategoryException e,
			WebRequest web) {

		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(), web.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(OrderException.class)
	public ResponseEntity<MyErrorDetails> myOrderExceptionHandler(OrderException e, WebRequest web) {

		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(), web.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CartException.class)
	public ResponseEntity<MyErrorDetails> myCartExceptionHandler(CartException e, WebRequest web) {

		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(), web.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ProductException.class)
	public ResponseEntity<MyErrorDetails> myProductExceptionHandler(ProductException e, WebRequest web) {

		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(), web.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LoginException.class)
	public ResponseEntity<MyErrorDetails> myLoginExceptionHandler(LoginException e, WebRequest web) {

		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(), web.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AdminException.class)
	public ResponseEntity<MyErrorDetails> myAdminExceptionHandler(AdminException e, WebRequest web) {

		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(), web.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserException.class)
	public ResponseEntity<MyErrorDetails> myUserExceptionHandler(UserException e, WebRequest web) {

		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(), web.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<MyErrorDetails> myNoHandlerFoundExceptionHandler(NoHandlerFoundException e, WebRequest web) {

		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(), web.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MyErrorDetails> myMANVExceptionHandler(MethodArgumentNotValidException me) {
		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), "Validation Error",
				me.getBindingResult().getFieldError().getDefaultMessage());
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails> myExceptionHandler(Exception e, WebRequest web) {

		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(), web.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
}
