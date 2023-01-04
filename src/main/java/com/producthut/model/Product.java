package com.producthut.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer productId;

	private Integer productPrice;

	private Integer productQuantity;

	private String productName;
	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private Category category;
}
