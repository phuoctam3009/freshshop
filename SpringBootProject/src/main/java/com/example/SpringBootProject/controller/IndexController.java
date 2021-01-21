package com.example.SpringBootProject.controller;

import com.example.SpringBootProject.DAO.BalanceRepository;
import com.example.SpringBootProject.DAO.CategoryRepository;
import com.example.SpringBootProject.DAO.ProductRepository;
import com.example.SpringBootProject.DAO.UserRepository;
import com.example.SpringBootProject.DTO.CategoryDTO;
import com.example.SpringBootProject.Entity.Category;
import com.example.SpringBootProject.Entity.Product;
import com.example.SpringBootProject.Entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
        model.addAttribute("categories", listCategory);
        model.addAttribute("products", listProduct);
        return "index";
    }


    // Login form with error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "error";
    }
}
