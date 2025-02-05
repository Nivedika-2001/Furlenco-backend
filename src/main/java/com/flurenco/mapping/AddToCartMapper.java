package com.flurenco.mapping;

import org.springframework.stereotype.Component;
import com.flurenco.dto.CartDTO;
import com.flurenco.entity.Cart;

@Component
public class AddToCartMapper {

	/****
	 * 
	 * @param cart
	 * @return
	 * function which converts cart to cartdto
	 */
	public CartDTO toCartDTO(Cart cart)
	{
	
		CartDTO cartDTO= new CartDTO();
		cartDTO.setCardID(cart.getCardID());
		cartDTO.setProducts(cart.getProducts());
		cartDTO.setQuantity(cart.getQuantity());
		cartDTO.setUser(cart.getUser());
		return cartDTO;
	}
	/****
	 * 
	 * @param cartDTO
	 * @return
	 * function which converts cartdto to cart
	 */
	public Cart toCartEntity(CartDTO cartDTO) {
		Cart cart=new Cart();
		cart.setCardID(cartDTO.getCardID());
		cart.setProducts(cartDTO.getProducts());
		cart.setQuantity(cartDTO.getQuantity());
		cart.setUser(cartDTO.getUser());
		
		return cart;
	}
}
