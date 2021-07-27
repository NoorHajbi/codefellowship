package com.example.demo.web;

import com.example.demo.domain.User;
import com.example.demo.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/profile")
    public String getProfilePage(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", userDetails.getUsername());
        return "profile";
    }



    @PostMapping("/signup")
    public RedirectView attemptSignUp(@RequestParam(value = "username") String username,
                                      @RequestParam(value = "password") String password,
                                      @RequestParam(value = "firstName") String firstName,
                                      @RequestParam(value = "lastName") String lastName,
                                      @RequestParam(value = "dateOfBirth") String date,
                                      @RequestParam(value = "bio") String bio) {
        User newUser = new User(username, encoder.encode(password), firstName, lastName, date, bio);
        newUser = userRepository.save(newUser);

//        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, newUser.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        return new RedirectView("/");
        return new RedirectView("/login");
    }

    //Work more with the user and profile
    @GetMapping("/user/{id}")
    public String getUserProfilePage(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", userDetails.getUsername());
        return "profile";
    }

    @GetMapping("/access-denied")
    public String getAccessDenied() {
        return "/403";
    }
}
