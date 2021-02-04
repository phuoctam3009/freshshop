package com.example.SpringBootProject.DTO;

import com.example.SpringBootProject.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO implements Serializable {
    private Product product;
    private int quantity;
    private long totalPriceItem;

    public void setTotalPriceItem() {
        long total = 0;
        total = this.product.getPriceNew() * this.getQuantity();
        this.totalPriceItem = total;
    }
}
