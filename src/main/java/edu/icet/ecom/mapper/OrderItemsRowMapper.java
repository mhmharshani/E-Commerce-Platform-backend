package edu.icet.ecom.mapper;

import edu.icet.ecom.model.CartItems;
import edu.icet.ecom.model.OrderItems;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

//Used for Select Queries
public class OrderItemsRowMapper implements RowMapper<OrderItems> {

    @Override
    public OrderItems mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderItems orderItems = new OrderItems();
        orderItems.setId(UUID.fromString(rs.getString("id")));
        orderItems.setQuantity(rs.getInt("quantity"));
        orderItems.setUnitPrice(rs.getDouble("unit_price"));
        orderItems.setSubTotal(rs.getDouble("sub_total"));
        orderItems.setOrderId(UUID.fromString(rs.getString("order_id")));
        orderItems.setProductId(UUID.fromString(rs.getString("product_id")));

        return orderItems;
    }
}
