package com.flurenco.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.flurenco.entity.Products;

@Repository
public interface SearchRepository extends JpaRepository<Products, Integer>{

	
	public List<Products> findByProductNameContainingIgnoreCase(String productName);
	
	Products findByProductName(String productName);
	
	@Query(value = "SELECT * FROM products WHERE product_type = :product_type", nativeQuery = true)
	public List<Products> findProductsByProductType(@Param("product_type") String product_type);
	
	List<Products> findByProductType(String productType);
	
	@Query(value = "SELECT * FROM products WHERE product_type = :product_type ORDER BY product_name", nativeQuery = true)
	public List<Products> filterProductsByNameInASC(@Param("product_type") String product_type);
	
	@Query(value = "SELECT * FROM products WHERE product_type = :product_type ORDER BY product_name DESC", nativeQuery = true)
	public List<Products> filterProductsByNameInDESC(@Param("product_type") String product_type);
	
	List<Products> findByOrderByProductName();
	
	List<Products> findByOrderByProductNameDesc();
	
	@Query(value = "SELECT * FROM products WHERE product_type = :product_type ORDER BY product_price DESC", nativeQuery = true)
	public List<Products> filterProductsByPriceInDESC(@Param("product_type") String product_type);
	
	@Query(value = "SELECT * FROM products WHERE product_type = :product_type ORDER BY product_price", nativeQuery = true)
	public List<Products> filterProductsByPriceInASC(@Param("product_type") String product_type);
}
