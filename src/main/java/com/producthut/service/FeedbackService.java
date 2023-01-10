package com.producthut.service;

import com.producthut.entity.Feedback;
import com.producthut.exception.FeedbackException;
import com.producthut.exception.LoginException;
import com.producthut.exception.OrderException;
import com.producthut.exception.UserException;

import java.util.List;


public interface FeedbackService {

	public Feedback addFeedBack(Feedback feedback, String key, Integer orderId)
			throws FeedbackException, UserException, OrderException, LoginException;

	public Feedback updateFeedBack(Feedback feedback, String key, Integer feedbackId)
			throws FeedbackException, LoginException, UserException;

	public Feedback viewFeedBack(Integer feedbackId) throws FeedbackException;

	public List<Feedback> viewAllFeedBack() throws FeedbackException;

	public Feedback deleteFeedBack(String key, Integer feedbackId)
			throws FeedbackException, LoginException, UserException;

}
