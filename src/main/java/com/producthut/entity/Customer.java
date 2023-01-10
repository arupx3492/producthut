package com.producthut.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userLoginId;

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

	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private Cart cart;

	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private Address addresses;

	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Card> userCards;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	@JsonIgnore
	private List<Order> orders;

	public Customer(RegisterUserDto user) {
		super();
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.contact = user.getContact();
		this.email = user.getEmail();
	}

}
