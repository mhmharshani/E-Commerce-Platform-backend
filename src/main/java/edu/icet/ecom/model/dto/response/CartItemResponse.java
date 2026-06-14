package edu.icet.ecom.model.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    private UUID id;
    private UUID cartId;
    private UUID productId;
    private Integer quantity;
    private LocalDateTime createdAt;
}
