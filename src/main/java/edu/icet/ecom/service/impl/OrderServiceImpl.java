package edu.icet.ecom.service.impl;

import edu.icet.ecom.enums.OrderStatus;
import edu.icet.ecom.enums.PaymentStatus;
import edu.icet.ecom.model.*;
import edu.icet.ecom.model.dto.request.CheckoutRequest;
import edu.icet.ecom.model.dto.response.CheckoutResponse;
import edu.icet.ecom.model.dto.response.OrderItemResponse;
import edu.icet.ecom.repository.CartRepository;
import edu.icet.ecom.repository.OrderRepository;
import edu.icet.ecom.repository.PaymentRepository;
import edu.icet.ecom.repository.ProductRepository;
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

    @Override
    @Transactional
    public CheckoutResponse checkout(UUID userId, CheckoutRequest request) {
        System.out.println("Request : "+request);
        // 1. Get cart items
        List<CartItems> cartItemsList = cartRepository.getAllByUserId(userId);

        // 2. Calculate total
        double total = 0;
        for(int i=0; i<cartItemsList.size(); i++){
            CartItems item = cartItemsList.get(i);
            Product product = productRepository.findProductById(item.getProductId());
            if(product == null){
                throw new RuntimeException("Product not found: " + item.getProductId());
            }
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
}
