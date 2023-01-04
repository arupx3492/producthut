package com.producthut.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer categoryId;

	private String categoryName;

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "productCategory")
	@JsonIgnore
	private List<Product> products;
}
