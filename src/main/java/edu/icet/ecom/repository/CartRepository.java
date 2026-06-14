package edu.icet.ecom.repository;

import edu.icet.ecom.model.Cart;
import edu.icet.ecom.model.CartItems;
import edu.icet.ecom.model.dto.response.CartItemCountResponse;
import edu.icet.ecom.model.dto.response.CartItemResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository {

    Cart findCartByUserId(UUID userId);

    int save(Cart newCart);

    List<CartItems> getAll();

    int addToCart(CartItems cartItem);

    int updateCart(UUID itemId, CartItems cartItem);

    void deleteByProductId(UUID cartId, UUID itemId);

    void clearCart(UUID cartId);

    CartItemCountResponse getItemCount();
}
