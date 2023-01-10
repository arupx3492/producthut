package com.producthut.service;

import com.producthut.entity.Cart;
import com.producthut.entity.CurrentUserSession;
import com.producthut.entity.Customer;
import com.producthut.entity.Product;
import com.producthut.exception.AdminException;
import com.producthut.exception.LoginException;
import com.producthut.exception.ProductException;
import com.producthut.exception.UserException;
import com.producthut.repositiry.CartRepo;
import com.producthut.repositiry.CurrentUserSessionRepo;
import com.producthut.repositiry.CustomerRepo;
import com.producthut.repositiry.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private CustomerRepo uRepo;

	@Autowired
	private ProductRepo prodRepo;

	@Autowired
	private CurrentUserSessionRepo csdao;

	@Override
	public Cart addProductToCart(Integer productId, String customerKey)
			throws LoginException, UserException, AdminException, ProductException {

		CurrentUserSession loggedInUser = csdao.findByUuid(customerKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (!loggedInUser.getCustomer()) {
			throw new UserException("Unauthorized Access! Only Customer can make changes");
		}

		Optional<Product> existingProduct = prodRepo.findById(productId);

		if (!existingProduct.isPresent())
			throw new ProductException("Invalid Product id");

		Product product = existingProduct.get();

		Optional<Customer> existingUser = uRepo.findById(loggedInUser.getUserId());

		if (existingUser.isPresent()) {
			Customer customer = existingUser.get();

			Cart cart = customer.getCart();
			Map<Product, Integer> map = cart.getProducts();

			if (map.containsKey(product)) {
				map.put(product, map.get(product) + 1);
			} else {
				map.put(product, 1);
			}
			//decrease the product own quantity
//			product.setProductQuantity(product.getProductQuantity() - 1);
			//build the logic to update the product quantity uniformly

			cart.setCartValue(cart.getCartValue() + product.getProductPrice());

			Cart savedCart = cartRepo.save(cart);

			return savedCart;

		} else {
			throw new UserException("User Not Found");
		}

	}

	@Override
	public Cart increaseProductQuantityInCart(Integer productId, String customerKey)
			throws LoginException, UserException, ProductException {
		CurrentUserSession loggedInUser = csdao.findByUuid(customerKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (!loggedInUser.getCustomer()) {
			throw new UserException("Unauthorized Access! Only Customer can make changes");
		}

		Optional<Product> existingProduct = prodRepo.findById(productId);

		if (!existingProduct.isPresent())
			throw new ProductException("Invalid Product id");

		Product product = existingProduct.get();

		Optional<Customer> existingUser = uRepo.findById(loggedInUser.getUserId());

		if (existingUser.isPresent()) {
			Customer customer = existingUser.get();

			Cart cart = customer.getCart();
			Map<Product, Integer> map = cart.getProducts();

			if (map.containsKey(product)) {
				map.put(product, map.get(product) + 1);
			} else {
				map.put(product, 1);
			}
			cart.setCartValue(cart.getCartValue() + product.getProductPrice());

			Cart savedCart = cartRepo.save(cart);

			return savedCart;

		} else {
			throw new UserException("User Not Found");
		}
	}

	@Override
	public Cart reduceProductQuantityInCart(Integer productId, String customerKey)
			throws LoginException, UserException, ProductException {

		CurrentUserSession loggedInUser = csdao.findByUuid(customerKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (!loggedInUser.getCustomer()) {
			throw new UserException("Unauthorized Access! Only Customer can make changes");
		}

		Optional<Product> existingProduct = prodRepo.findById(productId);

		if (!existingProduct.isPresent())
			throw new ProductException("Invalid Product id");

		Product product = existingProduct.get();

		Optional<Customer> existingUser = uRepo.findById(loggedInUser.getUserId());

		if (existingUser.isPresent()) {
			Customer customer = existingUser.get();

			Cart cart = customer.getCart();
			Map<Product, Integer> map = cart.getProducts();

			if (map.containsKey(product)) {
				int quantity = map.get(product);
				if (quantity == 1) {
					map.remove(product);
				} else {
					map.put(product, quantity - 1);
				}

			} else {
				throw new ProductException("Product Not found in Cart");
			}

			cart.setCartValue(cart.getCartValue() - product.getProductPrice());

			Cart savedCart = cartRepo.save(cart);

			return savedCart;

		} else {
			throw new UserException("User Not Found");
		}
	}

	@Override
	public Cart deleteProductToCart(Integer productId, String customerKey)
			throws ProductException, LoginException, UserException {

		CurrentUserSession loggedInUser = csdao.findByUuid(customerKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (!loggedInUser.getCustomer()) {
			throw new UserException("Unauthorized Access! Only Customer can make changes");
		}

		Optional<Product> existingProduct = prodRepo.findById(productId);

		if (!existingProduct.isPresent())
			throw new ProductException("Invalid Product id");

		Product product = existingProduct.get();

		Optional<Customer> existingUser = uRepo.findById(loggedInUser.getUserId());

		if (existingUser.isPresent()) {
			Customer customer = existingUser.get();

			Cart cart = customer.getCart();
			Map<Product, Integer> map = cart.getProducts();

			if (map.containsKey(product)) {

				map.remove(product);

			} else {
				throw new ProductException("Product Not found in Cart");
			}

			cart.setCartValue(cart.getCartValue() - product.getProductPrice());

			Cart savedCart = cartRepo.save(cart);

			return savedCart;
		} else {
			throw new UserException("User Not Found");
		}
	}

	@Override
	public List<Product> getProductListOfCart(String customerKey)
			throws ProductException, LoginException, UserException {

		CurrentUserSession loggedInUser = csdao.findByUuid(customerKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (!loggedInUser.getCustomer()) {
			throw new UserException("Unauthorized Access! Only Customer can make changes");
		}

		Optional<Customer> existingUser = uRepo.findById(loggedInUser.getUserId());

		if (existingUser.isPresent()) {
			Customer customer = existingUser.get();

			Cart cart = customer.getCart();
			Map<Product, Integer> map = cart.getProducts();

			if (map.size() > 0) {

				Set<Product> set = map.keySet();

				return new ArrayList<>(set);

			} else {
				throw new ProductException("Product Not found in Cart");
			}

		} else {
			throw new UserException("User Not Found");
		}
	}

	@Override
	public Integer getCartValue(String customerKey) throws UserException, LoginException {

		CurrentUserSession loggedInUser = csdao.findByUuid(customerKey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (!loggedInUser.getCustomer()) {
			throw new UserException("Unauthorized Access! Only Customer can make changes");
		}

		Optional<Customer> existingUser = uRepo.findById(loggedInUser.getUserId());

		if (existingUser.isPresent()) {
			Customer customer = existingUser.get();

			Cart cart = customer.getCart();

			return cart.getCartValue();

		} else {
			throw new UserException("User Not Found");
		}
	}

	@Override
	public Cart emptyCart(String customerkey) throws UserException, LoginException {

		CurrentUserSession loggedInUser = csdao.findByUuid(customerkey);

		if (loggedInUser == null) {
			throw new LoginException("Invalid Key Entered");
		}

		if (!loggedInUser.getCustomer()) {
			throw new UserException("Unauthorized Access! Only Customer can make changes");
		}

		Optional<Customer> existingUser = uRepo.findById(loggedInUser.getUserId());

		if (existingUser.isPresent()) {
			Customer customer = existingUser.get();

			Cart cart = customer.getCart();

			cart.setProducts(new HashMap<>());

			Cart savedCart = cartRepo.save(cart);

			return savedCart;

		} else {
			throw new UserException("User Not Found");
		}
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
}
