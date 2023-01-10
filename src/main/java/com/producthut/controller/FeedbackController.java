package com.producthut.controller;

import java.util.List;

import com.producthut.entity.Feedback;
import com.producthut.exception.*;
import com.producthut.service.FeedbackService;
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




@RestController
@RequestMapping("/feedback")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	@PostMapping("/feedbacks")
	public ResponseEntity<Feedback> addFeedbackHandler(@RequestParam String key, @RequestParam Integer orderId,
													   @RequestBody Feedback feedback) throws FeedbackException, UserException, OrderException, LoginException {

		Feedback addFeedback = feedbackService.addFeedBack(feedback, key, orderId);

		return new ResponseEntity<Feedback>(addFeedback, HttpStatus.OK);
	}

	@PutMapping("/feedbacks/{feedbackId}")
	public ResponseEntity<Feedback> updateFeedbackHandler(@RequestParam String key, @RequestBody Feedback feedback,
			@PathVariable("feedbackId") Integer feedbackId) throws FeedbackException, LoginException, UserException {

		Feedback updatedFeedback = feedbackService.updateFeedBack(feedback, key, feedbackId);

		return new ResponseEntity<Feedback>(updatedFeedback, HttpStatus.OK);

	}

	@GetMapping("/feedbacks/{feedbackId}")
	public ResponseEntity<Feedback> viewFeedBackHandler(@PathVariable("feedbackId") Integer feedbackId) throws FeedbackException {

		Feedback feedback = feedbackService.viewFeedBack(feedbackId);

		return new ResponseEntity<Feedback>(feedback, HttpStatus.OK);

	}

	@GetMapping("/feedbacks")
	public ResponseEntity<List<Feedback>> viewAllFeedBackHandler() throws FeedbackException {

		List<Feedback> feedbackList = feedbackService.viewAllFeedBack();

		return new ResponseEntity<List<Feedback>>(feedbackList, HttpStatus.OK);

	}

	@DeleteMapping("/feedbacks/{feedbackId}")
	public ResponseEntity<Feedback> deleteFeedbackHandler(@RequestParam String key, @PathVariable("feedbackId") Integer feedbackId)
			throws FeedbackException, LoginException, UserException {
		Feedback deleteFeedback = feedbackService.deleteFeedBack(key, feedbackId);

		return new ResponseEntity<Feedback>(deleteFeedback, HttpStatus.ACCEPTED);

	}
}
