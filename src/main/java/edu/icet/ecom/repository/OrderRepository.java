package edu.icet.ecom.repository;

import edu.icet.ecom.enums.OrderStatus;
import edu.icet.ecom.model.Order;
import edu.icet.ecom.model.OrderItems;

import java.util.List;
import java.util.UUID;

public interface OrderRepository {

    int save(Order order);

    int saveOrderItem(OrderItems orderItem);

    List<OrderItems> getAllByOrderId(UUID orderId);

    List<Order> findOrdersByUserId(UUID userId);

    Order findOrderByOrderId(UUID orderId);

    List<Order> getAll();

    Order findOrderByIdAndUserId(UUID orderId, UUID userId);

    int updateOrderStatus(UUID orderId, OrderStatus status);
}
