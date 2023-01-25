package com.codeup.newblog.controllers;

import com.codeup.newblog.models.Post;
import com.codeup.newblog.repositories.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;

@Controller
public class PostController {
    private PostRepository postsDao;

    public PostController(PostRepository postsDao) {
        this.postsDao = postsDao;
    }

    @GetMapping("posts")
    public String postsIndex(Model model) throws ParseException {
        model.addAttribute("posts", postsDao.findAll());
        return "posts/index";
    }

    @GetMapping("posts/create")
    public String showCreatePost(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("posts/create")
    public String createPost(@ModelAttribute Post post) {
        postsDao.save(post);
        return "redirect:/posts";
    }

    @GetMapping("posts/{id}")
    public String showPost(Model model, @PathVariable long id) {
        Post post = postsDao.getById(id);
        model.addAttribute("post", post);
        return "posts/show";
    }
}
