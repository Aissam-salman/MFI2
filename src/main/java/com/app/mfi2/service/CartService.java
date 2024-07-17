package com.app.mfi2.service;

import com.app.mfi2.dto.ListItemDto;
import com.app.mfi2.model.Cart;
import com.app.mfi2.model.ECartStatus;
import com.app.mfi2.model.Item;
import com.app.mfi2.model.Product;
import com.app.mfi2.repository.CartRepository;
import com.app.mfi2.repository.ProductRepository;
import com.app.mfi2.user.model.Client;
import com.app.mfi2.user.model.User;
import com.app.mfi2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * The type Cart service.
 */
@Service
@RequiredArgsConstructor
public class CartService {
    private static final Logger log = LoggerFactory.getLogger(CartService.class);
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    /**
     * Search cart by ClientId and create item to add in cart, finally save in DB
     *
     * @param userId    the user id
     * @param productId the product id
     * @param quantity  the quantity
     * @return the cart
     */
    @Transactional
    public Cart addItemToCart(Long userId, Long productId, int quantity) {
        Cart cart = cartRepository.findCartByClientId(userId);
        log.debug("\uD83D\uDE00 addItemToCart cart = {}", cart);
        if (cart == null) {
            Optional<User> userData = userRepository.findById(userId);
            if (userData.isPresent()) {
                Client _client = (Client) userData.get();
                cart = Cart.builder()
                        .client(_client)
                        .status(ECartStatus.NEW)
                        .date(new Date())
                        .items(new ArrayList<>())
                        .build();
            } else {
                return null;
            }
        }

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        Item item = Item.builder()
                .product(product)
                .quantity(quantity)
                .build();

        cart.addItem(item);
        return cartRepository.save(cart);
    }

    /**
     * Remove item from Cart or decrease quantity.
     *
     * @param userId           the user id
     * @param productId        the product id
     * @param quantityToRemove the quantity to remove
     * @return the cart
     */
    @Transactional
    public Cart removeItemFromCart(Long userId, Long productId, int quantityToRemove) {
        Cart cart = cartRepository.findCartByClientId(userId);
        if (cart == null) {
            return null;
        }
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        Item itemToRemove = null;

        for (Item item : cart.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                if (item.getQuantity() > quantityToRemove) {
                    item.setQuantity(item.getQuantity() - quantityToRemove);
                    return cartRepository.save(cart);
                } else {
                    itemToRemove = item;
                }
            }
        }

        if (itemToRemove != null) {
            cart.removeItem(itemToRemove);
        } else {
            throw new RuntimeException("Product not found in Cart");
        }
        return cartRepository.save(cart);
    }

    /**
     * Gets cart by client id.
     *
     * @param userId the user id
     * @return the cart by client id
     */
    @Transactional(readOnly = true)
    public Cart getCartByClientId(Long userId) {
        return cartRepository.findCartByClientId(userId);
    }

    /**
     * Clear cart.
     *
     * @param userId the user id
     * @return boolean boolean
     */
    @Transactional
    public boolean clearCart(Long userId) {
        Cart cart = cartRepository.findCartByClientId(userId);
        if (cart != null && cart.getStatus() != ECartStatus.ARCHIVED) {
            cart.getItems().clear();
            cartRepository.save(cart);
            return true;
        }
        return false;
    }

    /**
     * Change status cart.
     *
     * @param userId the user id
     * @param status the status
     * @return the cart
     */
    @Transactional
    public Cart changeStatus(Long userId, ECartStatus status) {
        Cart cart = cartRepository.findCartByClientId(userId);
        if (cart == null) {
            return null;
        }
        cart.setStatus(status);
        return cartRepository.save(cart);
    }

    /**
     * Gets items from cart.
     *
     * @param userId the user id
     * @return the items from cart
     */
    @Transactional(readOnly = true)
    public List<ListItemDto> getItemsFromCart(Long userId) {
        Cart cart = cartRepository.findCartByClientId(userId);
        if (cart == null) {
            return new ArrayList<>();
        }

        List<ListItemDto> items = new ArrayList<>();
        cart.getItems().forEach(item -> {
            items.add(new ListItemDto(item));
        });

        return items;
    }
}
