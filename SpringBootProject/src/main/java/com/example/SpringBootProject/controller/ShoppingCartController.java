package com.example.SpringBootProject.controller;

import com.example.SpringBootProject.DAO.BalanceRepository;
import com.example.SpringBootProject.DAO.CategoryRepository;
import com.example.SpringBootProject.DAO.ProductRepository;
import com.example.SpringBootProject.DAO.UserRepository;
import com.example.SpringBootProject.DTO.InfoUpdate;
import com.example.SpringBootProject.DTO.ItemDTO;
import com.example.SpringBootProject.DTO.OrderDTO;
import com.example.SpringBootProject.Entity.CartItem;
import com.example.SpringBootProject.Entity.Product;
import com.example.SpringBootProject.Entity.User;
import com.example.SpringBootProject.Service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ShoppingCartController {
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

    @GetMapping("/cart/add")
    public String addToCart(Model model, HttpServletRequest request,
                            @RequestParam(name = "productId") String productId,
                            @RequestParam(name = "quantity", required = false, defaultValue = "1") String quantity) {
        System.out.println("addProductToCart: " + productId + " - " + quantity);
        Principal user = request.getUserPrincipal();
        //Check login
        if (user == null) {
            return "You must login to add this product to your shopping cart";
        } else {
            int qty = Integer.parseInt(quantity);
            String username = user.getName();
            User byUsername = userRepository.findByUsername(username);
            //Check role admin
            if (byUsername.getRole().getId() == 1) {
                return "You can't access this page";
            } else {
                Integer addedQuantity = cartService.addProduct(Long.parseLong(productId), qty, byUsername);
                List<CartItem> cartItems = cartService.listCartItems(byUsername);
                OrderDTO orderDTO = new OrderDTO();
                List<ItemDTO> itemDTOList = new ArrayList<>();
                for (CartItem cartItem : cartItems) {
                    ItemDTO itemDTO = new ItemDTO();
                    Product product = cartItem.getProduct();
                    int itemQuantity = cartItem.getQuantity();
                    itemDTO.setProduct(product);
                    itemDTO.setQuantity(itemQuantity);
                    itemDTO.setTotalPriceItem();
                    itemDTOList.add(itemDTO);
                }
                orderDTO.setItems(itemDTOList);
                orderDTO.setUser(byUsername);
                orderDTO.setTotal_order();
                orderDTO.setTotal_quantity();
                model.addAttribute("order", orderDTO);
                return addedQuantity + "item(s) of this product were added to your shopping cart";
            }
        }
    }

    @PostMapping("/cart/remove/{pid}")
    public String removeProductFromCart(@PathVariable("pid") Long productId, HttpServletRequest request) {
        System.out.println("ProductId: " + productId);
        Principal user = request.getUserPrincipal();
        //Check login
        if (user == null) {
            return "You must login to remove product";
        } else {
            String username = user.getName();
            User byUsername = userRepository.findByUsername(username);
            //Check role admin
            if (byUsername.getRole().getId() == 1) {
                return "You can't access this page";
            } else {
                cartService.removeProduct(byUsername, productId);
                return "The product has been removed from your shopping cart";
            }
        }
    }

    @PostMapping("/cart/update")
    public String updateCart(@RequestBody InfoUpdate infoUpdate, HttpServletRequest request){
        System.out.println("productid: " + infoUpdate.getProductid());
        System.out.println("quantity: " + infoUpdate.getQuantity());
        Long productid = Long.parseLong(infoUpdate.getProductid());
        Integer quantity = Integer.parseInt(infoUpdate.getQuantity());
        Principal user = request.getUserPrincipal();
        //Check login
        if (user == null) {
            return "You must login to remove product";
        } else {
            String username = user.getName();
            User byUsername = userRepository.findByUsername(username);
            //Check role admin
            if (byUsername.getRole().getId() == 1) {
                return "You can't access this page";
            } else {
                //Check quantity
                boolean update_status = cartService.updateCart(byUsername, productid, quantity);
                if (update_status){
                    return "Success";
                }else{
                    System.out.println("aaaaaa");
                    String message = "Sản phẩm " + productRepository.findById(productid).get().getName() +
                            " có số lượng tối đa được mua là " + productRepository.findById(productid).get().getQuantity();
                    return message;
                }
            }
        }
    }

}
