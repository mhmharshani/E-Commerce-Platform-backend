package edu.icet.ecom.model;

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
public class InventoryLog {
    private UUID id;
    private UUID productId;
    private Integer quantityChange;
    private Integer stockBefore;
    private Integer stockAfter;
    private InventoryAction action;
    private UUID referenceId;
    private LocalDateTime createdAt;
}
