package com.geekbrains.shop.mapper;
import com.geekbrains.shop.dto.OrdersDetailsDto;
import com.geekbrains.shop.entities.OrdersStatus;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderDetailsMapper  {
    OrderDetailsMapper MAPPER = Mappers.getMapper(OrderDetailsMapper.class);

    OrdersStatus toOrderDetails(OrdersDetailsDto orderDetailsDto);

    @InheritInverseConfiguration
    OrdersDetailsDto fromOrderDetails(OrdersStatus orderDetails);

    List<OrdersStatus> toOrderDetails(List<OrdersDetailsDto> orderDetailsDtos);

    List<OrdersDetailsDto> fromOrderDetails(List<OrdersStatus> orderDetails);
}