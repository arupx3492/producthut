package com.producthut.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.producthut.repositiry.*;

import com.producthut.entity.*;
import com.producthut.exception.*;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private SellerRepo uRepo;

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private ProductCategoryRepo pcRepo;

	@Autowired
	private CurrentUserSessionRepo csdao;

	@Override
	public Product addProduct(Product product, String sellerKey, Integer categoryId)
			throws LoginException, AdminException, ProductCategoryException, UserException {

		CurrentUserSession loggedInUser = csdao.findByUuid(sellerKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (loggedInUser.getSeller() == false) {
			throw new AdminException("Unauthorized Access! Only Seller can make changes");
		}

		Optional<ProductCategory> existedCategory = pcRepo.findById(categoryId);

		if (existedCategory.isPresent()) {

			Optional<Seller> existingSeller = uRepo.findById(loggedInUser.getUserId());

			if (existingSeller.isPresent()) {

				Seller seller = existingSeller.get();

				ProductCategory presentCategory = existedCategory.get();
				product.setProductCategory(presentCategory);

				Map<Product, Integer> map = seller.getProducts();

				if (map.containsKey(product)) {
					map.put(product, map.get(product) + product.getProductQuantity());
					product.setProductQuantity(product.getProductQuantity() + map.get(product));
				} else {
					map.put(product, product.getProductQuantity());
				}

				return productRepo.save(product);

			} else {
				throw new UserException("Seller Not Found");
			}

		} else {
			throw new ProductCategoryException("Category does not exists with this category Id" + categoryId);
		}

	}

	@Override
	public Product updateProduct(Product product, String sellerKey, Integer productId)
			throws LoginException, AdminException, ProductException, UserException {

		CurrentUserSession loggedInUser = csdao.findByUuid(sellerKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (loggedInUser.getSeller() == false) {
			throw new AdminException("Unauthorized Access! Only Seller can make changes");
		}

		Optional<Product> existingProduct = productRepo.findById(productId);

		if (existingProduct.isPresent()) {

			Product present = existingProduct.get();

			product.setProductCategory(present.getProductCategory());

			Optional<Seller> existingSeller = uRepo.findById(loggedInUser.getUserId());

			if (existingSeller.isPresent()) {

				Seller seller = existingSeller.get();

				Map<Product, Integer> map = seller.getProducts();

				if (map.containsKey(product)) {
					map.put(product, product.getProductQuantity());
				} else {

					throw new ProductException("Product not associated with seller");
				}

				return productRepo.save(product);

			} else {
				throw new UserException("Seller Not Found");
			}

		} else {
			throw new ProductException("Product does not exists with this product Id" + productId);
		}
	}

	@Override
	public Product deleteProduct(String sellerKey, Integer productId)
			throws ProductException, LoginException, AdminException, OrderException {

		CurrentUserSession loggedInUser = csdao.findByUuid(sellerKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (loggedInUser.getSeller() == false) {
			throw new AdminException("Unauthorized Access! Only Seller can make changes");
		}

		Optional<Product> existingProduct = productRepo.findById(productId);

		if (existingProduct.isPresent()) {

			Product present = existingProduct.get();

			present.setProductCategory(null);

			if (!getProductInOrders(present)) {

				List<Cart> carts = getAllCartWithProduct(present);

				if (!carts.isEmpty()) {

					for (Cart c : carts) {
						c.getProducts().remove(present);
					}

				}

				Optional<Seller> currentSeller = uRepo.findById(loggedInUser.getUserId());

				if (currentSeller.isPresent()) {
					Seller seller = currentSeller.get();

					seller.getProducts().remove(present);

					productRepo.delete(present);

					return present;

				} else {
					throw new AdminException("Seller not Found");
				}

			} else {
				throw new OrderException("Product can't deleted once order is placed");
			}

		} else {
			throw new ProductException("Product does not exists with this product Id" + productId);
		}
	}

	@Override
	public Product getProductById(Integer productId) throws ProductException {

		Optional<Product> existingProduct = productRepo.findById(productId);

		if (existingProduct.isPresent()) {

			Product present = existingProduct.get();

			return present;

		} else {
			throw new ProductException("Product does not exists with this product Id" + productId);
		}
	}

	@Override
	public List<Product> getAllProducts() throws ProductException {

		List<Product> list = productRepo.findAll();
		if (list.isEmpty()) {
			throw new ProductException("No Products found");
		}

		return list;
	}

	@Override
	public List<Product> getProductsUnderPrice(Integer price) throws ProductException {

		List<Product> list = productRepo.getProductsUnderPrice(price);
		if (list.isEmpty()) {
			throw new ProductException("No Products found");
		}

		return list;
	}

	@Override
	public List<Product> getProductsFromPriceToPrice(Integer fromPrice, Integer toPrice) throws ProductException {

		List<Product> list = productRepo.getProductsFromPriceToPrice(fromPrice, toPrice);
		if (list.isEmpty()) {
			throw new ProductException("No Products found");
		}

		return list;
	}

	@Override
	public List<Cart> getAllCartWithProduct(Product product) {

		List<Cart> carts = new ArrayList<>();

		carts = cartRepo.findAll();

		if (carts.isEmpty())
			return carts;

		carts = carts.stream().filter(c -> c.getProducts().containsKey(product)).collect(Collectors.toList());

		return carts;

	}

	@Override
	public boolean getProductInOrders(Product product) {

		List<Order> ordersList = orderRepo.findAll();

		if (ordersList == null)
			return false;

		List<Order> newList = ordersList.stream().filter(o -> o.getProducts().containsKey(product))
				.collect(Collectors.toList());

		if (newList.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

}
