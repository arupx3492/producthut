package com.producthut.repositiry;

import java.util.List;

import com.producthut.entity.Product;
import com.producthut.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

	@Query("from Product p where p.productPrice <=:price")
	public List<Product> getProductsUnderPrice(@Param("price") Integer price);

	@Query("from Product p where p.productPrice >=:fromPrice AND p.productPrice <=:toPrice")
	public List<Product> getProductsFromPriceToPrice(@Param("fromPrice") Integer fromPrice,
			@Param("toPrice") Integer toPrice);

	public List<Product> findByProductCategory(ProductCategory productCategory);

	public List<Product> findByProductColor(String productColor);

	public List<Product> findByProductManufacturer(String productManufacturer);

	public List<Product> findByProductName(String productName);

	public List<Product> findByProductPrice(Integer productPrice);

	public List<Product> findTop5ByProductPrice(Integer productPrice);

	public List<Product> findByProductPriceBetween(Integer s_productPrice, Integer e_productPrice);

	public List<Product> findByProductPriceGreaterThanEqual(Integer productPrice);

	public List<Product> findByProductQuantity(Integer productQuantity);

}
