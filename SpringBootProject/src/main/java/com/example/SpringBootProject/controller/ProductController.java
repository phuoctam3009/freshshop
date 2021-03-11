package com.example.SpringBootProject.controller;

import com.example.SpringBootProject.DAO.*;
import com.example.SpringBootProject.Entity.Category;
import com.example.SpringBootProject.Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Controller
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BalanceRepository balanceRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/shop")
    public String shop(Model model, @RequestParam(value = "id", required = false) Long categoryId) {
        System.out.println(categoryId);
        if (categoryId == null) {
            List<Product> listProduct = productRepository.findAll();
            model.addAttribute("listProduct", listProduct);
            for (Product product : listProduct
            ) {
                System.out.println(product.getId());
            }
        } else {
            Optional<Category> categoryFindById = categoryRepository.findById(categoryId);
            categoryFindById.ifPresent(category -> {
                model.addAttribute("listProduct", category.getProducts());
            });
        }
        List<Category> listCategory = categoryRepository.findAll();
        model.addAttribute("listCategory", listCategory);

        return "shop";
    }

    @GetMapping("/test")
    public String test(Model model){
        return "shop-detail";
    }
}
