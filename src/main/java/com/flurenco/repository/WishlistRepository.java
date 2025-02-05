package com.flurenco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.flurenco.entity.Wishlist;
import com.flurenco.entity.User;
import com.flurenco.entity.Products;
import java.util.List;



@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer>{

	Wishlist findByUserAndProducts(User user, Products products);
	List<Wishlist> findByUser(User user);
	List<Wishlist> findByProducts(Products products);
	
	@Query(value = "SELECT productid FROM wishlist WHERE userid = :phoneNo", nativeQuery = true)
	public List<Integer> findProductIdByUserId(@Param("phoneNo") long phoneNo);
	
	@Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END FROM wishlist WHERE productid = :productId AND userid = :phoneNo", nativeQuery = true)
    int existsByProductIdAndUserId(@Param("productId") int productId, @Param("phoneNo") long phoneNo);
}
