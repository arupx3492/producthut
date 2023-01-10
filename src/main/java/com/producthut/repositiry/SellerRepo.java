package com.producthut.repositiry;

import com.producthut.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface SellerRepo extends JpaRepository<Seller, Integer> {

	public Seller findByEmail(String email);

	public Seller findByUserName(String userName);

	public Seller findByUserNameOrEmail(String username, String email);

}
