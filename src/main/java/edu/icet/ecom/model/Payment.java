package edu.icet.ecom.model;

import edu.icet.ecom.enums.PaymentMethod;
import edu.icet.ecom.enums.PaymentStatus;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    private UUID id;
    private Double amount;
    private LocalDateTime paidAt;
    private UUID transactionRef;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private UUID orderId;
}
