package edu.icet.ecom.service;

import edu.icet.ecom.model.Cart;
import edu.icet.ecom.model.User;
import edu.icet.ecom.model.dto.request.AddToCartRequest;
import edu.icet.ecom.model.dto.request.UpdateCartItemRequest;
import edu.icet.ecom.model.dto.response.CartItemCountResponse;
import edu.icet.ecom.model.dto.response.CartItemResponse;

import java.util.List;
import java.util.UUID;

public interface CartService {

    public Cart getOrCreateCart(UUID userId);

    List<CartItemResponse> getAllCartItems();

    CartItemResponse addToCart(AddToCartRequest request);

    CartItemResponse updateCartItem(UUID itemId, UpdateCartItemRequest request);

    void removeCartItem(UUID itemId);

    void clearCart();

    CartItemCountResponse getCartItemCount();
}
