package edu.icet.ecom.model.dto.response;

import lombok.*;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserResponse {
    private UUID id;
    private String name;
    private String email;
    private Timestamp createdAt;
}
