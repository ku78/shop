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
    private Double sum;

    public OrdersDetailsDto(Product product) {
        this.product = product;
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.amount = 1.0;
        this.sum = product.getPrice();
    }
}
