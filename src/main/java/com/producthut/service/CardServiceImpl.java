package com.producthut.service;

import com.producthut.entity.Card;
import com.producthut.entity.CurrentUserSession;
import com.producthut.entity.Customer;
import com.producthut.exception.CardException;
import com.producthut.exception.LoginException;
import com.producthut.exception.UserException;
import com.producthut.repositiry.CardRepo;
import com.producthut.repositiry.CurrentUserSessionRepo;
import com.producthut.repositiry.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CardServiceImpl implements CardService {

	@Autowired
	private CardRepo cardRepo;

	@Autowired
	private CustomerRepo uRepo;

	@Autowired
	private CurrentUserSessionRepo csdao;

	@Override
	public Card addCard(Card card, String customerKey) throws UserException, LoginException {

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

			Card savedcard = cardRepo.save(card);

			customer.getUserCards().add(savedcard);

			uRepo.save(customer);

			return savedcard;

		} else {
			throw new UserException("User Not Found");
		}

	}

	@Override
	public String deleteCard(Integer cardId, String customerKey) throws CardException, LoginException, UserException {

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

			List<Card> cards = customer.getUserCards();

			boolean flag = cards.removeIf((c) -> c.getCardId() == cardId);

			customer.setUserCards(cards);

			if (flag) {

				uRepo.save(customer);

				cardRepo.deleteById(cardId);

				return "Card Deleted Succesfully!";
			} else {
				throw new CardException("No card found for this user with cardId: " + cardId);
			}
		} else {
			throw new UserException("User Not Found");
		}
	}

	@Override
	public Card updateCard(Integer cardId, Card card, String customerKey)
			throws LoginException, UserException, CardException {

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

			List<Card> cards = customer.getUserCards();

			boolean flag = cards.removeIf((c) -> c.getCardId() == cardId);

			if (flag) {

				Card savedcard = cardRepo.save(card);
				cards.add(savedcard);

				customer.setUserCards(cards);

				uRepo.save(customer);

				return savedcard;
			} else {
				throw new CardException("No card found for this user with cardId: " + cardId);
			}

		} else {
			throw new UserException("User Not Found");
		}
	}

	@Override
	public Card getCardById(Integer cardId, String customerKey) throws LoginException, UserException, CardException {

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

			List<Card> cards = customer.getUserCards();

			Card card = null;
			for (Card cd : cards) {
				if (cd.getCardId() == cardId) {
					card = cd;
					break;
				}
			}
			if (card != null)
				return card;
			else
				throw new CardException("No card found for this user with cardId: " + cardId);

		} else {
			throw new UserException("User Not Found");
		}
	}

	@Override
	public List<Card> getAllCardOfCustomer(String customerKey) throws CardException, UserException, LoginException {

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

			List<Card> cards = customer.getUserCards();

			if (!cards.isEmpty())
				return cards;
			else
				throw new CardException("No card found for this user");

		} else {
			throw new UserException("User Not Found");
		}
	}

}
