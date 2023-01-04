package com.producthut.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cardId;

	@CreditCardNumber(message = "{card.number.invalid}")
	private String cardNumber;

	@DateTimeFormat(pattern = "dd/yy")
	private LocalDate validFrom;

	@DateTimeFormat(pattern = "dd/yy")
	private LocalDate validTo;

	@Transient
	private Integer cvv;
}
