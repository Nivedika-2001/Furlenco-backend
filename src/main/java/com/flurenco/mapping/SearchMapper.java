package com.flurenco.mapping;

import org.springframework.stereotype.Component;
import com.flurenco.dto.ProductsDTO;
import com.flurenco.entity.Products;

@Component
public class SearchMapper {

	/****
	 * 
	 * @param product
	 * @return
	 * function which converts product to productdto
	 */
	public ProductsDTO toProductDTO(Products product)
	{
		ProductsDTO productDTO= new ProductsDTO();
		productDTO.setID(product.getProductID());
		productDTO.setProductName(product.getProductName());
		productDTO.setProductPrice(product.getProductPrice());
		productDTO.setProductType(product.getProductType());
		productDTO.setAvailableStock(product.getAvailableStock());
		productDTO.setProductURL(product.getProductURL());
		return productDTO;
	}
	/****
	 * 
	 * @param productDTO
	 * @return
	 * function which converts productdto to product
	 */
	public Products toProductEntity(ProductsDTO productDTO) {
		Products product=new Products();
		product.setProductID(productDTO.getID());
		product.setProductName(productDTO.getProductName());
		product.setProductPrice(productDTO.getProductPrice());
		product.setProductType(productDTO.getProductType());
		product.setAvailableStock(productDTO.getAvailableStock());
		product.setProductURL(productDTO.getProductURL());
		return product;
	}
}
