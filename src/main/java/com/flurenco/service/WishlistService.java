package com.flurenco.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.flurenco.dto.WishlistDTO;
import com.flurenco.entity.Products;
import com.flurenco.entity.User;
import com.flurenco.entity.Wishlist;
import com.flurenco.exception.ProductNotFound;
import com.flurenco.exception.UserNotFound;
import com.flurenco.mapping.WishlistMapper;
import com.flurenco.repository.ProductsRepository;
import com.flurenco.repository.UserRepository;
import com.flurenco.repository.WishlistRepository;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;
    
    @Autowired
    private WishlistMapper wishlistMapper;
    
    @Autowired
    private ProductsRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(WishlistService.class);
    
    /**
     * Function to add an item to the wishlist.
     * 
     * @param phoneNo   The phone number of the user.
     * @param productID The ID of the product to add.
     * @return The added WishlistDTO object.
     * @throws ProductNotFound If the product is not found.
     * @throws UserNotFound    If the user is not found.
     */
    public WishlistDTO addItemToWishlist(long phoneNo, int productID) throws ProductNotFound, UserNotFound {
        logger.info("Adding item to wishlist for user with phone number: {}, product ID: {}", phoneNo, productID);
        Products product = productRepository.findById(productID).orElseThrow(() -> new ProductNotFound("Product with ID " + productID + " not found"));
        User user = userRepository.findById(phoneNo).orElseThrow(() -> new UserNotFound("User with phone number " + phoneNo + " not found"));
        
        boolean flag = true;
        Wishlist wishlist = wishlistRepository.findByUserAndProducts(user, product);
        if (wishlist == null) {
            wishlist = new Wishlist();
            wishlist.setProducts(product);
            wishlist.setUser(user);
            wishlistRepository.save(wishlist);
        } else {
            List<Integer> listProductID = wishlistRepository.findProductIdByUserId(phoneNo);
            for (Integer id : listProductID) {
                if (productID == id) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                wishlistRepository.save(wishlist);
            }
        }
        
        return wishlistMapper.toWishlistDTO(wishlist);
    }
    
    /**
     * Function to list all items in the wishlist.
     * 
     * @param phoneNo The phone number of the user.
     * @return A list of WishlistDTO objects.
     * @throws UserNotFound If the user is not found.
     */
    public List<WishlistDTO> listAllItems(long phoneNo) throws UserNotFound {
        logger.info("Listing all items in wishlist for user with phone number: {}", phoneNo);
        User user = userRepository.findById(phoneNo).orElseThrow(() -> new UserNotFound("User with phone number " + phoneNo + " not found"));
        List<Wishlist> wishlist = wishlistRepository.findByUser(user);
        List<WishlistDTO> wishlistdto = new ArrayList<>();
        for (Wishlist wishList : wishlist) {
            wishlistdto.add(wishlistMapper.toWishlistDTO(wishList));
        }
        return wishlistdto;
    }
    
    /**
     * Function to delete an item from the wishlist.
     * 
     * @param phoneNo   The phone number of the user.
     * @param productID The ID of the product to delete.
     * @throws UserNotFound    If the user is not found.
     * @throws ProductNotFound If the product is not found.
     */
    public void deleteItemFromWishlist(long phoneNo, int productID) throws UserNotFound, ProductNotFound {
        logger.info("Deleting item from wishlist for user with phone number: {}, product ID: {}", phoneNo, productID);
        Products product = productRepository.findById(productID).orElseThrow(() -> new ProductNotFound("Product with ID " + productID + " not found"));
        User user = userRepository.findById(phoneNo).orElseThrow(() -> new UserNotFound("User with phone number " + phoneNo + " not found"));
        Wishlist wishlist = wishlistRepository.findByUserAndProducts(user, product);
        wishlistRepository.deleteById(wishlist.getWishlistID());
    }
    
    /**
     * Function to check whether a product exists in the wishlist.
     * 
     * @param phoneNo   The phone number of the user.
     * @param productID The ID of the product to check.
     * @return The count of the product.
     * @throws ProductNotFound If the product is not found.
     * @throws UserNotFound    If the user is not found.
     */
    public int fetchProducts(long phoneNo, int productID) throws ProductNotFound, UserNotFound {
        logger.info("Fetching product from wishlist for user with phone number: {}, product ID: {}", phoneNo, productID);
        if (String.valueOf(phoneNo).length() > 0) {
            int count = wishlistRepository.existsByProductIdAndUserId(productID, phoneNo);
            if (count == 0) {
                throw new ProductNotFound("Product with ID " + productID + " not found for user with phone number " + phoneNo);
            }
            return count;
        } else {
            throw new UserNotFound("User with phone number " + phoneNo + " not found.");
        }
    }
}
