package com.geekbrains.shop.service;

import com.geekbrains.shop.dto.ProductDto;

import java.util.List;


public interface ProductService {
    List<ProductDto> getAll();
    void addToUserCart(Long productId, String username);
}