package edu.icet.ecom.model.dto.request;

import edu.icet.ecom.enums.OrderStatus;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOrderStatusRequest {
    private OrderStatus orderStatus;
}
