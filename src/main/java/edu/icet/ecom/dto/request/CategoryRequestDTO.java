package edu.icet.ecom.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDTO {
    private String id;
    private String name;
    private String description;
}
