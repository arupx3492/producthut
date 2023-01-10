package com.producthut.controller;

import java.util.List;

import javax.validation.Valid;

import com.producthut.entity.Card;
import com.producthut.exception.CardException;
import com.producthut.exception.UserException;
import com.producthut.service.CardService;
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

import com.producthut.exception.LoginException;


@RestController
@RequestMapping("addressController")
public class CardController {

	@Autowired
	private CardService cservice;

	@PostMapping("/cards")
	public ResponseEntity<Card> addCardHandler(@Valid @RequestBody Card card, @RequestParam String customerKey)
			throws LoginException, UserException, CardException {

		Card savedcard = cservice.addCard(card, customerKey);

		return new ResponseEntity<Card>(savedcard, HttpStatus.OK);

	}

	@PutMapping("/cards/{cardId}")
	public ResponseEntity<Card> updateCardHandler(@PathVariable("cardId") Integer cardId,
			@Valid @RequestBody Card card, @RequestParam String customerKey)
			throws LoginException, UserException, CardException {

		Card savedcard = cservice.updateCard(cardId, card, customerKey);

		return new ResponseEntity<Card>(savedcard, HttpStatus.OK);

	}

	@DeleteMapping("/cards/{cardId}")
	public ResponseEntity<String> deleteCardHandler(@PathVariable("cardId") Integer cardId,
			@RequestParam String customerKey) throws LoginException, UserException, CardException {

		String res = cservice.deleteCard(cardId, customerKey);

		return new ResponseEntity<String>(res, HttpStatus.OK);

	}

	@GetMapping("/cards/{cardId}")
	public ResponseEntity<Card> getCardByIdHandler(@PathVariable("cardId") Integer cardId,
			@RequestParam String customerKey) throws LoginException, UserException, CardException {

		Card savedcard = cservice.getCardById(cardId, customerKey);

		return new ResponseEntity<Card>(savedcard, HttpStatus.OK);

	}

	@GetMapping("/cards")
	public ResponseEntity<List<Card>> getAllCardsOfCustomerHandler(@RequestParam String customerKey)
			throws LoginException, UserException, CardException {

		List<Card> savedcard = cservice.getAllCardOfCustomer(customerKey);

		return new ResponseEntity<List<Card>>(savedcard, HttpStatus.OK);

	}

}
