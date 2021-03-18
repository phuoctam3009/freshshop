package com.example.SpringBootProject.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String img;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;
    public Category(){

    }

    @Transient
    public String getImagePath(){
        if (img == null) return null;

        return "/images/category/" + id + "/" + img;
    }
}
