package com.example.demo.web;

import com.example.demo.domain.MyUser;
import com.example.demo.domain.Post;
import com.example.demo.infrastructure.PostRepository;
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

import java.security.Principal;
import java.util.Set;
import java.util.ArrayList;


@Controller
public class UserController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @GetMapping("/signup")
    public String getSignUpPage(Principal principal, Model model) {
        model.addAttribute("user", principal);
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
        // to make user signup logins users into app automatically.
//        userRepository.findUserByUsername(newUser.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null,  new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        long id = newUser.getId();
        return new RedirectView("/users/" + id);

    }


    @GetMapping("/users/{id}")
    public String getUserProfilePage(@PathVariable long id, Model model,Principal p) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser currentUser = userRepository.findById(id).get();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", p);

        return "profile";
    }


    @GetMapping("/myprofile")
    public String getProfilePage(Principal principal, Model model) {
//            first way
//            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            MyUser currentUser = userRepository.findUserByUsername(userDetails.getUsername());

//            second way
            MyUser currentUser = userRepository.findUserByUsername(principal.getName());

            model.addAttribute("currentUser", currentUser);
            model.addAttribute("user", principal);
            Iterable<Post> posts = postRepository.findAll();
            model.addAttribute("posts", posts);
        return "profile";
    }


    //Lab18
    @GetMapping("/users")
    public String getAllUsers(Principal principal, Model model) {

        model.addAttribute("alluser", userRepository.findAll());
        model.addAttribute("user", principal);

        return "users";
    }
    @PostMapping ("/follow")
    public RedirectView followAUser(Principal principal, long followUser) {

        // user to follow and get current logged in user username
        MyUser poster = userRepository.getById(followUser);

        // get current logged in user username
        MyUser follower = userRepository.findUserByUsername(principal.getName());
        // add the curetn logged in user to the following of usertofollow
//        add usertofollow to current logged in user followers
        follower.followUser(poster);

        userRepository.save(follower);

        return new RedirectView("/myprofile");
    }

    @GetMapping("/feed")
    public String Feed(Principal principal, Model model) {


        MyUser currentUser = userRepository.findUserByUsername(principal.getName());

        Set<MyUser> followerList = currentUser.getUsersIFollowing();

        model.addAttribute("personThatIfollowList", followerList);

        model.addAttribute("user", principal);
        return "feed";
    }

}
