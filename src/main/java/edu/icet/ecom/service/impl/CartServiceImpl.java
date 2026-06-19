package edu.icet.ecom.service.impl;

import edu.icet.ecom.model.Cart;
import edu.icet.ecom.model.CartItems;
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
import java.util.ArrayList;
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
    public List<CartItemResponse> getAllCartItems(UUID userId) {
        List<CartItems> all = repository.getAllByUserId(userId);

        List<CartItemResponse> cartItemResponseList = new ArrayList<>();

        all.forEach(cartItem -> {
            cartItemResponseList.add(
                    CartItemResponse.builder()
                            .id(cartItem.getId())
                            .productId(cartItem.getProductId())
                            .quantity(cartItem.getQuantity())
                            .build()
            );
        });

        return cartItemResponseList;
    }

    @Override
    public CartItemResponse addToCart(AddToCartRequest request, UUID userId) {
        Cart currentUserCart = getOrCreateCart(userId);
        CartItems cartItem = CartItems.builder()
                .id(UUID.randomUUID())
                .cartId(currentUserCart.getId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .build();

        repository.addToCart(cartItem);
        return CartItemResponse.builder()
                .id(cartItem.getId())
                .productId(cartItem.getProductId())
                .quantity(cartItem.getQuantity())
                .build();
    }

    @Override
    public CartItemResponse updateCartItem(UUID itemId,UUID userId,UpdateCartItemRequest request) {
        Cart currentUserCart = getOrCreateCart(userId);
        repository.updateCart(currentUserCart.getId(),itemId, request.getQuantity());

        CartItems cartItem = repository.findByCartIdAndProductId(currentUserCart.getId(), itemId);
        return CartItemResponse.builder()
                .id(cartItem.getId())
                .productId(itemId)
                .quantity(request.getQuantity())
                .build();
    }

    @Override
    public void removeCartItem(UUID userId, UUID itemId) {
        Cart currentUserCart = getOrCreateCart(userId);
        repository.deleteByProductId(currentUserCart.getId(), itemId);
    }

    @Override
    public void clearCart(UUID userId) {
        Cart currentUserCart = getOrCreateCart(userId);
        repository.clearCart(currentUserCart.getId());
    }

    @Override
    public CartItemCountResponse getCartItemCount(UUID userId) {
        List<CartItems> allByUserId = repository.getAllByUserId(userId);
        int totalQty = 0;
        for (CartItems cartItem : allByUserId) {
            totalQty += cartItem.getQuantity();
        }

        return CartItemCountResponse.builder()
                .itemCount(totalQty)
                .build();
    }
}
