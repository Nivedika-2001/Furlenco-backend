package com.flurenco.serviceInterface;

import java.util.List;
import com.flurenco.dto.CartDTO;
import com.flurenco.exception.NoSuchItemInCartExists;
import com.flurenco.exception.ProductNotFound;
import com.flurenco.exception.UserNotFound;


public interface CartInterface {

	public List<CartDTO> listCartItems(long phoneNo)  throws UserNotFound,ProductNotFound,NoSuchItemInCartExists;
	public int addProductToCart(long phoneNo,int productID,int quantity) throws UserNotFound,ProductNotFound;
	public void deleteItemFromCart(long phoneNo,int productID) throws UserNotFound,ProductNotFound,NoSuchItemInCartExists;
	public double totalPrice(long phoneNo) throws UserNotFound,NoSuchItemInCartExists,ProductNotFound;
	public int getQuantity(long phoneNo,int productID)throws UserNotFound,ProductNotFound,NoSuchItemInCartExists;
}
