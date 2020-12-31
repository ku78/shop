package com.geekbrains.shop.service;

import com.geekbrains.shop.dao.ProductRepository;
import com.geekbrains.shop.entities.Cart;
import com.geekbrains.shop.entities.User;
import com.geekbrains.shop.dto.ProductDto;
import com.geekbrains.shop.mapper.ProductMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper = ProductMapper.MAPPER;

    private final ProductRepository productRepository;
    private final UserService userService;
    private final CartService cartService;

    private final SimpMessagingTemplate template;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService, CartService cartService, SimpMessagingTemplate template) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.cartService = cartService;
        this.template = template;
    }

    @Override
    public List<ProductDto> getAll() {
        return mapper.fromProductList(productRepository.findAll());
    }

    @Override
    @Transactional
    public void addToUserCart(Long productId, String username) {
        User user = userService.findByName(username);
        if (user == null) {
            throw new RuntimeException("User not found. " + username);
        }
        Cart cart = user.getCart();
        if (cart == null) {
            cart = cartService.createCart(user, Collections.singletonList(productId));
            user.setCart(cart);
            userService.save(user);
        } else {
            cartService.addProducts(cart, Collections.singletonList(productId));
        }
        template.convertAndSend("/topic/addToCart", cartService.fromCartToCartDto(cart));
    }
}
