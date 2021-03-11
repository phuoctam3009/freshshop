package com.example.SpringBootProject.Service;

import com.example.SpringBootProject.DAO.*;
import com.example.SpringBootProject.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public boolean checkoutOrder(Order order, User user){
        Order newOrder = orderRepository.save(order);
        newOrder.setUser(user);
        List<CartItem> cartItems = listCartItems(user);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(newOrder);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetailRepository.save(orderDetail);
            //Xóa DB trong cartitem
            cartItemRepository.deleteByUserAndProduct(user.getId(), cartItem.getProduct().getId());
            //Update quantity trong product
            Product product = productRepository.findById(cartItem.getProduct().getId()).get();
            int quantity = (int) product.getQuantity();
            productRepository.updateQuantityProduct((long) (quantity - cartItem.getQuantity()), product.getId());
            orderDetails.add(orderDetail);
        }
        newOrder.setOrderDetails(orderDetails);
        long total = 0;
        for (OrderDetail orderDetail : orderDetails) {
            total += orderDetail.getQuantity() * orderDetail.getProduct().getPriceNew();
        }
        newOrder.setTotal(total);
        newOrder.setStatus(true);
        orderRepository.save(newOrder);
        System.out.println("Xử lý xong checkout");
        return true;
    }

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

    @Transactional
    public boolean updateCart(User user, Long productId, Integer quantity){
        Product product = productRepository.findById(productId).get();
        //Check số lượng giỏ hàng và số lượng sẵn có
        if(product.getQuantity() < quantity){
            System.out.println("Vượt quá số lượng");
            return false;
        }
        cartItemRepository.updateQuantityCart(quantity, productId, user.getId());
        return true;
    }
}
