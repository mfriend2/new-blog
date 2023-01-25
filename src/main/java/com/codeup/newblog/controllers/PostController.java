package com.codeup.newblog.controllers;

import com.codeup.newblog.models.Post;
import com.codeup.newblog.models.User;
import com.codeup.newblog.repositories.PostRepository;
import com.codeup.newblog.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
    private PostRepository postsDao;
    private UserRepository usersDao;

    public PostController(PostRepository postsDao, UserRepository usersDao) {
        this.usersDao = usersDao;
        this.postsDao = postsDao;
    }

    @GetMapping("posts")
    public String postsIndex(Model model) throws ParseException {
        model.addAttribute("posts", postsDao.findAll());
        return "posts/index";
    }

    @GetMapping("posts/create")
    public String showCreatePost(Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (String.valueOf(currentUser).equalsIgnoreCase("anonymousUser")) {
            return "redirect:/login";
        }
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("posts/create")
    public String createPost(@ModelAttribute Post post) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserID = currentUser.getId();
        List<Post> currentUserPosts = usersDao.getById(currentUserID).getPosts();
        post.setUser(currentUser);
        postsDao.save(post);
        if (currentUserPosts == null) {
            currentUser.setPosts(new ArrayList<>());
            currentUserPosts.add(post);
            return "redirect:/posts";
        }
        else {
            currentUserPosts.add(post);
            return "redirect:/posts";
        }
    }

    @GetMapping("posts/{id}")
    public String showPost(Model model, @PathVariable long id) {
        Post post = postsDao.getById(id);
        model.addAttribute("post", post);
        return "posts/show";
    }
}
