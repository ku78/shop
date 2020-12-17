package com.geekbrains.shop.dao;

import com.geekbrains.shop.entities.Cart;
import  com.geekbrains.shop.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}