package com.producthut.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException {
	public ResponseEntity<MyExceptionDetails> productExceptionHandler(ProductException p, WebRequest request) {


		MyExceptionDetails md = new MyExceptionDetails(LocalDateTime.now(), p.getMessage(), request.getDescription(false));
		return new ResponseEntity<MyExceptionDetails>(md, HttpStatus.OK);


	}
}