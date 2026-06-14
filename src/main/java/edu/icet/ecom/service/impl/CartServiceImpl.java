package edu.icet.ecom.service.impl;

import edu.icet.ecom.model.Cart;
import edu.icet.ecom.model.CartItems;
import edu.icet.ecom.model.User;
import edu.icet.ecom.model.dto.request.AddToCartRequest;
import edu.icet.ecom.model.dto.request.UpdateCartItemRequest;
import edu.icet.ecom.model.dto.response.CartItemCountResponse;
import edu.icet.ecom.model.dto.response.CartItemResponse;
import edu.icet.ecom.repository.CartRepository;
import edu.icet.ecom.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository repository;

    @Override
    public Cart getOrCreateCart(UUID userId) {

        LocalDateTime now = Timestamp.from(Instant.now()).toLocalDateTime();

        Cart cart = repository.findCartByUserId(userId);
        if(cart == null) {
            Cart newCart = Cart.builder()
                    .id(UUID.randomUUID())
                    .userId(userId)
                    .createdAt(now)
                    .build();
            repository.save(newCart);
            return newCart;
        }
        return cart;
    }

    @Override
    public List<CartItemResponse> getAllCartItems() {
        List<CartItems> all = repository.getAll();
        return null;
    }

    @Override
    public CartItemResponse addToCart(AddToCartRequest request) {
        CartItems cartItem = new CartItems();
        repository.addToCart(cartItem);
        return null;
    }

    @Override
    public CartItemResponse updateCartItem(UUID itemId, UpdateCartItemRequest request) {
        CartItems cartItem = new CartItems();
        repository.updateCart(itemId, cartItem);
        return null;
    }

    @Override
    public void removeCartItem(UUID itemId) {
        UUID cartId = null;
        repository.deleteByProductId(cartId, itemId);
    }

    @Override
    public void clearCart() {
        UUID cartId = null;
        repository.clearCart(cartId);
    }

    @Override
    public CartItemCountResponse getCartItemCount() {

        return repository.getItemCount();
    }
}
