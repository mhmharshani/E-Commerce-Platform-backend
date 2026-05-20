package edu.icet.ecom.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String roleId;
}
