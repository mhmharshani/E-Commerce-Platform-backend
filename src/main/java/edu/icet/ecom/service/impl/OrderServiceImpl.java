package edu.icet.ecom.service.impl;

import edu.icet.ecom.enums.OrderStatus;
import edu.icet.ecom.enums.PaymentStatus;
import edu.icet.ecom.model.*;
import edu.icet.ecom.model.dto.request.CheckoutRequest;
import edu.icet.ecom.model.dto.request.UpdateOrderStatusRequest;
import edu.icet.ecom.model.dto.response.*;
import edu.icet.ecom.repository.*;
import edu.icet.ecom.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final PaymentRepository paymentRepository;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public CheckoutResponse checkout(UUID userId, CheckoutRequest request) {

        // 1. Get cart items
        List<CartItems> cartItemsList = cartRepository.getAllByUserId(userId);
        // Check if cart is not empty
        if(cartItemsList.isEmpty()){
            throw new RuntimeException("Cart is empty");
        }
        // Address validation
        ShippingAddress address = addressRepository.findById(request.getShippingAddressId());
        if (address == null) {
            throw new RuntimeException("Address not found");
        }

        // Check Product existence, stock availability and Calculate total
        double total = 0;
        for(int i=0; i<cartItemsList.size(); i++){
            CartItems item = cartItemsList.get(i);
            Product product = productRepository.findProductById(item.getProductId());
            if(product == null){
                throw new RuntimeException("Product not found: " + item.getProductId());
            }
            if(product.getStock() < item.getQuantity()){
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }
            // Get price from product table
            total += product.getPrice() * item.getQuantity();
        }

        // 3. Create order
         Order order = Order.builder()
                .id(UUID.randomUUID())
                .totalAmount(total)
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .userId(userId)
                .deliveryPersonId(null) // Assign delivery person later
                .shippingAddressId(request.getShippingAddressId())
                .build();
         orderRepository.save(order);

        // 4. Create order items
        List<OrderItemResponse> orderItemResponseList = new ArrayList<>();

        for(int i=0; i<cartItemsList.size(); i++){
            CartItems item = cartItemsList.get(i);
            Product product = productRepository.findProductById(item.getProductId());

            if(product == null){
                throw new RuntimeException("Product not found: " + item.getProductId());
            }
            orderRepository.saveOrderItem(
                    OrderItems.builder()
                    .id(UUID.randomUUID())
                    .quantity(item.getQuantity())
                    .unitPrice(product.getPrice())
                    .subTotal(product.getPrice() * item.getQuantity())
                    .orderId(order.getId())
                    .productId(product.getId())
                    .build()
            );

            orderItemResponseList.add(
                    OrderItemResponse.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .quantity(item.getQuantity())
                    .unitPrice(product.getPrice())
                    .subTotal(product.getPrice() * item.getQuantity())
                    .build()
            );
        }

        // Update - Here, decrease product stock
        for(int i=0; i<cartItemsList.size(); i++){
            CartItems item = cartItemsList.get(i);
            Product product = productRepository.findProductById(item.getProductId());
            int updated = productRepository.updateStock(product.getId(), item.getQuantity());
            if(updated <= 0){
                throw new RuntimeException("Failed to update stock for product: " + product.getName());
            }
        }

        // 5. Create payment record
        PaymentStatus paymentStatus = switch (request.getPaymentMethod()) {
            case CREDIT_CARD, DEBIT_CARD -> PaymentStatus.PAID;
            case CASH_ON_DELIVERY -> PaymentStatus.PENDING;
            default -> throw new RuntimeException("Unsupported payment method: " + request.getPaymentMethod());
        };

        Payment payment = Payment.builder()
                .id(UUID.randomUUID())
                .amount(total)
                .paidAt(LocalDateTime.now())
                .transactionRef(UUID.randomUUID())
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(paymentStatus)
                .orderId(order.getId())
                .build();// Assume payment is successful for this example
        paymentRepository.save(payment);

        // 6. Clear cart
        Cart currentCart = cartRepository.findCartByUserId(userId);
        cartRepository.clearCart(currentCart.getId());

        return CheckoutResponse.builder()
                .orderId(order.getId())
                .userId(userId)
                .orderStatus(order.getStatus().name())
                .paymentStatus(payment.getPaymentStatus().name())
                .paymentMethod(request.getPaymentMethod().name())
                .totalAmount(order.getTotalAmount())
                .orderDate(order.getOrderDate())
                .items(orderItemResponseList)
                .build();
    }

    @Override
    public List<OrderResponse> getOrders(UUID userId) {
        List<Order> ordersList = orderRepository.findOrdersByUserId(userId);
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for(Order order : ordersList){
            Payment payment = paymentRepository.findPaymentByOrderId(order.getId());
            List<OrderItems> orderItemsList = orderRepository.getAllByOrderId(order.getId());
            List<OrderItemResponse> orderItemResponseList = new ArrayList<>();
            for(OrderItems item : orderItemsList){
                Product product = productRepository.findProductById(item.getProductId());
                orderItemResponseList.add(
                    OrderItemResponse.builder()
                            .productId(item.getProductId())
                            .productName(product.getName())
                            .quantity(item.getQuantity())
                            .unitPrice(item.getUnitPrice())
                            .subTotal(item.getSubTotal())
                            .build()
                );
            }
            ShippingAddress address = addressRepository.findById(order.getShippingAddressId());

            orderResponseList.add(
                    OrderResponse.builder()
                        .id(order.getId())
                        .status(order.getStatus())
                        .paymentStatus(payment.getPaymentStatus())
                        .totalAmount(order.getTotalAmount())
                        .orderDate(order.getOrderDate())
                        .shippingAddress(
                                AddressResponse.builder()
                                    .id(address.getId())
                                    .fullName(address.getFullName())
                                    .phoneNumber(address.getPhoneNumber())
                                    .addressLine1(address.getAddressLine1())
                                    .city(address.getCity())
                                    .postalCode(address.getPostalCode())
                                    .build()
                        )
                        .orderItemResponseList(orderItemResponseList)
                        .build()
            );
        }
        return orderResponseList;
    }

    @Override
    public OrderResponse getOrderById(UUID orderId) {
        Order order = orderRepository.findOrderByOrderId(orderId);
        if(order != null){
            Payment payment = paymentRepository.findPaymentByOrderId(order.getId());
            List<OrderItems> orderItemsList = orderRepository.getAllByOrderId(order.getId());

            List<OrderItemResponse> orderItemResponseList = new ArrayList<>();

            for(OrderItems item : orderItemsList){
                Product product = productRepository.findProductById(item.getProductId());
                orderItemResponseList.add(
                        OrderItemResponse.builder()
                                .productId(item.getProductId())
                                .productName(product.getName())
                                .quantity(item.getQuantity())
                                .unitPrice(item.getUnitPrice())
                                .subTotal(item.getSubTotal())
                                .build()
                );
            }
            ShippingAddress address = addressRepository.findById(order.getShippingAddressId());

            return OrderResponse.builder()
                    .id(order.getId())
                    .status(order.getStatus())
                    .paymentStatus(payment.getPaymentStatus())
                    .totalAmount(order.getTotalAmount())
                    .orderDate(order.getOrderDate())
                    .shippingAddress(
                            AddressResponse.builder()
                                    .id(address.getId())
                                    .fullName(address.getFullName())
                                    .phoneNumber(address.getPhoneNumber())
                                    .addressLine1(address.getAddressLine1())
                                    .city(address.getCity())
                                    .postalCode(address.getPostalCode())
                                    .build()
                    )
                    .orderItemResponseList(orderItemResponseList)
                    .build();
        }

        return null;
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> ordersList = orderRepository.getAll();
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for(Order order : ordersList){
            Payment payment = paymentRepository.findPaymentByOrderId(order.getId());
            List<OrderItems> orderItemsList = orderRepository.getAllByOrderId(order.getId());
            List<OrderItemResponse> orderItemResponseList = new ArrayList<>();
            for(OrderItems item : orderItemsList){
                Product product = productRepository.findProductById(item.getProductId());
                orderItemResponseList.add(
                        OrderItemResponse.builder()
                                .productId(item.getProductId())
                                .productName(product.getName())
                                .quantity(item.getQuantity())
                                .unitPrice(item.getUnitPrice())
                                .subTotal(item.getSubTotal())
                                .build()
                );
            }
            ShippingAddress address = addressRepository.findById(order.getShippingAddressId());

            orderResponseList.add(
                    OrderResponse.builder()
                            .id(order.getId())
                            .status(order.getStatus())
                            .paymentStatus(payment.getPaymentStatus())
                            .totalAmount(order.getTotalAmount())
                            .orderDate(order.getOrderDate())
                            .shippingAddress(
                                    AddressResponse.builder()
                                            .id(address.getId())
                                            .fullName(address.getFullName())
                                            .phoneNumber(address.getPhoneNumber())
                                            .addressLine1(address.getAddressLine1())
                                            .city(address.getCity())
                                            .postalCode(address.getPostalCode())
                                            .build()
                            )
                            .orderItemResponseList(orderItemResponseList)
                            .build()
            );
        }
        return orderResponseList;
    }

    @Override
    public UpdateOrderStatusResponse updateStatus(UUID orderId, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findOrderByOrderId(orderId);
        if(order != null){
            int updated = orderRepository.updateOrderStatus(orderId, request.getOrderStatus());
            if(updated<=0){
                throw new RuntimeException("Failed to update order status for order: " + orderId);
            }
            return UpdateOrderStatusResponse.builder()
                    .id(orderId)
                    .userId(order.getUserId())
                    .status(request.getOrderStatus())
                    .build();
        }
        return null;
    }

    @Override
    public OrderResponse getOrderByIdAndUserId(UUID orderId, UUID userId) {
        Order order = orderRepository.findOrderByIdAndUserId(orderId,userId);
        if(order != null){
            Payment payment = paymentRepository.findPaymentByOrderId(order.getId());
            List<OrderItems> orderItemsList = orderRepository.getAllByOrderId(order.getId());

            List<OrderItemResponse> orderItemResponseList = new ArrayList<>();

            for(OrderItems item : orderItemsList){
                Product product = productRepository.findProductById(item.getProductId());
                orderItemResponseList.add(
                        OrderItemResponse.builder()
                                .productId(item.getProductId())
                                .productName(product.getName())
                                .quantity(item.getQuantity())
                                .unitPrice(item.getUnitPrice())
                                .subTotal(item.getSubTotal())
                                .build()
                );
            }
            ShippingAddress address = addressRepository.findById(order.getShippingAddressId());

            return OrderResponse.builder()
                    .id(order.getId())
                    .status(order.getStatus())
                    .paymentStatus(payment.getPaymentStatus())
                    .totalAmount(order.getTotalAmount())
                    .orderDate(order.getOrderDate())
                    .shippingAddress(
                            AddressResponse.builder()
                                    .id(address.getId())
                                    .fullName(address.getFullName())
                                    .phoneNumber(address.getPhoneNumber())
                                    .addressLine1(address.getAddressLine1())
                                    .city(address.getCity())
                                    .postalCode(address.getPostalCode())
                                    .build()
                    )
                    .orderItemResponseList(orderItemResponseList)
                    .build();
        }

        return null;
    }
}
