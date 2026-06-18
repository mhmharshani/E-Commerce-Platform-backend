package edu.icet.ecom.controller;

import edu.icet.ecom.model.dto.request.UpdateOrderStatusRequest;
import edu.icet.ecom.model.dto.response.OrderResponse;
import edu.icet.ecom.model.dto.response.StatisticsResponse;
import edu.icet.ecom.model.dto.response.UpdateOrderStatusResponse;
import edu.icet.ecom.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/order")
@PreAuthorize("hasRole('ADMIN')")
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PatchMapping("/{id}/status")
    public UpdateOrderStatusResponse updateStatus(
            @PathVariable("id") UUID orderId,
            @RequestBody UpdateOrderStatusRequest request) {
        return orderService.updateStatus(orderId,request);
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrderById(@PathVariable UUID orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/stat")
    public StatisticsResponse getStatistics() {
        return null;
    }


}
