package com.example.SpringBootProject.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String img;
    private String name;
    private long priceNew;
    private long priceOld;
    @Column(length = 3000)
    private String description;
    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;
    private long quantity;
    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private Category category;

    public Product(){

    }

}
