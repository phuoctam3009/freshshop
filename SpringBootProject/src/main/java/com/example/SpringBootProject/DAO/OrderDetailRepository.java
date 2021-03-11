package com.example.SpringBootProject.DAO;

import com.example.SpringBootProject.Entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
