package com.producthut.controller;

import java.util.List;

import javax.validation.Valid;

import com.producthut.entity.Product;
import com.producthut.exception.*;
import com.producthut.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/productController")
public class ProductController {

	@Autowired
	private ProductService pservice;

	/**********************************
	 * POST MAPPINGS START
	 *  
	 ************************************/

	@PostMapping("/products")
	public ResponseEntity<Product> addProducthandler(@RequestBody Product product, @RequestParam String key,
													 @RequestParam Integer categoryId)
			throws ProductException, AdminException, LoginException, ProductCategoryException, UserException {

		Product savedproduct = pservice.addProduct(product, key, categoryId);

		return new ResponseEntity<Product>(savedproduct, HttpStatus.OK);
	}

	@PutMapping("/products")
	public ResponseEntity<Product> updateProductHandler(@Valid @RequestBody Product product,
			@RequestParam Integer productId, @RequestParam String key)
			throws ProductException, AdminException, LoginException, UserException {

		Product savedproduct = pservice.updateProduct(product, key, productId);

		return new ResponseEntity<Product>(savedproduct, HttpStatus.FOUND);
	}

	@DeleteMapping("/products/{productId}")
	public ResponseEntity<Product> deleteProductHandler(@PathVariable("productId") Integer productId,
			@RequestParam String key) throws ProductException, AdminException, LoginException, OrderException {

		Product savedproduct = pservice.deleteProduct(key, productId);

		return new ResponseEntity<Product>(savedproduct, HttpStatus.OK);
	}

	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProductByIdHandler(@PathVariable("productId") Integer productId)
			throws ProductException {

		Product product = pservice.getProductById(productId);

		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	// ***************************************************************************************************************
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProductsHandler() throws ProductException {

		List<Product> products = pservice.getAllProducts();

		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	// ***************************************************************************************************************

	@GetMapping("/productsUnderPrice")
	public ResponseEntity<List<Product>> getProductsUnderPriceHandler(@RequestParam Integer price)
			throws ProductException {

		List<Product> products = pservice.getProductsUnderPrice(price);

		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	// ***************************************************************************************************************

	@GetMapping("/productsFromPriceToPrice")
	public ResponseEntity<List<Product>> getProductsFromPriceToPriceHandler(@RequestParam Integer fromPrice,
			@RequestParam Integer toPrice) throws ProductException {

		List<Product> products = pservice.getProductsFromPriceToPrice(fromPrice, toPrice);

		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	// ***************************************************************************************************************F

}
