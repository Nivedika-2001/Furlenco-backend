package com.flurenco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.flurenco.entity.Products;



@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer>{


	@Query(value = "SELECT product_price FROM products WHERE productid = :productId", nativeQuery = true)
	String findPriceByProductId(@Param("productId") int productId);
	
	

}
