package com.geekbrains.shop.service;

import com.geekbrains.shop.dao.CartRepository;
import com.geekbrains.shop.dao.ProductRepository;
import com.geekbrains.shop.entities.Cart;
import com.geekbrains.shop.entities.Product;
import com.geekbrains.shop.entities.User;
import com.geekbrains.shop.dto.CartDetailDto;
import com.geekbrains.shop.dto.CartDto;
import com.geekbrains.shop.service.CartServiceImpl;
import com.geekbrains.shop.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.UUID;

public class CartServiceImplTest {

    private CartServiceImpl cartService;
    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        cartRepository = Mockito.mock(CartRepository.class);
        productRepository = Mockito.mock(ProductRepository.class);
        userService = Mockito.mock(UserService.class);
        cartService = new CartServiceImpl(cartRepository, productRepository, userService);
    }

    @Test
    void checkGetCartDtoByUser() {
        String name = "admin";
        User user = User.builder().id(1L).name(name).build();
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(7L, new ArrayList<>(), "tangerines", "Description 1", 5.0));
        products.add(new Product(8L, new ArrayList<>(), "sausage", "Description 2", 10.0));
        Cart expectedCart = new Cart(7L, user, products);
        user.setCart(expectedCart);
        ArrayList<CartDetailDto> cartDetailDtos = new ArrayList<>();
        for (Product product : products){
            cartDetailDtos.add(new CartDetailDto(product));
        }
        CartDto expectedCartDto = CartDto.builder().amountProducts(2).sum(15.0).cartDetails(cartDetailDtos).build();

        Mockito.when(userService.findByName(Mockito.anyString())).thenReturn(user);
        CartDto actualCartDto = cartService.getCartDtoByUser(name);

        Assertions.assertNotNull(actualCartDto);
        Assertions.assertEquals(expectedCartDto, actualCartDto);

    }


}