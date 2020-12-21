package com.geekbrains.shop.dto;

import com.geekbrains.shop.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersDetailsDto {
    private String title;
    private Product product;
    private Double price;
    private Double amount;

}
