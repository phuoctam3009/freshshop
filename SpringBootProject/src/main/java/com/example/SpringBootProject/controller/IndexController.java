package com.example.SpringBootProject.controller;

import com.example.SpringBootProject.DAO.BalanceRepository;
import com.example.SpringBootProject.DAO.CategoryRepository;
import com.example.SpringBootProject.DAO.ProductRepository;
import com.example.SpringBootProject.DAO.UserRepository;
import com.example.SpringBootProject.Entity.Category;
import com.example.SpringBootProject.Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BalanceRepository balanceRepository;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request){
        List<Product> listProduct = productRepository.findAll();
        List<Category> listCategory = categoryRepository.findAll();
        System.out.println("Số lượng sản phẩm: " + listProduct.size());
        System.out.println("Số lượng category: " + listCategory.size());
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
