package edu.icet.ecom.model.dto.response;

import edu.icet.ecom.enums.OrderStatus;
import edu.icet.ecom.enums.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticsResponse {
    private Integer totalOrders;
    private Integer pendingOrders;
    private Integer processingOrders;
    private Integer shippedOrders;
    private Integer deliveredOrders;
    private Integer cancelledOrders;
}
