package com.geekbrains.shop.service;

import com.geekbrains.shop.entities.Cart;
import com.geekbrains.shop.entities.User;
import com.geekbrains.shop.dto.CartDto;

import java.util.List;

public interface CartService {
    Cart createCart(User user, List<Long> productIds);
    void addProducts(Cart cart, List<Long> productIds);
    CartDto getCartDtoByUser(String name);
    CartDto fromCartToCartDto(Cart cart);
}
