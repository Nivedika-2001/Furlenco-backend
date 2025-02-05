package com.flurenco.dto;

import com.flurenco.entity.Products;
import com.flurenco.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WishlistDTO {

	private int wishlistID;
	private Products products;
	private User user;
}
