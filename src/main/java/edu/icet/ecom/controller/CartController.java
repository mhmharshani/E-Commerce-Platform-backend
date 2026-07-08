package edu.icet.ecom.controller;

import edu.icet.ecom.model.dto.request.AddToCartRequest;
import edu.icet.ecom.model.dto.request.UpdateCartItemRequest;
import edu.icet.ecom.model.dto.response.CartItemCountResponse;
import edu.icet.ecom.model.dto.response.CartItemResponse;
import edu.icet.ecom.service.CartService;
import edu.icet.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @GetMapping
    public List<CartItemResponse> getAllCartItems(@AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = userService.getCurrentUserId(userDetails.getUsername());
        return cartService.getAllCartItems(userId);
    }

    @PostMapping
    public CartItemResponse addToCart(@RequestBody AddToCartRequest request,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = userService.getCurrentUserId(userDetails.getUsername());
        return cartService.addToCart(
                request,
                userId
        );
    }

    @PutMapping("/{itemId}")
    public CartItemResponse updateCartItem(@PathVariable UUID itemId, @RequestBody UpdateCartItemRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = userService.getCurrentUserId(userDetails.getUsername());
        return cartService.updateCartItem(itemId, userId, request);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable UUID itemId,@AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = userService.getCurrentUserId(userDetails.getUsername());
        cartService.removeCartItem(userId, itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = userService.getCurrentUserId(userDetails.getUsername());
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public CartItemCountResponse getCartItemCount(@AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = userService.getCurrentUserId(userDetails.getUsername());
        return cartService.getCartItemCount(userId);
    }
}