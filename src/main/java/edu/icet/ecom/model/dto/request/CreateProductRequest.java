package edu.icet.ecom.model.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String categoryId;
}
