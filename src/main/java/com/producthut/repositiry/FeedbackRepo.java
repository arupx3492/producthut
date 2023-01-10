package com.producthut.repositiry;

import java.time.LocalDate;
import java.util.List;

import com.producthut.entity.Customer;
import com.producthut.entity.Feedback;
import com.producthut.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FeedbackRepo extends JpaRepository<Feedback, Integer> {

	public List<Feedback> findByCustomer(Customer customer);

	public List<Feedback> findByOrder(Order order);

	public List<Feedback> findByComments(String comments);

	public List<Feedback> findByFeedbackdate(LocalDate feedbackdate);

	public List<Feedback> findByDeliveryRating(Integer deliveryRating);

	public List<Feedback> findByOverallRating(Integer overallRating);

	public List<Feedback> findByProductRating(Integer productRating);

	public List<Feedback> findByStatus(String status);

}
