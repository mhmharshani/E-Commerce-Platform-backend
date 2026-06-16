package edu.icet.ecom.repository;

import edu.icet.ecom.model.Order;
import edu.icet.ecom.model.OrderItems;

public interface OrderRepository {

    int save(Order order);

    int saveOrderItem(OrderItems orderItem);
}
