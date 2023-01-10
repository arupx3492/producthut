package com.producthut.entity;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {
	
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
	
}
