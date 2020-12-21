package com.geekbrains.shop.mapper;

import com.geekbrains.shop.entities.Orders;
import com.geekbrains.shop.dto.OrderDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {
    OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);

    Orders toOrder(OrderDto orderDto);

    @InheritInverseConfiguration
    OrderDto fromOrder(Orders order);

    List<Orders> toOrder(List<OrderDto> orderDtos);

    List<OrderDto> fromOrder(List<Orders> order);
}
