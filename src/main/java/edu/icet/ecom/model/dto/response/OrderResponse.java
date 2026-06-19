package edu.icet.ecom.model.dto.response;

import edu.icet.ecom.enums.OrderStatus;
import edu.icet.ecom.enums.PaymentStatus;
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
public class OrderResponse {
    private UUID id;
    private OrderStatus status;
    private PaymentStatus paymentStatus;
    private Double totalAmount;
    private LocalDateTime orderDate;
    private AddressResponse shippingAddress;
    private List<OrderItemResponse> orderItemResponseList;
}
