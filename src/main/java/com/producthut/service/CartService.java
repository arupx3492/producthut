package com.producthut.service;

import com.producthut.entity.Cart;
import com.producthut.entity.Product;
import com.producthut.exception.AdminException;
import com.producthut.exception.LoginException;
import com.producthut.exception.ProductException;
import com.producthut.exception.UserException;

import java.util.List;


public interface CartService {

	public Cart addProductToCart(Integer productId, String customerKey)
			throws LoginException, UserException, AdminException, ProductException;

	public Cart increaseProductQuantityInCart(Integer productId, String customerKey)
			throws LoginException, UserException, AdminException, ProductException;

	public Cart reduceProductQuantityInCart(Integer productId, String customerKey)
			throws LoginException, UserException, AdminException, ProductException;

	public Cart deleteProductToCart(Integer productId, String customerKey)
			throws LoginException, UserException, AdminException, ProductException;

	public List<Product> getProductListOfCart(String customerKey)
			throws LoginException, UserException, AdminException, ProductException;

	public Integer getCartValue(String customerKey)
			throws LoginException, UserException, AdminException, ProductException;

	public Cart emptyCart(String customerkey) throws LoginException, UserException, AdminException, ProductException;

	public List<Cart> getAllCartWithProduct(Product product);
}
