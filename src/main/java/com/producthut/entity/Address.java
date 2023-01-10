package com.producthut.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer addressId;

//	@Pattern(regexp = "^(true|false)$",message = "{address.default.invalid}")
	private boolean isDefault;

	@NotBlank(message = "{address.type.invalid}")
	@NotNull(message = "{address.type.invalid}")
	private String addressType;

	@NotBlank(message = "{address.state.invalid}")
	@NotNull(message = "{address.state.invalid}")
	private String state;

	@NotBlank(message = "{address.district.invalid}")
	@NotNull(message = "{address.district.invalid}")
	private String district;

	@Pattern(regexp = "^[0-9]{6}", message = "{address.pincode.invalid}")
	private String pincode;

	@NotBlank(message = "{address.landmark.invalid}")
	@NotNull(message = "{address.landmark.invalid}")
	private String landmark;
}
