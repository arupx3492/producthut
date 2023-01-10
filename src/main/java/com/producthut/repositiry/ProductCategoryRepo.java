package com.producthut.repositiry;

import java.util.List;

import com.producthut.entity.Product;
import com.producthut.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductCategoryRepo extends JpaRepository<ProductCategory, Integer> {

	public ProductCategory findByProductCategoryName(String productCategoryName);

	@Query("Select p.products from ProductCategory p where p.productCategoryId =:pid")
	public List<Product> getProductsFromProductCategoryByProductCategoryId(@Param("pid") Integer pId);

	@Query("select p.products from ProductCategory p where p.productCategoryName=:pname")
	public List<Product> getProductsFromProductCategoryByProductCategoryName(@Param("pname") String pname);

}
