package com.producthut.service;

import java.util.List;
import java.util.Optional;

import com.producthut.repositiry.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.producthut.entity.*;
import com.producthut.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryRepo pcRepo;

	@Autowired
	private CurrentUserSessionRepo csdao;

	@Override
	public ProductCategory addCategory(ProductCategory category, String adminKey)
			throws LoginException, AdminException, ProductCategoryException {

		CurrentUserSession loggedInUser = csdao.findByUuid(adminKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (loggedInUser.getAdmin() == false) {
			throw new AdminException("Unauthorized Access! Only Admin can make changes");
		}

		ProductCategory existisingCategory = pcRepo.findByProductCategoryName(category.getProductCategoryName());

		if (existisingCategory == null) {
			return pcRepo.save(category);
		} else {
			throw new ProductCategoryException("Route is Already present");
		}
	}

	@Override
	public ProductCategory updateCategory(ProductCategory category, Integer productCategoryId, String key)
			throws LoginException, AdminException, ProductCategoryException {

		CurrentUserSession loggedInUser = csdao.findByUuid(key);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (!loggedInUser.getAdmin()) {
			throw new AdminException("Unauthorized Access! Only Admin can make changes");
		}

		Optional<ProductCategory> existedCategory = pcRepo.findById(productCategoryId);

		if (existedCategory.isPresent()) {
			ProductCategory presentCategory = existedCategory.get();

			List<Product> productList = presentCategory.getProducts();

			for (int i = 0; i < productList.size(); i++) {
				Product p = productList.get(i);
				p.setProductCategory(category);
			}

			category.setProducts(productList);

			return pcRepo.save(category);

		} else {
			throw new ProductCategoryException("Category does not exists with this category Id" + productCategoryId);
		}

	}

	@Override
	public ProductCategory getCategoryById(Integer productCategoryId, String key)
			throws ProductCategoryException, LoginException, AdminException {

		CurrentUserSession loggedInUser = csdao.findByUuid(key);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (!loggedInUser.getAdmin()) {
			throw new AdminException("Unauthorized Access! Only Admin can make changes");
		}

		Optional<ProductCategory> existedCategory = pcRepo.findById(productCategoryId);

		if (existedCategory.isPresent()) {
			ProductCategory presentCategory = existedCategory.get();

			return presentCategory;

		} else {
			throw new ProductCategoryException("Category does not exists with this category Id" + productCategoryId);
		}
	}

	@Override
	public ProductCategory deleteCategory(Integer productCategoryId, String key)
			throws ProductCategoryException, LoginException, AdminException {

		CurrentUserSession loggedInUser = csdao.findByUuid(key);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (loggedInUser.getAdmin() == false) {
			throw new AdminException("Unauthorized Access! Only Admin can make changes");
		}

		Optional<ProductCategory> existedCategory = pcRepo.findById(productCategoryId);

		if (existedCategory.isPresent()) {
			ProductCategory presentCategory = existedCategory.get();

			List<Product> productList = presentCategory.getProducts();

			for (int i = 0; i < productList.size(); i++) {
				Product p = productList.get(i);
				p.setProductCategory(null);
			}

			presentCategory.setProducts(null);

			pcRepo.delete(presentCategory);

			return presentCategory;

		} else {
			throw new ProductCategoryException("Category does not exists with this category Id" + productCategoryId);
		}
	}

	@Override
	public List<ProductCategory> getAllCategory() throws ProductCategoryException {

		List<ProductCategory> list = pcRepo.findAll();
		if (list.isEmpty() == false) {

			return list;

		} else {
			throw new ProductCategoryException("No Category found");
		}
	}

	@Override
	public List<Product> getAllProductByCategoryId(Integer productCategoryId) throws ProductCategoryException {

		List<Product> list = pcRepo.getProductsFromProductCategoryByProductCategoryId(productCategoryId);
		if (list.isEmpty() == false) {

			return list;

		} else {
			throw new ProductCategoryException("No Products found");
		}
	}

	@Override
	public List<Product> getAllProductByCategoryname(String productCategoryName) throws ProductCategoryException {

		List<Product> list = pcRepo.getProductsFromProductCategoryByProductCategoryName(productCategoryName);
		if (list.isEmpty() == false) {

			return list;

		} else {
			throw new ProductCategoryException("No Products found");
		}
	}

}
