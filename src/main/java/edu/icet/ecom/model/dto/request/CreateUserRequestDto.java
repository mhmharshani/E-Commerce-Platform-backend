package edu.icet.ecom.model.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequestDto {
    private String name;
    private String email;
    private String password;
    private String role;
}
