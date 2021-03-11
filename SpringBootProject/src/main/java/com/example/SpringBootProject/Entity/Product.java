package com.example.SpringBootProject.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems;
    @Column(name = "extra_image1")
    private String extraImage1;
    @Column(name = "extra_image2")
    private String extraImage2;
    @Column(name = "extra_image3")
    private String extraImage3;

    @Transient
    public String getImagePath(){
        if (img == null) return null;

        return "/images/product/" + id + "/" + img;
    }

}
