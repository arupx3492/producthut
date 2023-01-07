package com.producthut.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException {
	@ExceptionHandler(ProductException.class)
	public ResponseEntity<MyExceptionDetails> productExceptionHandler(ProductException p, WebRequest request) {


		MyExceptionDetails md = new MyExceptionDetails(LocalDateTime.now(), p.getMessage(), request.getDescription(false));
		return new ResponseEntity<MyExceptionDetails>(md, HttpStatus.OK);


	}

	@ExceptionHandler(CategoryException.class)
	public ResponseEntity<MyExceptionDetails> productExceptionHandler(CategoryException p, WebRequest request) {


		MyExceptionDetails md = new MyExceptionDetails(LocalDateTime.now(), p.getMessage(), request.getDescription(false));
		return new ResponseEntity<MyExceptionDetails>(md, HttpStatus.OK);


	}

	@ExceptionHandler(AddressException.class)
	public ResponseEntity<MyExceptionDetails> productExceptionHandler(AddressException p, WebRequest request) {


		MyExceptionDetails md = new MyExceptionDetails(LocalDateTime.now(), p.getMessage(), request.getDescription(false));
		return new ResponseEntity<MyExceptionDetails>(md, HttpStatus.OK);


	}
	@ExceptionHandler(AdminException.class)
	public ResponseEntity<MyExceptionDetails> productExceptionHandler(AdminException p, WebRequest request) {


		MyExceptionDetails md = new MyExceptionDetails(LocalDateTime.now(), p.getMessage(), request.getDescription(false));
		return new ResponseEntity<MyExceptionDetails>(md, HttpStatus.OK);


	}
	@ExceptionHandler(CardException.class)
	public ResponseEntity<MyExceptionDetails> productExceptionHandler(CardException p, WebRequest request) {


		MyExceptionDetails md = new MyExceptionDetails(LocalDateTime.now(), p.getMessage(), request.getDescription(false));
		return new ResponseEntity<MyExceptionDetails>(md, HttpStatus.OK);


	}
	@ExceptionHandler(CartException.class)
	public ResponseEntity<MyExceptionDetails> productExceptionHandler(CartException p, WebRequest request) {


		MyExceptionDetails md = new MyExceptionDetails(LocalDateTime.now(), p.getMessage(), request.getDescription(false));
		return new ResponseEntity<MyExceptionDetails>(md, HttpStatus.OK);


	}
	@ExceptionHandler(OrderException.class)
	public ResponseEntity<MyExceptionDetails> productExceptionHandler(OrderException p, WebRequest request) {


		MyExceptionDetails md = new MyExceptionDetails(LocalDateTime.now(), p.getMessage(), request.getDescription(false));
		return new ResponseEntity<MyExceptionDetails>(md, HttpStatus.OK);


	}
	@ExceptionHandler(PaymentException.class)
	public ResponseEntity<MyExceptionDetails> productExceptionHandler(PaymentException p, WebRequest request) {


		MyExceptionDetails md = new MyExceptionDetails(LocalDateTime.now(), p.getMessage(), request.getDescription(false));
		return new ResponseEntity<MyExceptionDetails>(md, HttpStatus.OK);


	}
	@ExceptionHandler(UserException.class)
	public ResponseEntity<MyExceptionDetails> productExceptionHandler(UserException p, WebRequest request) {


		MyExceptionDetails md = new MyExceptionDetails(LocalDateTime.now(), p.getMessage(), request.getDescription(false));
		return new ResponseEntity<MyExceptionDetails>(md, HttpStatus.OK);


	}
}