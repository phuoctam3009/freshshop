package com.example.SpringBootProject.DAO;

import com.example.SpringBootProject.Entity.CartItem;
import com.example.SpringBootProject.Entity.Product;
import com.example.SpringBootProject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    public List<CartItem> findByUser(User user);

    public CartItem findByUserAndProduct(User user, Product product);

    @Transactional
    @Query("DELETE FROM CartItem c WHERE c.user.id = ?1 AND c.product.id = ?2")
    @Modifying
    public void deleteByUserAndProduct(Long userid, Long productid);

    @Transactional
    @Query("UPDATE CartItem c SET c.quantity = ?1 WHERE c.product.id = ?2 AND c.user.id = ?3")
    @Modifying
    public void updateQuantityCart(Integer quantity, Long productid, Long userid);
}
