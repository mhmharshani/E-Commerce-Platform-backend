package edu.icet.ecom.mapper;

import edu.icet.ecom.model.CartItems;
import edu.icet.ecom.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

//Used for Select Queries
public class CartItemsRowMapper implements RowMapper<CartItems> {

    @Override
    public CartItems mapRow(ResultSet rs, int rowNum) throws SQLException {
        CartItems cartItems = new CartItems();
        UUID cartItemsId = UUID.fromString(rs.getString("id"));
        cartItems.setId(cartItemsId);
        UUID cartId = UUID.fromString(rs.getString("cart_id"));
        cartItems.setCartId(cartId);
        UUID productId = UUID.fromString(rs.getString("product_id"));
        cartItems.setProductId(productId);
        cartItems.setQuantity(rs.getInt("quantity"));

        return cartItems;
    }
}
