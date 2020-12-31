package com.geekbrains.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {
    private double amountProducts;
    private double sum;
    private List<CartDetailDto> cartDetails = new ArrayList<>();

    public void aggregate() {
        for (CartDetailDto cartDetailDto : cartDetails) {
            this.amountProducts = this.amountProducts + cartDetailDto.getAmount();
            this.sum = this.sum + cartDetailDto.getSum();
        }
    }
}