package com.producthut.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer paymentId;

	private Boolean paid;

	private Integer amount;

	private LocalDate paymentDate;

	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private User user;

	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private Card card;

	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private Order order;
}
