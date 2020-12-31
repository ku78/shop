package com.geekbrains.shop.controller;

import com.geekbrains.shop.dto.CartDto;
import com.geekbrains.shop.service.CartService;
import com.geekbrains.shop.service.SessionObjectHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.UUID;

@Controller
public class MainController {
    private final SessionObjectHolder sessionObjectHolder;
    private final CartService cartService;

    public MainController(SessionObjectHolder sessionObjectHolder, CartService cartService) {
        this.sessionObjectHolder = sessionObjectHolder;
        this.cartService = cartService;
    }

    @ModelAttribute("cart")
    public CartDto addAttributeCart(Principal principal) {
        if (principal == null) {
            return new CartDto();
        } else {
            return cartService.getCartDtoByUser(principal.getName());
        }
    }

    @RequestMapping({"", "/"})
    public String index(Model model, HttpSession httpSession) {
        model.addAttribute("amountClicks", sessionObjectHolder.getAmountClicks());
        if (httpSession.getAttribute("myID") == null) {
            String uuid = UUID.randomUUID().toString();
            httpSession.setAttribute("myID", uuid);
            System.out.println("Generated UUID -> " + uuid);
        }
        model.addAttribute("uuid", httpSession.getAttribute("myID"));
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/log")
    public String list(Model model) {
        model.addAttribute("logDetails", sessionObjectHolder.getLogDetails());
        return "log";
    }
}