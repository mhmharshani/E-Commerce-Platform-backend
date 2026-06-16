package edu.icet.ecom.model.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutResponse {
    private UUID orderId;
    private UUID userId;
    private String orderStatus;
    private String paymentStatus;
    private String paymentMethod;
    private Double totalAmount;
    private LocalDateTime orderDate;
    private List<OrderItemResponse> items;
}
