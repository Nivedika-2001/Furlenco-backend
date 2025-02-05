package com.flurenco.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductsDTO {

	private int ID;
	private String productName;
	private String productType;
	private String productPrice;
	private int availableStock;
	private String productURL;	
}
