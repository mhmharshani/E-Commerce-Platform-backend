package edu.icet.ecom.model.dto.request;

import edu.icet.ecom.enums.InventoryAction;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateInventoryRequest {
    private UUID productId;
    private Integer quantityChange;
    private InventoryAction action;
}
