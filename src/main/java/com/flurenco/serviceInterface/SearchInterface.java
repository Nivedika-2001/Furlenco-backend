package com.flurenco.serviceInterface;

import java.util.List;
import com.flurenco.dto.ProductsDTO;
import com.flurenco.exception.ProductDeletionException;
import com.flurenco.exception.ProductNotFound;

public interface SearchInterface {

	public List<ProductsDTO> fetchProducts(String productName)throws ProductNotFound;
	public ProductsDTO saveproduct(ProductsDTO productdto);
	public List<ProductsDTO> listAllProducts();
	public void deleteProduct(int productID) throws ProductNotFound, ProductDeletionException;
	public List<ProductsDTO> fetchProductsByCategory(String productCategory)throws ProductNotFound;
	public List<ProductsDTO> filterProductsNameInASC(String product_type) throws ProductNotFound ;
	public List<ProductsDTO> filterProductsNameInDESC(String product_type) throws ProductNotFound ;
	public List<ProductsDTO> filterProductsPriceInASC(String product_type) throws ProductNotFound ;
	public List<ProductsDTO> filterProductsPriceInDESC(String product_type) throws ProductNotFound ;
	public ProductsDTO updateProduct(ProductsDTO productdto) throws ProductNotFound ;
}
