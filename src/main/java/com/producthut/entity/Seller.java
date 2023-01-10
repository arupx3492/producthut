package com.producthut.entity;

import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Seller {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer sellerLoginId;

	@Size(min = 3, max = 20, message = "{user.invalid.userName}")
	@Column(unique = true)
	private String userName;

	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "{user.invalid.password}")
	private String password;

	@Size(min = 2, max = 20, message = "{user.invalid.firstName}")
	private String firstName;

	@Size(min = 2, max = 20, message = "{user.invalid.lastName}")
	private String lastName;

	@Pattern(regexp = "^[0-9]{10}", message = "{user.invalid.contact}")
	private String contact;

	@Email(message = "incorrect email")
	@Column(unique = true)
	private String email;

	@ElementCollection
	@CollectionTable(name = "seller_product", joinColumns = @JoinColumn(name = "seller_id"))
	@Column(name = "quantity")
	@MapKeyJoinColumn(name = "product_id", referencedColumnName = "productId")
	@JsonIgnore
	private Map<Product, Integer> products;

	public Seller(RegisterUserDto user) {
		super();
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.contact = user.getContact();
		this.email = user.getEmail();
	}
}
