package com.app.mfi2.controller;

import com.app.mfi2.dto.CartDto;
import com.app.mfi2.dto.ChangeStatusCartDto;
import com.app.mfi2.dto.ItemDto;
import com.app.mfi2.dto.ListItemDto;
import com.app.mfi2.model.Cart;
import com.app.mfi2.service.CartService;
import com.app.mfi2.utils.MapperDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Cart controller.
 */
@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Gestion du panier")
@CrossOrigin(value = "*")
public class CartController {
    private final CartService cartService;

    /**
     * Add item to cart response entity.
     *
     * @param itemDto the item dto
     * @return the response entity
     */
    @PostMapping
    @ResponseBody
    @Operation(summary = "Ajouter un item dans le panier")
    public ResponseEntity<CartDto> addItemToCart(@RequestBody ItemDto itemDto) {
        Cart cart = cartService.addItemToCart(itemDto.getUserId(), itemDto.getProductId(), itemDto.getQuantity());
        if (cart != null) {
            return ResponseEntity.ok(MapperDTO.convertToDto(cart, CartDto.class));
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Remove item from cart response entity.
     *
     * @param itemDto the item dto
     * @return the response entity
     */
    @DeleteMapping
    @ResponseBody
    @Operation(summary = "Supprimer un item dans le panier")
    public ResponseEntity<CartDto> removeItemFromCart(@RequestBody ItemDto itemDto) {
        Cart cart = cartService.removeItemFromCart(itemDto.getUserId(), itemDto.getProductId(), itemDto.getQuantity());
        if (cart != null) {
            return ResponseEntity.ok(MapperDTO.convertToDto(cart, CartDto.class));
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Gets cart by id.
     *
     * @param userId the user id
     * @return the cart by id
     */
    @GetMapping("/{userId}")
    @ResponseBody
    @Operation(summary = "Trouver un panier grave à son userId")
    public ResponseEntity<CartDto> getCartById(@PathVariable("userId") Long userId) {
        Cart cart = cartService.getCartByClientId(userId);
        if (cart != null) {
            return ResponseEntity.ok(MapperDTO.convertToDto(cart, CartDto.class));
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Clear cart response entity.
     *
     * @param userId the user id
     * @return the response entity
     */
    @DeleteMapping("/{userId}/clear")
    @ResponseBody
    @Operation(summary = "Vider le panier")
    public ResponseEntity<?> clearCart(@PathVariable("userId") Long userId) {
        boolean isCleared = cartService.clearCart(userId);
        if (isCleared) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Update cart status response entity.
     *
     * @param req the req
     * @return the response entity
     */
    @PutMapping("/status")
    @ResponseBody
    @Operation(summary = "Changer le status du panier")
    public ResponseEntity<CartDto> updateCartStatus(@RequestBody ChangeStatusCartDto req) {
        Cart cart = cartService.changeStatus(req.getUserId(), req.getStatus());
        if (cart != null) {
            return ResponseEntity.ok(MapperDTO.convertToDto(cart, CartDto.class));
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Gets items.
     *
     * @param userId the user id
     * @return the items
     */
    @GetMapping("/{userId}/items")
    @ResponseBody
    @Operation(summary = "Liste des produits du panier")
    public ResponseEntity<List<ListItemDto>> getItems(@PathVariable("userId") Long userId) {
        List<ListItemDto> itemDtos = cartService.getItemsFromCart(userId);
        if (itemDtos.isEmpty()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(itemDtos);
    }
}
