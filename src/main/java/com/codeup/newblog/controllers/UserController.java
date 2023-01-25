package com.codeup.newblog.controllers;

import com.codeup.newblog.models.User;
import com.codeup.newblog.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserRepository usersDao;

    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository usersDao, PasswordEncoder passwordEncoder) {
        this.usersDao = usersDao;
        this.passwordEncoder = passwordEncoder;
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
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        usersDao.save(user);
        return "redirect:/posts";
    }

    @GetMapping("users/{id}")
    public String showUser(Model model, @PathVariable long id) {
        User user = usersDao.getById(id);
        model.addAttribute("user", user);
        return "users/profile";
    }
}
