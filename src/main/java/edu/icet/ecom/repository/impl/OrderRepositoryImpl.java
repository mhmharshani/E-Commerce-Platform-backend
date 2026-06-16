package edu.icet.ecom.repository.impl;

import edu.icet.ecom.model.Order;
import edu.icet.ecom.model.OrderItems;
import edu.icet.ecom.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final JdbcTemplate template;
    @Override
    public int save(Order order) {
        String sql = """
                INSERT INTO orders
                (id, total_amount, order_date, status, user_id, delivery_person_id, shipping_address_id)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        return template.update(
                sql,
                order.getId().toString(),
                order.getTotalAmount(),
                order.getOrderDate(),
                order.getStatus().name(),
                order.getUserId().toString(),
                order.getDeliveryPersonId() != null ? order.getDeliveryPersonId().toString() : null,
                order.getShippingAddressId().toString()
        );
    }

    @Override
    public int saveOrderItem(OrderItems orderItem) {
        String sql = """
                INSERT INTO order_items
                (id, quantity, unit_price, subtotal, order_id, product_id)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        return template.update(
                sql,
                orderItem.getId().toString(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice(),
                orderItem.getSubTotal(),
                orderItem.getOrderId().toString(),
                orderItem.getProductId().toString()
        );
    }
}
