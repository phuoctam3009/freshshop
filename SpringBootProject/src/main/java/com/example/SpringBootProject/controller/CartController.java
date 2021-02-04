package com.example.SpringBootProject.controller;

import com.example.SpringBootProject.DAO.BalanceRepository;
import com.example.SpringBootProject.DAO.CategoryRepository;
import com.example.SpringBootProject.DAO.ProductRepository;
import com.example.SpringBootProject.DAO.UserRepository;
import com.example.SpringBootProject.DTO.ItemDTO;
import com.example.SpringBootProject.DTO.OrderDTO;
import com.example.SpringBootProject.Entity.CartItem;
import com.example.SpringBootProject.Entity.Order;
import com.example.SpringBootProject.Entity.Product;
import com.example.SpringBootProject.Entity.User;
import com.example.SpringBootProject.Service.ShoppingCartService;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController{
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

    @GetMapping("/cart/view")
    public String showShoppingCart(Model model, HttpServletRequest request){
        Principal user = request.getUserPrincipal();
        //Check login
        if(user == null){
            return "redirect:/login";
        }else{
            String username = user.getName();
            User byUsername = userRepository.findByUsername(username);
            //Check role admin
            if(byUsername.getRole().getId() == 1){
                model.addAttribute("loginError", true);
                return "error";
            }else{
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
                return "cart";
            }
        }
    }

    @GetMapping("/cart/change")
    public String changeQuantity(@RequestParam(name = "productId") String id, @RequestParam(name = "quantity") String quantity, Model model, HttpServletRequest request){
        System.out.println("Data nhan duoc");
        System.out.println("ID: " + id);
        System.out.println("Quantity: " + quantity);
        HttpSession session = request.getSession();
        if(session.getAttribute("order") != null){
            OrderDTO orderDTO = (OrderDTO) session.getAttribute("order");
            for (ItemDTO itemDTO: orderDTO.getItems()) {
                if(itemDTO.getProduct().getId() == Long.parseLong(id)){
                    itemDTO.setQuantity(Integer.parseInt(quantity));
                    itemDTO.setTotalPriceItem();
                    orderDTO.setItems(orderDTO.getItems());
                    break;
                }
            }
            orderDTO.setTotal_quantity();
            orderDTO.setTotal_order();
            session.setAttribute("order", orderDTO);
        }
        return "aaaa";
    }

}
