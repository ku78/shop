package com.geekbrains.shop.controller;

import com.geekbrains.shop.dto.CartDto;
import com.geekbrains.shop.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @ModelAttribute("cart")
    public CartDto addAttributeCart(Principal principal){
        if (principal == null) {
            return new CartDto();
        } else {
            return cartService.getCartDtoByUser(principal.getName());
        }
    }

    @GetMapping("/cart")
    public String cartInfo(Model model, Principal principal) {
        return "cart";
    }
}
