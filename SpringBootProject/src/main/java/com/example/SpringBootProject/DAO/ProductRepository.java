package com.example.SpringBootProject.DAO;

import com.example.SpringBootProject.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Transactional
    @Query("UPDATE Product p SET p.quantity = ?1 WHERE p.id = ?2")
    @Modifying
    public void updateQuantityProduct(Long quantity, Long productid);
}
