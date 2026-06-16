package edu.icet.ecom.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItems {
    private UUID id;
    private Integer quantity;
    private Double unitPrice;
    private Double subTotal;
    private UUID orderId;
    private UUID productId;
}

