package edu.icet.ecom.model.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private  String usernameOrEmail;
    private String password;
}
