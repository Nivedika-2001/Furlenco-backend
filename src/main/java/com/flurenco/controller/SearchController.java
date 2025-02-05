package com.flurenco.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flurenco.dto.ProductsDTO;
import com.flurenco.exception.ProductDeletionException;
import com.flurenco.exception.ProductNotFound;
import com.flurenco.service.SearchService;

@RestController
@RequestMapping("/search")
@CrossOrigin(origins = "*")
public class SearchController {

    @Autowired
    private SearchService searchService;


    /**
     * Fetches products based on the provided product name.
     * 
     * @param productName The name of the product to search for.
     * @return A list of ProductsDTO objects matching the provided product name.
     * @throws ProductNotFound If no products are found with the given name.
     */
    @GetMapping("/fetch/{productName}")
    public ResponseEntity<List<ProductsDTO>> fetchProducts(@PathVariable String productName) throws ProductNotFound {
        try {
            List<ProductsDTO> products = searchService.fetchProducts(productName);
            return new ResponseEntity<>(products, HttpStatus.ACCEPTED);
        } catch (ProductNotFound e) {
            throw new ProductNotFound(e.getMessage(), e);
        }
    }

    /**
     * Saves a new product.
     * 
     * @param productdto The product details to be saved.
     * @return The saved ProductsDTO object.
     */
    @PostMapping("/addProduct")
    public ResponseEntity<ProductsDTO> saveproduct(@RequestBody ProductsDTO productdto) {
        return new ResponseEntity<>(searchService.saveproduct(productdto), HttpStatus.ACCEPTED);
    }

    /**
     * Retrieves a list of all products.
     * 
     * @return A list of all products.
     */
    @GetMapping("/listAllProducts")
    public ResponseEntity<List<ProductsDTO>> listAllProducts() {
        return new ResponseEntity<>(searchService.listAllProducts(), HttpStatus.ACCEPTED);
    }

    /**
     * Deletes a product with the given ID.
     * 
     * @param productID The ID of the product to delete.
     * @return A message indicating the result of the deletion.
     * @throws ProductNotFound        If no product is found with the given ID.
     * @throws ProductDeletionException If an error occurs while deleting the product.
     */
    @DeleteMapping("deleteProduct/{productID}")
    public ResponseEntity<String> deleteProduct(@PathVariable int productID) throws ProductNotFound, ProductDeletionException {
        try {
            searchService.deleteProduct(productID);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.ACCEPTED);
        } catch (ProductNotFound e) {
            throw new ProductNotFound(e.getMessage(), e);
        } catch (ProductDeletionException e) {
            throw new ProductDeletionException(e.getMessage(), e);
        }
    }

    /**
     * Fetches products based on the provided product category.
     * 
     * @param productCategory The category of the products to fetch.
     * @return A list of ProductsDTO objects belonging to the provided category.
     * @throws ProductNotFound If no products are found in the given category.
     */
    @GetMapping("getProductsByCategory/{productCategory}")
    public ResponseEntity<List<ProductsDTO>> fetchProductsByCategory(@PathVariable String productCategory) throws ProductNotFound {
        try {
            return new ResponseEntity<>(searchService.fetchProductsByCategory(productCategory), HttpStatus.ACCEPTED);
        } catch (ProductNotFound e) {
            throw new ProductNotFound(e.getMessage(), e);
        }
    }

    /**
     * Filters products by name in ascending order.
     * 
     * @param product_type The type of products to filter.
     * @return A list of ProductsDTO objects filtered by name in ascending order.
     * @throws ProductNotFound If no products are found for the given type.
     */
    @GetMapping("filterProductsNameInASC/{product_type}")
    public ResponseEntity<List<ProductsDTO>> filterProductsNameInASC(@PathVariable String product_type) throws ProductNotFound {
        try {
            return new ResponseEntity<>(searchService.filterProductsNameInASC(product_type), HttpStatus.ACCEPTED);
        } catch (ProductNotFound e) {
            throw new ProductNotFound(e.getMessage(), e);
        }
    }

    /**
     * Filters products by name in descending order.
     * 
     * @param product_type The type of products to filter.
     * @return A list of ProductsDTO objects filtered by name in descending order.
     * @throws ProductNotFound If no products are found for the given type.
     */
    @GetMapping("filterProductsNameInDESC/{product_type}")
    public ResponseEntity<List<ProductsDTO>> filterProductsNameInDESC(@PathVariable String product_type) throws ProductNotFound {
        try {
            // Retrieve products filtered by name in descending order
            List<ProductsDTO> products = searchService.filterProductsNameInDESC(product_type);
            // Return the filtered products with an HTTP status of ACCEPTED
            return new ResponseEntity<>(products, HttpStatus.ACCEPTED);
        } catch (ProductNotFound e) {
            // Throw an exception with a custom message if no products are found
            throw new ProductNotFound(e.getMessage(), e);
        }
    }

    /**
     * Filters products by price in ascending order.
     * 
     * @param product_type The type of products to filter.
     * @return A list of ProductsDTO objects filtered by price in ascending order.
     * @throws ProductNotFound If no products are found for the given type.
     */
    @GetMapping("filterProductsPriceInASC/{product_type}")
    public ResponseEntity<List<ProductsDTO>> filterProductsPriceInASC(@PathVariable String product_type) throws ProductNotFound {
        try {
            // Retrieve products filtered by price in ascending order
            List<ProductsDTO> products = searchService.filterProductsPriceInASC(product_type);
            // Return the filtered products with an HTTP status of ACCEPTED
            return new ResponseEntity<>(products, HttpStatus.ACCEPTED);
        } catch (ProductNotFound e) {
            // Throw an exception with a custom message if no products are found
            throw new ProductNotFound(e.getMessage(), e);
        }
    }

    /**
     * Filters products by price in descending order.
     * 
     * @param product_type The type of products to filter.
     * @return A list of ProductsDTO objects filtered by price in descending order.
     * @throws ProductNotFound If no products are found for the given type.
     */
    @GetMapping("filterProductsPriceInDESC/{product_type}")
    public ResponseEntity<List<ProductsDTO>> filterProductsPriceInDESC(@PathVariable String product_type) throws ProductNotFound {
        try {
            // Retrieve products filtered by price in descending order
            List<ProductsDTO> products = searchService.filterProductsPriceInDESC(product_type);
            // Return the filtered products with an HTTP status of ACCEPTED
            return new ResponseEntity<>(products, HttpStatus.ACCEPTED);
        } catch (ProductNotFound e) {
            // Throw an exception with a custom message if no products are found
            throw new ProductNotFound(e.getMessage(), e);
        }
    }

    /**
     * Updates a product.
     * 
     * @param productdto The product details to update.
     * @return The updated ProductsDTO object.
     * @throws ProductNotFound If the product to update is not found.
     */
    @PutMapping("updateProduct")
    public ResponseEntity<ProductsDTO> updateProduct(@RequestBody ProductsDTO productdto) throws ProductNotFound {
        try {
            // Update the product details and return the updated product with an HTTP status of ACCEPTED
            return new ResponseEntity<>(searchService.updateProduct(productdto), HttpStatus.ACCEPTED);
        } catch (ProductNotFound e) {
            // Throw an exception with a custom message if the product is not found
            throw new ProductNotFound(e.getMessage(), e);
        }
    }

}
