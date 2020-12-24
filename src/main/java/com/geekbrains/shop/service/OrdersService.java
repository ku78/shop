package com.geekbrains.shop.service;

import com.geekbrains.shop.dto.OrderDto;
import com.geekbrains.shop.entities.Orders;

public interface OrdersService {
    OrderDto getOrderById(Long id);
    Orders getOrder(Long id);
}


