package edu.icet.ecom.mapper;

import edu.icet.ecom.enums.OrderStatus;
import edu.icet.ecom.model.Order;
import edu.icet.ecom.model.OrderItems;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

//Used for Select Queries
public class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(UUID.fromString(rs.getString("id")));
        order.setTotalAmount(rs.getDouble("total_amount"));
        order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
        order.setStatus(OrderStatus.valueOf(rs.getString("status")));
        order.setUserId(UUID.fromString(rs.getString("user_id")));
        order.setDeliveryPersonId(rs.getString("delivery_person_id") != null ? UUID.fromString(rs.getString("delivery_person_id")) : null);
        order.setShippingAddressId(UUID.fromString(rs.getString("shipping_address_id")));

        return order;
    }
}
