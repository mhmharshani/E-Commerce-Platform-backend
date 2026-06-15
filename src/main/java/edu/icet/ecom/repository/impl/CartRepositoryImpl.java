package edu.icet.ecom.repository.impl;

import edu.icet.ecom.mapper.CartItemsRowMapper;
import edu.icet.ecom.model.Cart;
import edu.icet.ecom.model.CartItems;
import edu.icet.ecom.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {

    private final JdbcTemplate template;

    @Override
    public Cart findCartByUserId(UUID userId) {
        String sql = """
                SELECT *
                FROM cart
                WHERE user_id = ?
                """;
        try{
            return template.queryForObject(
                    sql,
                    (rs, rowNum) -> new Cart(
                            UUID.fromString(rs.getString("id")),
                            UUID.fromString(rs.getString("user_id")),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    ),
                    userId.toString()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int save(Cart cart) {
        String sql = """
                INSERT INTO cart
                (id, user_id, created_at)
                VALUES (?, ?, ?)
                """;
        return template.update(
                sql,
                cart.getId().toString(),
                cart.getUserId().toString(),
                cart.getCreatedAt()
        );
    }

    @Override
    public List<CartItems> getAllByUserId(UUID userId) {
        String sql = """
            SELECT ci.*
            FROM cart_items ci
            JOIN cart c ON ci.cart_id = c.id
            WHERE c.user_id = ?
            """;
        return template.query(
                sql,
                new CartItemsRowMapper(),
                userId.toString()
        );
    }

    @Override
    public int addToCart(CartItems cartItem) {
        String sql = """
                INSERT INTO cart_items
                (id, cart_id, product_id, quantity)
                VALUES (?, ?, ?, ?)
                """;
        return template.update(
                sql,
                cartItem.getId().toString(),
                cartItem.getCartId().toString(),
                cartItem.getProductId().toString(),
                cartItem.getQuantity()
        );
    }

    @Override
    public int updateCart(UUID cartId, UUID productId, Integer quantity) {
        String sql = """
                UPDATE cart_items
                SET quantity = ?
                WHERE cart_id = ? AND product_id = ?
                """;
        return template.update(
                sql,
                quantity,
                cartId.toString(),
                productId.toString()
        );
    }

    @Override
    public void deleteByProductId(UUID cartId, UUID productId) {
        String sql = """
                DELETE FROM cart_items
                WHERE cart_id = ? AND product_id = ?
                """;
        template.update(
                sql,
                cartId.toString(),
                productId.toString()
        );
    }

    @Override
    public void clearCart(UUID cartId) {
        String sql = """
                DELETE FROM cart_items
                WHERE cart_id = ?
                """;
        template.update(
                sql,
                cartId.toString()
        );
    }

    @Override
    public CartItems findByCartIdAndProductId(UUID cartId, UUID productId) {
        String sql = """
            SELECT *
            FROM cart_items
            WHERE cart_id = ? AND product_id = ?
            """;
        try{
            return template.queryForObject(
                    sql,
                    new CartItemsRowMapper(),
                    cartId.toString(),
                    productId.toString()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

}
