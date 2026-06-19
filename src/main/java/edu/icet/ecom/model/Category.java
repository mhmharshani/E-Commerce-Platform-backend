package edu.icet.ecom.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private UUID id;
    private String name;
    private String description;
}
