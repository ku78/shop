package com.geekbrains.shop.dao;

import  com.geekbrains.shop.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
