package com.producthut.service;

import com.producthut.entity.Card;
import com.producthut.exception.CardException;
import com.producthut.exception.LoginException;
import com.producthut.exception.UserException;

import java.util.List;

public interface CardService {

	public Card addCard(Card card, String customerKey) throws LoginException, UserException, CardException;

	public String deleteCard(Integer cardId, String customerKey) throws LoginException, UserException, CardException;

	public Card updateCard(Integer cardId, Card card, String customerKey)
			throws LoginException, UserException, CardException;

	public Card getCardById(Integer cardId, String customerKey) throws LoginException, UserException, CardException;

	public List<Card> getAllCardOfCustomer(String customerKey) throws LoginException, UserException, CardException;

}
