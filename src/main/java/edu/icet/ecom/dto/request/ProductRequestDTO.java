package edu.icet.ecom.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {
    private String id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String categoryId;
}
