package com.geekbrains.shop.service;

import com.geekbrains.shop.dao.CartRepository;
import com.geekbrains.shop.dao.ProductRepository;
import com.geekbrains.shop.entities.Cart;
import com.geekbrains.shop.entities.Product;
import com.geekbrains.shop.entities.User;
import com.geekbrains.shop.dto.CartDetailDto;
import com.geekbrains.shop.dto.CartDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, UserService userService) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Cart createCart(User user, List<Long> productIds) {
        Cart cart = new Cart();
        cart.setUser(user);
        List<Product> productList = getCollectRefProductsByIds(productIds);
        cart.setProducts(productList);
        return cartRepository.save(cart);
    }

    private List<Product> getCollectRefProductsByIds(List<Long> productIds) {
        return productIds.stream()
                .map(productRepository::getOne)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addProducts(Cart cart, List<Long> productIds) {
        List<Product> products = cart.getProducts();
        List<Product> newProductsList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductsList.addAll(getCollectRefProductsByIds(productIds));
        cart.setProducts(newProductsList);
        cartRepository.save(cart);
    }

    @Override
    public CartDto getCartDtoByUser(String name) {
        User user = userService.findByName(name);
        if (user == null || user.getCart() == null) {
            return new CartDto();
        }
        return fromCartToCartDto(user.getCart());
    }

    public CartDto fromCartToCartDto(Cart cart) {
        CartDto cartDto = new CartDto();
        Map<Long, CartDetailDto> mapByProductId = new HashMap<>();
        List<Product> products = cart.getProducts();
        for (Product product : products) {
            CartDetailDto detail = mapByProductId.get(product.getId());
            if (detail == null) {
                mapByProductId.put(product.getId(), new CartDetailDto(product));
            } else {
                detail.setAmount(detail.getAmount() + 1.0);
                detail.setSum(detail.getSum() + product.getPrice());
            }
        }
        cartDto.setCartDetails(new ArrayList<>(mapByProductId.values()));
        cartDto.aggregate();
        return cartDto;
    }

}