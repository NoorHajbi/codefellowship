package com.example.demo.web;

import com.example.demo.domain.MyUser;
import com.example.demo.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @GetMapping("/signup")
    public String getSignUpPage() {
        return "signup";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/signup")
    public RedirectView attemptSignUp(@RequestParam(value = "password") String password,
                                      @RequestParam(value = "username") String username,
                                      @RequestParam(value = "firstName") String firstName,
                                      @RequestParam(value = "lastName") String lastName,
                                      @RequestParam(value = "bio") String bio,
                                      @RequestParam(value = "dateOfBirth") String date
    ) {
        MyUser newUser = new MyUser(encoder.encode(password), username, firstName, lastName, bio, date);
        newUser = userRepository.save(newUser);
        // to make user registration logs users into app automatically.
        userRepository.findUserByUsername(newUser.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, newUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        long id = newUser.getId();
        return new RedirectView("/users/" + id);

    }


    @GetMapping("/users/{id}")
    public String getUserProfilePage(@PathVariable long id, Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser currentUser = userRepository.findUserByUsername(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);
        return "profile";
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser currentUser = userRepository.findUserByUsername(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", true);
        return "profile";
    }


}
