package com.codeup.newblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("login")
    public String showLogin() {
        return "users/login";
    }
}
