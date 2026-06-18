package edu.icet.ecom.service;

import edu.icet.ecom.model.dto.request.CheckoutRequest;
import edu.icet.ecom.model.dto.request.UpdateOrderStatusRequest;
import edu.icet.ecom.model.dto.response.CheckoutResponse;
import edu.icet.ecom.model.dto.response.OrderResponse;
import edu.icet.ecom.model.dto.response.UpdateOrderStatusResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    CheckoutResponse checkout(UUID userId, CheckoutRequest request);

    List<OrderResponse> getOrders(UUID userId);

    OrderResponse getOrderById(UUID orderId);

    List<OrderResponse> getAllOrders();

    UpdateOrderStatusResponse updateStatus(UUID orderId, UpdateOrderStatusRequest request);

    OrderResponse getOrderByIdAndUserId(UUID orderId, UUID userId);
}
