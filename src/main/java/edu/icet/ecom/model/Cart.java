package edu.icet.ecom.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    private UUID id;
    private UUID userId;
    private LocalDateTime createdAt;
}
