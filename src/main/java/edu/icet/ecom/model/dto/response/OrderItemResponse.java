package edu.icet.ecom.model.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemResponse {
    private UUID productId;
    private String productName;
    private Integer quantity;
    private Double unitPrice;
    private Double subTotal;
}
