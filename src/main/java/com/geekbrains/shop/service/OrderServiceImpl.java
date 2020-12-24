package com.geekbrains.shop.service;

import  com.geekbrains.shop.dao.OrdersRepository;
import  com.geekbrains.shop.entities.*;
import  com.geekbrains.shop.dto.OrderDto;
import  com.geekbrains.shop.mapper.OrderDetailsMapper;
import com.geekbrains.shop.mapper.OrderMapper;
import org.springframework.stereotype.Service;
import com.geekbrains.shop.dto.OrderIntegrationDto;
import com.geekbrains.shop.config.OrderIntegrationConfig;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrdersService {
    private final OrderDetailsMapper orderDetailsMapper = OrderDetailsMapper.MAPPER;
    private final OrderMapper orderMapper = OrderMapper.MAPPER;
    private final OrdersRepository orderRepository;
    private final OrderIntegrationConfig integrationConfig;



    public OrderServiceImpl(OrdersRepository orderRepository, OrderIntegrationConfig integrationConfig) {
        this.orderRepository = orderRepository;
        this.integrationConfig = integrationConfig;
    }
    @Override
    @Transactional
    public void saveOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        sendIntegrationNotify(savedOrder);
    }

    private void sendIntegrationNotify(Order order){
        OrderIntegrationDto dto = new OrderIntegrationDto();
        dto.setUsername(order.getUser().getName());
        dto.setAddress(order.getAddress());
        dto.setOrderId(order.getId());
        List<OrderIntegrationDto.OrderDetailsDto> details = order.getDetails().stream()
                .map(OrderIntegrationDto.OrderDetailsDto::new).collect(Collectors.toList());
        dto.setDetails(details);

        Message<OrderIntegrationDto> message = MessageBuilder.withPayload(dto)
                .setHeader("Content-type", "application/json")
                .build();

        integrationConfig.getOrdersChannel().send(message);
    }
    @Override
    public Orders getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
    @Override
    public OrderDto getOrderDtoById(Long id) {
        Orders order = orderRepository.findOneById(id);
        OrderDto orderDto = orderMapper.fromOrder(order);
        orderDto.setUsername(order.getUser().getName());
        orderDto.aggregate();
        return orderDto;
    }
}