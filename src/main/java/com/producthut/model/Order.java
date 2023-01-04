package com.producthut.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;

	private String orderStatus;

	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private User user;

	private Integer orderAmount;

	private LocalDate orderDate;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "order_table_product", joinColumns = @JoinColumn(name = "order_table_id"))
	@Column(name = "quantity")
	@MapKeyJoinColumn(name = "product_id", referencedColumnName = "productId")
	@JsonIgnore
	private Map<Product, Integer> products;

	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private Address address;

	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private Payment payment;
}
