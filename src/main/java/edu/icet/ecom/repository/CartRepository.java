package edu.icet.ecom.repository;

import edu.icet.ecom.model.Cart;
import edu.icet.ecom.model.CartItems;
import java.util.List;
import java.util.UUID;

public interface CartRepository {

    Cart findCartByUserId(UUID userId);

    int save(Cart newCart);

    List<CartItems> getAllByUserId(UUID userId);

    int addToCart(CartItems cartItem);

    int updateCart(UUID cartId, UUID productId, Integer quantity);

    void deleteByProductId(UUID cartId, UUID itemId);

    void clearCart(UUID cartId);

    CartItems findByCartIdAndProductId(UUID cartId, UUID productId);

}
