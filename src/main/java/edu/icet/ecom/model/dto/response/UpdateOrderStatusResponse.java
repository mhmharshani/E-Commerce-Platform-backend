package edu.icet.ecom.model.dto.response;

import edu.icet.ecom.enums.OrderStatus;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOrderStatusResponse {
    private UUID id;
    private UUID userId;
    private OrderStatus status;
}
