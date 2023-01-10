package com.producthut.service;

import com.producthut.entity.Product;
import com.producthut.entity.ProductCategory;
import com.producthut.exception.AdminException;
import com.producthut.exception.LoginException;
import com.producthut.exception.ProductCategoryException;

import java.util.List;


public interface ProductCategoryService {

	public ProductCategory addCategory(ProductCategory category, String adminKey) throws LoginException, AdminException, ProductCategoryException;

	public ProductCategory updateCategory(ProductCategory category, Integer productCategoryId, String adminKey) throws LoginException, AdminException, ProductCategoryException;

	public ProductCategory getCategoryById(Integer productCategoryId, String adminKey) throws ProductCategoryException, LoginException, AdminException;

	public ProductCategory deleteCategory(Integer productCategoryId, String adminKey) throws ProductCategoryException, LoginException, AdminException;

	public List<ProductCategory> getAllCategory() throws ProductCategoryException;

	public List<Product> getAllProductByCategoryId(Integer productCategoryId) throws ProductCategoryException;

	public List<Product> getAllProductByCategoryname(String productCategoryName) throws ProductCategoryException;

}
