package com.example.demo.web;

import com.example.demo.domain.MyUser;
import com.example.demo.domain.Post;
import com.example.demo.infrastructure.PostRepository;
import com.example.demo.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
public class PostController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @GetMapping("/posts")
    public String getPostPage() {
        return "addpost";
    }


    @PostMapping("/posts")
    public RedirectView addPost(String body, Principal p, Model m) {
        MyUser currentUser = userRepository.findUserByUsername(p.getName());
        Timestamp timeStamp = Timestamp.valueOf(LocalDateTime.now());
        Post newPost = new Post(body, timeStamp, currentUser);
        postRepository.save(newPost);
        m.addAttribute("profile", currentUser);
        m.addAttribute("user", p);
        return new RedirectView("/myprofile");
    }
}
