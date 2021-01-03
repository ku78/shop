package com.geekbrains.shop.demo.integration;

import com.geekbrains.shop.entities.Orders;
import com.geekbrains.shop.service.OrderService;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderActivator {

    private final OrderService orderService;

    public OrderActivator(OrderService orderService) {
        this.orderService = orderService;
    }

    @ServiceActivator(inputChannel = "ordersChannel")
    public void listenOrderChannel(@Payload Order payload, @Headers Map<String, Object> headers){
        orderService.save(payload);
    }
}
