package com.flurenco.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.flurenco.dto.ProductsDTO;
import com.flurenco.entity.Cart;
import com.flurenco.entity.Products;
import com.flurenco.entity.Wishlist;
import com.flurenco.exception.ProductDeletionException;
import com.flurenco.exception.ProductNotFound;
import com.flurenco.mapping.SearchMapper;
import com.flurenco.repository.CartRepository;
import com.flurenco.repository.SearchRepository;
import com.flurenco.repository.WishlistRepository;
import com.flurenco.serviceInterface.SearchInterface;

@Service
public class SearchService implements SearchInterface {

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private SearchMapper searchMapper;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);

    /**
     * Fetches products based on the provided product name.
     * 
     * @param productName The name of the product to search for.
     * @return A list of ProductsDTO objects matching the provided product name.
     * @throws ProductNotFound If no products are found with the given name.
     */
    @Override
    public List<ProductsDTO> fetchProducts(String productName) throws ProductNotFound {
        logger.info("Fetching products by name: {}", productName);
        List<Products> products = searchRepository.findByProductNameContainingIgnoreCase(productName);
        if (products.isEmpty()) {
            logger.error("No products found with the given name: {}", productName);
            throw new ProductNotFound("No products found with the given name: " + productName);
        }
        return products.stream().map(searchMapper::toProductDTO).collect(Collectors.toList());
    }

    /**
     * Saves a product. If a product with the same name exists, updates its stock.
     * 
     * @param productdto The product details to be saved or updated.
     * @return The saved or updated ProductsDTO object.
     */
    public ProductsDTO saveproduct(ProductsDTO productdto) {
        logger.info("Saving product: {}", productdto);
        Products productEntity = searchMapper.toProductEntity(productdto);
        Products products = searchRepository.findByProductName(productEntity.getProductName());
        if (products != null) {
            products.setAvailableStock(products.getAvailableStock() + productEntity.getAvailableStock());
            products = searchRepository.save(products);
        } else {
            products = searchRepository.save(productEntity);
        }
        return searchMapper.toProductDTO(products);
    }

    /**
     * Updates an existing product.
     * 
     * @param productdto The product details to be updated.
     * @return The updated ProductsDTO object.
     * @throws ProductNotFound If the product to be updated is not found.
     */
    public ProductsDTO updateProduct(ProductsDTO productdto) throws ProductNotFound {
        logger.info("Updating product: {}", productdto);
        Products productEntity = searchMapper.toProductEntity(productdto);
        Products products = searchRepository.findByProductName(productEntity.getProductName());
        if (products == null) {
            throw new ProductNotFound("Product not found with the name: " + productdto.getProductName());
        }
        productEntity.setProductID(products.getProductID());
        return searchMapper.toProductDTO(searchRepository.save(productEntity));
    }

    /**
     * Lists all products.
     * 
     * @return A list of all ProductsDTO objects.
     */
    public List<ProductsDTO> listAllProducts() {
        logger.info("Listing all products");
        List<Products> productsEntity = searchRepository.findAll();
        return productsEntity.stream().map(searchMapper::toProductDTO).collect(Collectors.toList());
    }

    /**
     * Deletes a product by ID.
     * 
     * @param productID The ID of the product to be deleted.
     * @throws ProductNotFound         If the product with the given ID is not found.
     * @throws ProductDeletionException If an error occurs while deleting the product.
     */
    public void deleteProduct(int productID) throws ProductNotFound, ProductDeletionException {
        logger.info("Deleting product with ID: {}", productID);
        Products products = searchRepository.findById(productID).orElse(null);
        if (products == null) {
            throw new ProductNotFound("Product not found with ID: " + productID);
        }
        try {
            List<Cart> cartList = cartRepository.findByProducts(products);
            cartList.forEach(cartRepository::delete);
            List<Wishlist> wishlistList = wishlistRepository.findByProducts(products);
            wishlistList.forEach(wishlistRepository::delete);
            searchRepository.delete(products);
        } catch (Exception e) {
            throw new ProductDeletionException("Error occurred while deleting the product with ID: " + productID);
        }
    }

    /**
     * Fetches products based on the provided product category.
     * 
     * @param productCategory The category of the products to search for.
     * @return A list of ProductsDTO objects belonging to the provided category.
     * @throws ProductNotFound If no products are found in the given category.
     */
    public List<ProductsDTO> fetchProductsByCategory(String productCategory) throws ProductNotFound {
        logger.info("Fetching products by category: {}", productCategory);
        List<Products> productsEntity = searchRepository.findProductsByProductType(productCategory);
        if (productsEntity.isEmpty()) {
            logger.error("No products found in the category: {}", productCategory);
            throw new ProductNotFound("No products found in the category: " + productCategory);
        }
        return productsEntity.stream().map(searchMapper::toProductDTO).collect(Collectors.toList());
    }

    /**
     * Filters products by name in ascending order.
     * 
     * @param product_type The type of products to filter.
     * @return A list of ProductsDTO objects sorted by name in ascending order.
     * @throws ProductNotFound If no products are found for the given type.
     */
    public List<ProductsDTO> filterProductsNameInASC(String product_type) throws ProductNotFound {
        logger.info("Filtering products by name in ascending order for type: {}", product_type);
        List<Products> productsEntity = searchRepository.filterProductsByNameInASC(product_type);
        if (productsEntity.isEmpty()) {
            logger.error("No products found for the type: {}", product_type);
            throw new ProductNotFound("No products found for the type: " + product_type);
        }
        return productsEntity.stream().map(searchMapper::toProductDTO).collect(Collectors.toList());
    }

    /**
     * Filters products by name in descending order.
     * 
     * @param product_type The type of products to filter.
     * @return A list of ProductsDTO objects sorted by name in descending order.
     * @throws ProductNotFound If no products are found for the given type.
     */
    public List<ProductsDTO> filterProductsNameInDESC(String product_type) throws ProductNotFound {
        logger.info("Filtering products by name in descending order for type: {}", product_type);
        List<Products> productsEntity = searchRepository.filterProductsByNameInDESC(product_type);
        if (productsEntity.isEmpty()) {
            logger.error("No products found for the type: {}", product_type);
            throw new ProductNotFound("No products found for the type: " + product_type);
        }
        return productsEntity.stream().map(searchMapper::toProductDTO).collect(Collectors.toList());
    }

    /**
     * Filters products by price in ascending order.
     * 
     * @param product_type The type of products to filter.
     * @return A list of ProductsDTO objects sorted by price in ascending order.
     * @throws ProductNotFound If no products are found for the given type.
     */
    public List<ProductsDTO> filterProductsPriceInASC(String product_type) throws ProductNotFound {
        logger.info("Filtering products by price in ascending order for type: {}", product_type);
        List<Products> productsEntity = searchRepository.filterProductsByPriceInASC(product_type);
        if (productsEntity.isEmpty()) {
            logger.error("No products found for the type: {}", product_type);
            throw new ProductNotFound("No products found for the type: " + product_type);
        }
        return mapAndSortProducts(productsEntity, true);
    }

    /**
     * Filters products by price in descending order.
     * 
     * @param product_type The type of products to filter.
     * @return A list of ProductsDTO objects sorted by price in descending order.
     * @throws ProductNotFound If no products are found for the given type.
     */
    public List<ProductsDTO> filterProductsPriceInDESC(String product_type) throws ProductNotFound {
        logger.info("Filtering products by price in descending order for type: {}", product_type);
        List<Products> productsEntity = searchRepository.filterProductsByPriceInDESC(product_type);
        if (productsEntity.isEmpty()) {
            logger.error("No products found for the type: {}", product_type);
            throw new ProductNotFound("No products found for the type: " + product_type);
        }
        return mapAndSortProducts(productsEntity, false);
    }

    /**
     * Maps products to DTOs and sorts them based on price.
     * 
     * @param productsEntity The list of Products entities.
     * @param ascending      Boolean value indicating ascending or descending order.
     * @return A sorted list of ProductsDTO objects.
     */
    private List<ProductsDTO> mapAndSortProducts(List<Products> productsEntity, boolean ascending) {
        List<ProductsDTO> productsDTO = productsEntity.stream().map(searchMapper::toProductDTO)
                .collect(Collectors.toList());

        Collections.sort(productsDTO,
                Comparator.comparing(product -> new BigDecimal(product.getProductPrice().replace(",", ""))));

        if (!ascending) {
            Collections.reverse(productsDTO);
        }

        return productsDTO;
    }
}
