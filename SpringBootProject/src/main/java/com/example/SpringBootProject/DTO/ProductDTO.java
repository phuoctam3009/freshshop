package com.example.SpringBootProject.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String img;
    private String name;
    private long priceNew;
    private long priceOld;
    private long quantity;
}
