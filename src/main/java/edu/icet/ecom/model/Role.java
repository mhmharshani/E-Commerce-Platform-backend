package edu.icet.ecom.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    private UUID id;
    private String name;

}
