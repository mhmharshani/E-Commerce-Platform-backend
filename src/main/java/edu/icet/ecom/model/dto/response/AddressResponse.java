package edu.icet.ecom.model.dto.response;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse {
    private UUID id;
    private String fullName;
    private String phoneNumber;
    private String addressLine1;
    private String city;
    private String postalCode;
}
