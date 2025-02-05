package com.flurenco.service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.flurenco.dto.CartDTO;
import com.flurenco.entity.Cart;
import com.flurenco.entity.Products;
import com.flurenco.entity.User;
import com.flurenco.exception.NoSuchItemInCartExists;
import com.flurenco.exception.ProductNotFound;
import com.flurenco.exception.UserNotFound;
import com.flurenco.mapping.AddToCartMapper;
import com.flurenco.repository.CartRepository;
import com.flurenco.repository.ProductsRepository;
import com.flurenco.repository.UserRepository;
import com.flurenco.serviceInterface.CartInterface;

@Service
public class CartService implements CartInterface{

    @Autowired
    private ProductsRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AddToCartMapper addToCartMapper;

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);
   
    /**
     * Function to add a product to the cart.
     * 
     * @param phoneNo The phone number of the user adding the product to the cart.
     * @param productID The ID of the product to be added to the cart.
     * @param quantity The quantity of the product to be added.
     * @return The quantity of the product added to the cart.
     * @throws UserNotFound If the user does not exist.
     * @throws ProductNotFound If the product does not exist.
     */
    public int addProductToCart(long phoneNo, int productID, int quantity) throws UserNotFound, ProductNotFound {
        logger.info("Adding product to cart...");
        Products product;
        try {
             product = productRepository.findById(productID).orElseThrow(() -> new ProductNotFound("No Such Product Exists"));
        } catch (Exception e) {
            logger.error("Error occurred while finding product: " + e.getMessage());
            throw new ProductNotFound("No Such Product Exists");
        }

        User user = userRepository.findById(phoneNo).orElse(null);
        if (user == null) {
            logger.error("User not found with phone number: " + phoneNo);
            throw new UserNotFound("No Such User Exists");
        }

        Cart cart = cartRepository.findByUserAndProducts(user, product);
        if (cart != null) {
            cart.setQuantity(quantity);
        } else {
            cart = new Cart();
            cart.setUser(user);
            cart.setQuantity(quantity);
            cart.setProducts(product);
        }
        cartRepository.save(cart);
        logger.info("Product added to cart successfully.");
        return quantity;
    }

    /**
     * Function to list all items in the cart.
     * 
     * @param phoneNo The phone number of the user whose cart items need to be listed.
     * @return A list of CartDTO objects representing items in the user's cart.
     * @throws UserNotFound If the user does not exist.
     * @throws NoSuchItemInCartExists If there are no items in the cart.
     */
    @Override
    public List<CartDTO> listCartItems(long phoneNo) throws UserNotFound, NoSuchItemInCartExists {
        logger.info("Listing items in cart...");
        User user = userRepository.findById(phoneNo).orElse(null);
        if (user == null) {
            logger.error("User not found with phone number: " + phoneNo);
            throw new UserNotFound("No Such User Exists");
        }

        List<Cart> carts = cartRepository.findByUser(user);
        if (carts == null || carts.isEmpty()) {
            logger.warn("No items found in cart for user with phone number: " + phoneNo);
            throw new NoSuchItemInCartExists("No Such Item In Cart Exists");
        }
        List<CartDTO> cartDTOs = new ArrayList<>();

        for (Cart cart : carts) {
            cartDTOs.add(addToCartMapper.toCartDTO(cart));
        }
        return cartDTOs;
    }

    /**
     * Function to get the total price of all products added to the cart.
     * 
     * @param phoneNo The phone number of the user whose cart items' total price needs to be calculated.
     * @return The total price of all products in the cart.
     * @throws UserNotFound If the user does not exist.
     * @throws NoSuchItemInCartExists If there are no items in the cart.
     * @throws ProductNotFound If a product in the cart does not exist.
     */
    public double totalPrice(long phoneNo) throws UserNotFound, NoSuchItemInCartExists, ProductNotFound {
        logger.info("Calculating total price of items in cart...");
        User user = userRepository.findById(phoneNo).orElse(null);
        double totalPrice = 0;

        if (user == null) {
            logger.error("User not found with phone number: " + phoneNo);
            throw new UserNotFound("No Such User Exists");
        }

        List<Cart> carts = cartRepository.findByUser(user);
        if (carts == null || carts.isEmpty()) {
            logger.warn("No items found in cart for user with phone number: " + phoneNo);
            throw new NoSuchItemInCartExists("No Such Item In Cart Exists");
        }
        for (Cart cart : carts) {
            int productID;
            try {
                productID = cartRepository.findProductIdByCartId(cart.getCardID());
            } catch (Exception e) {
                logger.error("Error occurred while finding product ID: " + e.getMessage());
                throw new ProductNotFound("No Such Product Exists");
            }
            String productPrice = productRepository.findPriceByProductId(productID);
            int quantity = cartRepository.findQuantityByCartIdAndProductId(cart.getCardID(), productID);
            double product_price = Double.parseDouble(productPrice.replace(",", ""));
            totalPrice += (quantity * product_price);
        }

        return totalPrice;
    }

    /**
     * Function to delete an item from the cart.
     * 
     * @param phoneNo The phone number of the user whose cart item needs to be deleted.
     * @param productID The ID of the product to be deleted from the cart.
     * @throws UserNotFound If the user does not exist.
     * @throws ProductNotFound If the product does not exist.
     * @throws NoSuchItemInCartExists If the item to be deleted does not exist in the cart.
     */
    public void deleteItemFromCart(long phoneNo, int productID) throws UserNotFound, ProductNotFound, NoSuchItemInCartExists {
        logger.info("Deleting item from cart...");
        User user = userRepository.findById(phoneNo).orElse(null);
        if (user == null) {
            logger.error("User not found with phone number: " + phoneNo);
            throw new UserNotFound("No Such User Exists");
        }
        Products product;
        try {
            product = productRepository.findById(productID).orElseThrow(() -> new ProductNotFound("No Such Product Exists"));
        } catch (Exception e) {
            logger.error("Error occurred while finding product: " + e.getMessage());
            throw new ProductNotFound("No Such Product Exists");
        }

        Cart cart = cartRepository.findByUserAndProducts(user, product);

        if (cart == null) {
            logger.warn("No such item found in cart for product ID: " + productID);
            throw new NoSuchItemInCartExists("No Such Item In Cart Exists");
        }

        cartRepository.deleteById(cart.getCardID());
        logger.info("Item deleted from cart successfully.");
    }

    /**
     * Function to get the quantity of a product in the cart.
     * 
     * @param phoneNo The phone number of the user.
     * @param productID The ID of the product.
     * @return The quantity of the specified product in the cart.
     * @throws UserNotFound If the user does not exist.
     * @throws ProductNotFound If the product does not exist.
     * @throws NoSuchItemInCartExists If the item does not exist in the cart.
     */
    public int getQuantity(long phoneNo, int productID) throws UserNotFound, ProductNotFound, NoSuchItemInCartExists {
        logger.info("Getting quantity of product in cart...");
        User user = userRepository.findById(phoneNo).orElse(null);
        if (user == null) {
            logger.error("User not found with phone number: " + phoneNo);
            throw new UserNotFound("No Such User Exists");
        }
        Products product;
        int quantity = 0;
        if (productRepository.findById(productID).isPresent()) {
            product = productRepository.findById(productID).orElseThrow(() -> new ProductNotFound("No Such Product Exists"));

            Cart cart = cartRepository.findByUserAndProducts(user, product);

            if (cart == null) {
                logger.warn("No such item found in cart for product ID: " + productID);
                throw new NoSuchItemInCartExists("No Such Item In Cart Exists");
            }
            quantity = cart.getQuantity();
        }

        return quantity;
    }

}
