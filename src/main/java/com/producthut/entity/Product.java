package com.producthut.entity;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer productId;

	private Integer productPrice;

	private Integer productQuantity;

	private String productName;

	private String productDimension;

	private String productColor;

	private String productManufacturer;

	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private ProductCategory productCategory;
	
	
}
