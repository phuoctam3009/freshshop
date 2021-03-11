package com.example.SpringBootProject.controller;

import com.example.SpringBootProject.DAO.BalanceRepository;
import com.example.SpringBootProject.DAO.CategoryRepository;
import com.example.SpringBootProject.DAO.ProductRepository;
import com.example.SpringBootProject.DAO.UserRepository;
import com.example.SpringBootProject.DTO.CategoryDTO;
import com.example.SpringBootProject.DTO.ItemDTO;
import com.example.SpringBootProject.DTO.OrderDTO;
import com.example.SpringBootProject.Entity.CartItem;
import com.example.SpringBootProject.Entity.Category;
import com.example.SpringBootProject.Entity.Product;
import com.example.SpringBootProject.Entity.User;
import com.example.SpringBootProject.Service.ShoppingCartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
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
    @Autowired
    private ShoppingCartService cartService;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request, HttpServletResponse response){
        Principal user = request.getUserPrincipal();
        if(user != null){
            User byUsername = userRepository.findByUsername(user.getName());
            if(byUsername.getRole().getId() == 1){
                return "redirect:/admin/dashboard";
            }
        }
        List<Product> listProduct = productRepository.findAll();
        List<Category> listCategory = categoryRepository.findAll();
        model.addAttribute("categories", listCategory);
        model.addAttribute("products", listProduct);
        Principal userPrincipal = request.getUserPrincipal();
        if(userPrincipal != null){
            User byUsername = userRepository.findByUsername(userPrincipal.getName());
            List<CartItem> cartItems = cartService.listCartItems(byUsername);
            OrderDTO orderDTO = new OrderDTO();
            List<ItemDTO> itemDTOList = new ArrayList<>();
            for (CartItem cartItem: cartItems) {
                ItemDTO itemDTO = new ItemDTO();
                Product product = cartItem.getProduct();
                int quantity = cartItem.getQuantity();
                itemDTO.setProduct(product);
                itemDTO.setQuantity(quantity);
                itemDTO.setTotalPriceItem();
                itemDTOList.add(itemDTO);
            }
            orderDTO.setItems(itemDTOList);
            orderDTO.setUser(byUsername);
            orderDTO.setTotal_order();
            orderDTO.setTotal_quantity();
            model.addAttribute("order", orderDTO);
        }
        return "index";
    }


    // Login form with error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "error";
    }
}
