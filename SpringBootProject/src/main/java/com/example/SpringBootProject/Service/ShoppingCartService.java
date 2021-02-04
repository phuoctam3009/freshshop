package com.example.SpringBootProject.Service;

import com.example.SpringBootProject.DAO.CartItemRepository;
import com.example.SpringBootProject.DAO.ProductRepository;
import com.example.SpringBootProject.Entity.CartItem;
import com.example.SpringBootProject.Entity.Product;
import com.example.SpringBootProject.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    public List<CartItem> listCartItems(User user){
        return cartItemRepository.findByUser(user);
    }

    public Integer addProduct(Long productId, Integer quantity, User user){
        Integer addedQuantity = quantity;
        Product product = productRepository.findById(productId).get();

        CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product);

        if(cartItem != null){
            addedQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(addedQuantity);
        }else{
            cartItem = new CartItem();
            cartItem.setQuantity(quantity);
            cartItem.setUser(user);
            cartItem.setProduct(product);
        }
        cartItemRepository.save(cartItem);
        return addedQuantity;
    }

    public void removeProduct(User user, Long productId){
        cartItemRepository.deleteByUserAndProduct(user.getId(), productId);
    }

    public void updateCart(User user, Long productId, Integer quantity){
        cartItemRepository.updateQuantityCart(quantity, productId, user.getId());
    }
}
