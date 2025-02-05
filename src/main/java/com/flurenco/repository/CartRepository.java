package com.flurenco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.flurenco.entity.Cart;
import com.flurenco.entity.User;
import java.util.List;
import com.flurenco.entity.Products;


@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{

	public Cart findByUserAndProducts(User user, Products products);
	public List<Cart> findByUser(User user);
	public List<Cart> findByProducts(Products products);
	
	@Query(value = "SELECT quantity FROM cart WHERE cardid = :cartId AND productid = :productId", nativeQuery = true)
	public int findQuantityByCartIdAndProductId(@Param("cartId") int cartId, @Param("productId") int productId);

	@Query(value = "SELECT productid FROM cart WHERE cardid = :cartId", nativeQuery = true)
	public int findProductIdByCartId(@Param("cartId") int cartId);

	
}
