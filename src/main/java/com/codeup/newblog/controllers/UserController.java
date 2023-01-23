package com.codeup.newblog.controllers;

import com.codeup.newblog.models.User;
import com.codeup.newblog.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserRepository usersDao;

    public UserController(UserRepository usersDao) {
        this.usersDao = usersDao;
    }

    @GetMapping("users")
    public String usersIndex(Model model) {
        model.addAttribute("users", usersDao.findAll());
        return "users/index";
    }

    @GetMapping("users/create")
    public String showCreateUser(Model model) {
        model.addAttribute("user", new User());
        return "users/create";
    }

    @PostMapping("users/create")
    public String createUser(@ModelAttribute User user) {
        usersDao.save(user);
        return "redirect:/posts";
    }
}
