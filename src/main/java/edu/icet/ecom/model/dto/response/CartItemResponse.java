package edu.icet.ecom.model.dto.response;

import edu.icet.ecom.model.Product;
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
    private Integer quantity;
    private Product product;
}
