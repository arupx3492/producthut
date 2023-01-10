package com.producthut.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer productCategoryId;

	private String productCategoryName;

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "productCategory")
	@JsonIgnore
	private List<Product> products;
}
