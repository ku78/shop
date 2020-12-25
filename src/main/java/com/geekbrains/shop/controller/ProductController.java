package com.geekbrains.shop.controller;

import com.geekbrains.shop.dto.CartDto;
import com.geekbrains.shop.dto.ProductDto;
import com.geekbrains.shop.service.CartService;
import com.geekbrains.shop.service.ProductService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CartService cartService;

    public ProductController(ProductService productService, CartService cartService) {
        this.productService = productService;
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

    @GetMapping
    public String list(Model model){
        List<ProductDto> list = productService.getAll();
        model.addAttribute("products", list);
        return "products";
    }

    @GetMapping("/{id}/cart")
    public String addCart(@PathVariable Long id, Principal principal){
        if(principal == null){
            return "redirect:/products";
        }
        productService.addToUserCart(id, principal.getName());
        return "redirect:/products";
    }

    @MessageMapping("products/addToCart")
    public void messageAddProduct(ProductDto dto, Principal principal){
        if(principal == null){
            return;
        }
        productService.addToUserCart(dto.getId(), principal.getName());
    }

}
