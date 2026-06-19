package edu.icet.ecom.model.dto.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private Instant expiresAt;
    private String role;
}
