package com.flurenco.mapping;

import org.springframework.stereotype.Component;
import com.flurenco.dto.WishlistDTO;
import com.flurenco.entity.Wishlist;

@Component
public class WishlistMapper {

	/****
	 * 
	 * @param wishlist
	 * @return
	 * function which converts wishlist to wishlistdto
	 */
		public WishlistDTO toWishlistDTO(Wishlist wishlist)
		{
		
			WishlistDTO wishlistDTO= new WishlistDTO();
			wishlistDTO.setWishlistID(wishlist.getWishlistID());
			wishlistDTO.setProducts(wishlist.getProducts());
			wishlistDTO.setUser(wishlist.getUser());
			return wishlistDTO;
		}
		/****
		 * 
		 * @param wishlistDTO
		 * @return
		 * function which converts wishlistdto to wishlist
		 */
		public Wishlist toWishlistEntity(WishlistDTO wishlistDTO) {
			Wishlist wishlist=new Wishlist();
			wishlist.setWishlistID(wishlistDTO.getWishlistID());
			wishlist.setProducts(wishlistDTO.getProducts());
			wishlist.setUser(wishlistDTO.getUser());
			
			return wishlist;
		}
}
