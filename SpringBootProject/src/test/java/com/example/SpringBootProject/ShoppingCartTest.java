package com.example.SpringBootProject;

import com.example.SpringBootProject.DAO.CartItemRepository;
import com.example.SpringBootProject.Entity.CartItem;
import com.example.SpringBootProject.Entity.Product;
import com.example.SpringBootProject.Entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ShoppingCartTest {

    @Autowired
    private CartItemRepository cartRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void addOneCartItem(){
        Product product = entityManager.find(Product.class, 1L);
        User user = entityManager.find(User.class, 5L);

        CartItem newItem = new CartItem();
        newItem.setProduct(product);
        newItem.setUser(user);
        newItem.setQuantity(1);

        CartItem save = cartRepo.save(newItem);


    }
}
