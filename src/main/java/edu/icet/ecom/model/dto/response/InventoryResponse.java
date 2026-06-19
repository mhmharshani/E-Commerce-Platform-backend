package edu.icet.ecom.model.dto.response;

import edu.icet.ecom.enums.InventoryAction;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {
    private UUID id;
    private UUID productId;
    private Integer newStock;
    private InventoryAction action;
    private LocalDateTime createdAt;
}
