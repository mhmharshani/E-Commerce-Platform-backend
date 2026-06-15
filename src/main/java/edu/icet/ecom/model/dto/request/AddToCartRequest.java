package edu.icet.ecom.model.dto.request;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartRequest {
    private UUID productId;
    private Integer quantity;
}
