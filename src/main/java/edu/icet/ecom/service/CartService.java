package edu.icet.ecom.service;

import edu.icet.ecom.model.Cart;
import edu.icet.ecom.model.dto.request.AddToCartRequest;
import edu.icet.ecom.model.dto.request.UpdateCartItemRequest;
import edu.icet.ecom.model.dto.response.CartItemCountResponse;
import edu.icet.ecom.model.dto.response.CartItemResponse;
import java.util.List;
import java.util.UUID;

public interface CartService {

    public Cart getOrCreateCart(UUID userId);

    List<CartItemResponse> getAllCartItems(UUID userId);

    CartItemResponse addToCart(AddToCartRequest request,UUID userId);

    CartItemResponse updateCartItem(UUID cartId, UUID itemId, UpdateCartItemRequest request);

    void removeCartItem(UUID userId, UUID itemId);

    void clearCart(UUID userId);

    CartItemCountResponse getCartItemCount(UUID userId);
}
