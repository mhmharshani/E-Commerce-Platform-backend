package edu.icet.ecom.model.dto.response;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponse {
    private UUID id;
    private UUID productId;
    private Integer quantity;
}
