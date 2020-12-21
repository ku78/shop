package com.geekbrains.shop.service;

import com.geekbrains.shop.dto.OrderDto;

public interface OrdersService {
    OrderDto getOrderById(Long id);
}


