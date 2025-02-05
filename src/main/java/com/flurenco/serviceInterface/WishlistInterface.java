package com.flurenco.serviceInterface;

import java.util.List;
import com.flurenco.dto.WishlistDTO;
import com.flurenco.exception.ProductNotFound;
import com.flurenco.exception.UserNotFound;

public interface WishlistInterface {

	public WishlistDTO addItemToWishlist(long phoneNo,int productID) throws ProductNotFound, UserNotFound;
	public void deleteItemFromWishlist(long phoneNo,int productID) throws ProductNotFound, UserNotFound;
	public List<WishlistDTO> listAllItems(long phoneNo) throws UserNotFound ;
	public int fetchProducts(long phoneNo,int productID) throws ProductNotFound, UserNotFound ;
}
