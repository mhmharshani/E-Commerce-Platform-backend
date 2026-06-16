package edu.icet.ecom.model;

import edu.icet.ecom.enums.OrderStatus;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private UUID id;
    private Double totalAmount;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private UUID userId;
    private UUID deliveryPersonId;
    private UUID shippingAddressId;
}
