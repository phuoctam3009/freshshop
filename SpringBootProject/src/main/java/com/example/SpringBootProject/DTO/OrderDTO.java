package com.example.SpringBootProject.DTO;

import com.example.SpringBootProject.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO implements Serializable {
    private User user;
    private List<ItemDTO> items;
    private int total_quantity;
    private long total_order;

    public void setTotal_quantity() {
        int total = 0;
        for (ItemDTO itemDTO: items) {
            total += itemDTO.getQuantity();
        }
        this.total_quantity = total;
    }

    public void setTotal_order() {
        long total_order = 0;
        for (ItemDTO itemDTO:items) {
            total_order += itemDTO.getTotalPriceItem();
        }
        this.total_order = total_order;
    }
}
