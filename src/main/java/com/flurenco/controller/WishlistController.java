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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flurenco.dto.WishlistDTO;
import com.flurenco.service.WishlistService;
import com.flurenco.exception.UserNotFound; 
import com.flurenco.exception.ProductNotFound; 

@RestController
@RequestMapping("/wishlist")
@CrossOrigin(origins = "*")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;
    
    /**
     * Adds an item to the wishlist.
     * 
     * @param phoneNo   The phone number of the user.
     * @param productID The ID of the product to add to the wishlist.
     * @return The added WishlistDTO object.
     * @throws UserNotFound    If the user is not found.
     * @throws ProductNotFound If the product is not found.
     */
    @PostMapping("/add/{phoneNo}/{productID}")
    public ResponseEntity<WishlistDTO> addItemToWishlist(@PathVariable long phoneNo, @PathVariable int productID) throws UserNotFound, ProductNotFound{
        try {
            return new ResponseEntity<WishlistDTO>(wishlistService.addItemToWishlist(phoneNo, productID), HttpStatus.ACCEPTED);
        } catch (UserNotFound e) {
            throw new UserNotFound(e.getMessage(),e);
        } catch (ProductNotFound e) {
            throw new ProductNotFound(e.getMessage(),e);
        }
    }
    
    /**
     * Lists all items in the wishlist for a given user.
     * 
     * @param phoneNo The phone number of the user.
     * @return A list of WishlistDTO objects representing the items in the wishlist.
     * @throws UserNotFound If the user is not found.
     */
    @GetMapping("/list/{phoneNo}")
    public ResponseEntity<List<WishlistDTO>> listAllItems(@PathVariable long phoneNo) throws UserNotFound {
        try {
            return new ResponseEntity<List<WishlistDTO>>(wishlistService.listAllItems(phoneNo), HttpStatus.ACCEPTED);
        } catch (UserNotFound e) {
            throw new UserNotFound(e.getMessage(),e);
        }
    }
    
    /**
     * Deletes an item from the wishlist.
     * 
     * @param phoneNo   The phone number of the user.
     * @param productID The ID of the product to delete from the wishlist.
     * @throws UserNotFound    If the user is not found.
     * @throws ProductNotFound If the product is not found.
     */
    @DeleteMapping("/delete/{phoneNo}/{productID}")
    public void deleteItemFromWishlist(@PathVariable long phoneNo, @PathVariable int productID) throws UserNotFound, ProductNotFound {
        try {
            wishlistService.deleteItemFromWishlist(phoneNo, productID);
        } catch (UserNotFound e) {
            throw new UserNotFound(e.getMessage(),e);
        } catch (ProductNotFound e) {
            throw new ProductNotFound(e.getMessage(),e);
        }
    }
    
    /**
     * Fetches the quantity of a specific product in the wishlist.
     * 
     * @param phoneNo   The phone number of the user.
     * @param productID The ID of the product to fetch the quantity for.
     * @return The quantity of the specified product in the wishlist.
     * @throws UserNotFound    If the user is not found.
     * @throws ProductNotFound If the product is not found.
     */
    @GetMapping("user/{phoneNo}/{productID}")
    public ResponseEntity<Integer> fetchProducts(@PathVariable long phoneNo, @PathVariable int productID) throws UserNotFound, ProductNotFound{
        try {
            return new ResponseEntity<Integer>(wishlistService.fetchProducts(phoneNo, productID), HttpStatus.ACCEPTED);
        } catch (UserNotFound e) {
            throw new UserNotFound(e.getMessage(),e);
        } catch (ProductNotFound e) {
            throw new ProductNotFound(e.getMessage(),e);
        }
    }
}
