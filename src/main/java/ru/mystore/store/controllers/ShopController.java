package ru.mystore.store.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.mystore.store.beans.Cart;
import ru.mystore.store.services.ProductService;
import ru.mystore.store.services.ShopuserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ShopController {

    private final Cart cart;
    private final ProductService productService;
    private final ShopuserService shopuserService;

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String index(Model model, @RequestParam(required = false) Integer category) {
        model.addAttribute("cart", cart.getCartRecords());
        model.addAttribute("products", productService.findAll(category));
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(Model model, @CookieValue(value = "data", required = false) String data, Principal principal) {

        if (principal == null) {
            return "redirect:/";
        }

        if (data != null) {
            System.out.println(data);
        }

        model.addAttribute("products", productService.findAll(null));

        return "admin";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, @CookieValue(value = "data", required = false) String data, Principal principal) {

        if (principal == null) {
            return "redirect:/";
        }

        model.addAttribute("shopuser", shopuserService.findByPhone(principal.getName()));

        if (data != null) {
            System.out.println(data);
        }

        return "profile";
    }

}