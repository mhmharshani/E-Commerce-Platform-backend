package edu.icet.ecom.repository.impl;

import edu.icet.ecom.enums.OrderStatus;
import edu.icet.ecom.mapper.CartItemsRowMapper;
import edu.icet.ecom.mapper.OrderItemsRowMapper;
import edu.icet.ecom.mapper.OrderRowMapper;
import edu.icet.ecom.mapper.PaymentRowMapper;
import edu.icet.ecom.model.CartItems;
import edu.icet.ecom.model.Order;
import edu.icet.ecom.model.OrderItems;
import edu.icet.ecom.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

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
                (id, quantity, unit_price, sub_total, order_id, product_id)
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

    @Override
    public List<Order> getAll() {
        String sql = """
            SELECT *
            FROM orders
            """;
        return template.query(
                sql,
                new OrderRowMapper()
        );
    }

    @Override
    public List<OrderItems> getAllByOrderId(UUID orderId) {
        String sql = """
            SELECT *
            FROM order_items
            WHERE order_id = ?
            """;
        return template.query(
                sql,
                new OrderItemsRowMapper(),
                orderId.toString()
        );
    }

    @Override
    public List<Order> findOrdersByUserId(UUID userId) {
        String sql = """
            SELECT *
            FROM orders
            WHERE user_id = ?
            """;
        return template.query(
                sql,
                new OrderRowMapper(),
                userId.toString()
        );
    }

    @Override
    public Order findOrderByOrderId(UUID orderId) {
        String sql = """
            SELECT *
            FROM orders
            WHERE id = ?
            """;
        try {
            return template.queryForObject(
                    sql,
                    new OrderRowMapper(),
                    orderId.toString()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Order findOrderByIdAndUserId(UUID orderId, UUID userId) {
        String sql = """
            SELECT *
            FROM orders
            WHERE user_id = ? AND id = ?
            """;
        try {

            return template.queryForObject(
                    sql,
                    new OrderRowMapper(),
                    userId.toString(),
                    orderId.toString()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int updateOrderStatus(UUID orderId, OrderStatus status) {
        String sql = """
                UPDATE orders
                SET status = ?
                WHERE id = ?
                """;
        return template.update(
                sql,
                status.name(),
                orderId.toString()
        );
    }
}
