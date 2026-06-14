package edu.icet.ecom.controller;

import edu.icet.ecom.model.dto.request.AddToCartRequest;
import edu.icet.ecom.model.dto.request.UpdateCartItemRequest;
import edu.icet.ecom.model.dto.response.CartItemCountResponse;
import edu.icet.ecom.model.dto.response.CartItemResponse;
import edu.icet.ecom.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public List<CartItemResponse> getAllCartItems() {
        return cartService.getAllCartItems();
    }

    @PostMapping
    public CartItemResponse addToCart(@RequestBody AddToCartRequest request) {
        return cartService.addToCart(request);
    }

    @PutMapping("/{itemId}")
    public CartItemResponse updateCartItem(@PathVariable UUID itemId, @RequestBody UpdateCartItemRequest request) {
        return cartService.updateCartItem(itemId, request);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable UUID itemId) {
        cartService.removeCartItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public CartItemCountResponse getCartItemCount(){
        return cartService.getCartItemCount();
    }
}