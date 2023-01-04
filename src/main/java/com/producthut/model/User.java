package com.producthut.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int userId;
	private String userName;
	private String password;
	private String name;
	private int age;
	private String email;
	private String phone;

	private  Cart cart;
	private Address address;
	private List<Card> cards=new ArrayList<Card>();
	private List<Order> orders=new ArrayList<Order>();
}
