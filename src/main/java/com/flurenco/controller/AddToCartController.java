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
import com.flurenco.dto.CartDTO;
import com.flurenco.exception.NoSuchItemInCartExists;
import com.flurenco.exception.ProductNotFound;
import com.flurenco.exception.UserNotFound;
import com.flurenco.service.CartService;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*")
public class AddToCartController {

    @Autowired
    private CartService cartService;

    /**
     * Adds a product to the user's cart.
     * 
     * @param phoneNo   The user's phone number.
     * @param productID The ID of the product to add.
     * @param quantity  The quantity of the product.
     * @return The added quantity of the product.
     * @throws UserNotFound    If the user is not found.
     * @throws ProductNotFound If the product is not found.
     * @throws Exception       If an unexpected error occurs.
     */
    @PostMapping("/add/{phoneNo}/{productID}/{quantity}")
    public ResponseEntity<Integer> addProductToCart(@PathVariable long phoneNo, @PathVariable int productID,
            @PathVariable int quantity) throws UserNotFound, ProductNotFound, Exception {
        Integer addedQuantity = null;
        try {
            addedQuantity = cartService.addProductToCart(phoneNo, productID, quantity);
            return new ResponseEntity<Integer>(addedQuantity, HttpStatus.ACCEPTED);
        } catch (UserNotFound e) {
            throw new UserNotFound(e.getMessage(), e);
        } catch (ProductNotFound e) {
            throw new ProductNotFound(e.getMessage(), e);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * Retrieves all items in the user's cart.
     * 
     * @param phoneNo The user's phone number.
     * @return A list of CartDTO objects representing the items in the cart.
     * @throws UserNotFound           If the user is not found.
     * @throws NoSuchItemInCartExists If no items exist in the cart.
     * @throws Exception              If an unexpected error occurs.
     */
    @GetMapping("/getAll/{phoneNo}")
    public ResponseEntity<List<CartDTO>> listCartItems(@PathVariable long phoneNo)
            throws UserNotFound, NoSuchItemInCartExists, Exception {
        try {
            return new ResponseEntity<List<CartDTO>>(cartService.listCartItems(phoneNo), HttpStatus.ACCEPTED);
        } catch (UserNotFound e) {
            throw new UserNotFound(e.getMessage(), e);
        } catch (NoSuchItemInCartExists e) {
            throw new NoSuchItemInCartExists(e.getMessage(), e);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * Deletes an item from the user's cart.
     * 
     * @param phoneNo   The user's phone number.
     * @param productID The ID of the product to delete from the cart.
     * @throws UserNotFound           If the user is not found.
     * @throws ProductNotFound        If the product is not found.
     * @throws NoSuchItemInCartExists If no such item exists in the cart.
     * @throws Exception              If an unexpected error occurs.
     */
    @DeleteMapping("/deleteItem/{phoneNo}/{productID}")
    public void deleteItemFromCart(@PathVariable long phoneNo, @PathVariable int productID)
            throws UserNotFound, ProductNotFound, NoSuchItemInCartExists, Exception {
        try {
            cartService.deleteItemFromCart(phoneNo, productID);
        } catch (UserNotFound e) {
            throw new UserNotFound(e.getMessage(), e);
        } catch (ProductNotFound e) {
            throw new ProductNotFound(e.getMessage(), e);
        } catch (NoSuchItemInCartExists e) {
            throw new NoSuchItemInCartExists(e.getMessage(), e);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * Retrieves the total price of items in the user's cart.
     * 
     * @param phoneNo The user's phone number.
     * @return The total price of items in the cart.
     * @throws UserNotFound           If the user is not found.
     * @throws NoSuchItemInCartExists If no items exist in the cart.
     * @throws ProductNotFound        If a product is not found.
     * @throws Exception              If an unexpected error occurs.
     */
    @GetMapping("/totalPrice/{phoneNo}")
    public ResponseEntity<Double> totalPrice(@PathVariable long phoneNo)
            throws UserNotFound, NoSuchItemInCartExists, ProductNotFound, Exception {
        try {
            return new ResponseEntity<Double>(cartService.totalPrice(phoneNo), HttpStatus.ACCEPTED);
        } catch (UserNotFound e) {
            throw new UserNotFound(e.getMessage(), e);
        } catch (NoSuchItemInCartExists e) {
            throw new NoSuchItemInCartExists(e.getMessage(), e);
        } catch (ProductNotFound e) {
            throw new ProductNotFound(e.getMessage(), e);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * Retrieves the quantity of a specific product in the user's cart.
     * 
     * @param phoneNo   The user's phone number.
     * @param productID The ID of the product.
     * @return The quantity of the specified product in the cart.
     * @throws UserNotFound           If the user is not found.
     * @throws NoSuchItemInCartExists If no items exist in the cart.
     * @throws ProductNotFound        If the product is not found.
     * @throws Exception              If an unexpected error occurs.
     */
    @GetMapping("/quantity/{phoneNo}/{productID}")
    public ResponseEntity<Integer> getQuantity(@PathVariable long phoneNo, @PathVariable int productID)
            throws UserNotFound, NoSuchItemInCartExists, ProductNotFound, Exception {
        try {
            return new ResponseEntity<Integer>(cartService.getQuantity(phoneNo, productID), HttpStatus.ACCEPTED);
        } catch (UserNotFound e) {
            throw new UserNotFound(e.getMessage(), e);
        } catch (NoSuchItemInCartExists e) {
            throw new NoSuchItemInCartExists(e.getMessage(), e);
        } catch (ProductNotFound e) {
            throw new ProductNotFound(e.getMessage(), e);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
