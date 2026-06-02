package edu.icet.ecom.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {
    private UUID id;
    private String url;
    private Boolean isPrimary;
    private Product product;
}
