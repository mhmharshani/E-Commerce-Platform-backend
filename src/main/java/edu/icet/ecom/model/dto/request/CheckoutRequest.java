package edu.icet.ecom.model.dto.request;

import edu.icet.ecom.enums.PaymentMethod;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequest {
    private UUID shippingAddressId;
    private PaymentMethod paymentMethod;
}
