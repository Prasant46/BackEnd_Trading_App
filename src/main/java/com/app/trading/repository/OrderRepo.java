package com.app.trading.repository;

import com.app.trading.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Long> {

    List<Order> findByUserId(Long userId);
}
