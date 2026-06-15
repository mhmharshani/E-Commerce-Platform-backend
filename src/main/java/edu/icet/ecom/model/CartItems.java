package edu.icet.ecom.model;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItems {
    private UUID id;
    private UUID cartId;
    private UUID productId;
    private Integer quantity;
}
