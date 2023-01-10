package com.producthut.service;

import java.util.List;

import com.producthut.repositiry.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.producthut.entity.*;
import com.producthut.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface ProductService {

	public Product addProduct(Product product, String sellerKey, Integer categoryId)
			throws LoginException, AdminException, ProductCategoryException, UserException;

	public Product updateProduct(Product product, String sellerKey, Integer productId)
			throws LoginException, AdminException, ProductException, UserException;

	public Product deleteProduct(String sellerKey, Integer productId)
			throws LoginException, AdminException, ProductException, OrderException;

	public Product getProductById(Integer productId) throws ProductException;

	public List<Product> getAllProducts() throws ProductException;

	public List<Product> getProductsUnderPrice(Integer price) throws ProductException;

	public List<Product> getProductsFromPriceToPrice(Integer fromPrice, Integer toPrice) throws ProductException;

	public List<Cart> getAllCartWithProduct(Product product);

	public boolean getProductInOrders(Product product);
}
