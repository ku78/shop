package com.geekbrains.shop.mapper;

import com.geekbrains.shop.entities.OrderDetails;
import com.geekbrains.shop.entities.User;
import com.geekbrains.shop.dto.OrderDetailsDto;
import com.geekbrains.shop.dto.UserDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderDetailsMapper {

    OrderDetailsMapper MAPPER = Mappers.getMapper(OrderDetailsMapper.class);

    OrdersDetails toOrderDetails(OrdersDetailsDto ordersDetailsDto);

    @InheritInverseConfiguration
    OrdersDetailsDto fromOrderDetails(OrdersDetails ordersDetails);

    List<OrdersDetails> toOrderDetails(List<OrdersDetailsDto> ordersDetailsDtos);

    List<OrdersDetailsDto> fromOrderDetails(List<OrdersDetails> ordersDetails);
}