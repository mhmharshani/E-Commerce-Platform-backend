package edu.icet.ecom.controller;

import edu.icet.ecom.model.dto.request.CheckoutRequest;
import edu.icet.ecom.model.dto.response.CheckoutResponse;
import edu.icet.ecom.model.dto.response.OrderResponse;
import edu.icet.ecom.service.OrderService;
import edu.icet.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/order")
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;

    @PostMapping
    public CheckoutResponse checkoutOrder(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CheckoutRequest request){
        UUID userId = userService.getCurrentUserId(userDetails.getUsername());
        return orderService.checkout(userId,request);
    }

    @GetMapping
    public List<OrderResponse> getMyOrders(@AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = userService.getCurrentUserId(userDetails.getUsername());
        return orderService.getOrders(userId);
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrderById(@PathVariable UUID orderId, @AuthenticationPrincipal UserDetails userDetails ) {
        UUID userId = userService.getCurrentUserId(userDetails.getUsername());
        return orderService.getOrderByIdAndUserId(orderId,userId);
    }

    @PostMapping("/{orderId}/cancel")
    public OrderResponse cancelOrder(@PathVariable UUID orderId, @AuthenticationPrincipal UserDetails userDetails) {
        return null;
    }
}
