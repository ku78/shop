package com.geekbrains.shop.service;

import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import com.example.demo.dto.OrderDto;

import java.util.List;

public interface OrdersService {
    Orders createOrders(User user, List<Long> productIds);
    OrdersDto getOrderById(Long id);
}


