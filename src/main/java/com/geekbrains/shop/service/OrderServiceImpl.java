package com.geekbrains.shop.service;

import  com.geekbrains.shop.dao.OrderRepository;
import  com.geekbrains.shop.dao.ProductRepository;
import  com.geekbrains.shop.entities.*;
import  com.geekbrains.shop.dto.OrdersDetailsDto;
import  com.geekbrains.shop.OrdersDto;
import  com.geekbrains.shop.OrdersDetailsMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrdersService {
    private final OrdersDetailsMapper orderDetailsMapper = OrderDetailsMapper.MAPPER;
    private final OrdersRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, UserService userService) {
        this.ordersRepository = ordersRepository;
        this.productRepository = productsRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Orders createsOrder(User user, List<Long> productIds) {
        Orders order = new Orders();
        order.setUser(user);
        List<OrdersDetailsDto> detailsDtos = getOrderDetailsDtosByProductIds(orders, productIds);
        List<OrdersDetails> details = orderDetailsMapper.toOrdersDetails(detailsDtos);
        order.setDetails(details);
        return orderRepository.save(order);
    }

    private List<OrdersDetailsDto> getOrderDetailsDtosByProductIds(Orders order, List<Long> productIds) {
        List<OrdersDetailsDto> detailsDtos = new ArrayList<>();
        for (Long productId : productIds) {
            Product product = productRepository.getOne(productId);
            detailsDtos.add(new OrdersDetailsDto(product));
        }
        return detailsDtos;
    }

    @Override
    public OrdersDto getOrderById(Long id) {
        OrdersDto ordersDto = new OrdersDto();
        Order orders = orderRepository.getOne(id);
        orderDto.setUsername(order.getUser().getName());
        List<OrdersDetails> orderDetails = order.getDetails();
        List<OrdersDetailsDto> ordersDetailsDtos = orderDetailsMapper.fromOrdersDetails(orderDetails);
        Map<Long, OrdersDetailsDto> mapByProductId = new HashMap<>();
        for (OrdersDetailsDto orderDetailsDto : ordersDetailsDtos) {
            OrdersDetailsDto detail = mapByProductId.get(orderDetailsDto.getProduct().getId());
            if (detail == null) {
                mapByProductId.put(ordersDetailsDto.getProduct().getId(), ordersDetailsDto);
            } else {
                detail.setAmount(detail.getAmount() + 1.0);
                detail.setSum(detail.getSum() + orderDetailsDto.getProduct().getPrice());
            }
        }
        orderDto.setDetails(new ArrayList<>(mapByProductId.values()));
        orderDto.aggregate();
        return ordersDto;
    }
}