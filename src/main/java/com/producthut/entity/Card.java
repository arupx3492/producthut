package com.producthut.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cardId;

//	@Pattern(regexp = "^[0-9]{12}",message = "{card.number.invalid}")
	@CreditCardNumber(message = "{card.number.invalid}")
	private String cardNumber;

//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/yy")
	@DateTimeFormat(pattern = "dd/yy")
	private LocalDate validFrom;

	@DateTimeFormat(pattern = "dd/yy")
	private LocalDate validTo;

	@Transient
	private Integer cvv;
}
