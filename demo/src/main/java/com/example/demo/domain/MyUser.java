package com.example.demo.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
public class MyUser implements UserDetails {
//    @NotNull: a constrained CharSequence, Collection, Map, or Array is valid as long as it's not null, but it can be empty
//    @NotEmpty: a constrained CharSequence, Collection, Map, or Array is valid as long as it's not null and its size/length is greater than zero
//    @NotBlank: a constrained String is valid as long as it's not null and the trimmed length is greater than zero

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty()
    @Size(min = 8)
    @Column(nullable = false)
    private String password;

    @NotEmpty()
    @Size(max = 35)
    @Column(unique = true, nullable = false)
    private String username;

    @NotEmpty()
    @NotEmpty()
    @Size(max = 15)
    @Column(nullable = false)
    private String firstName;

    @NotEmpty()
    @Size(max = 15)
    @Column(nullable = false)
    private String lastName;
    private String dateOfBirth;
    private String bio;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "loggedInUser", cascade = CascadeType.ALL)
    @OrderBy("id")
    private List<Post> posts;

    public MyUser() {
    }

    public MyUser(String password, String username, String firstName, String lastName, String bio, String dateOfBirth) {
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.dateOfBirth = dateOfBirth;
    }

    //lab18
    @ManyToMany
    @JoinTable(
            name = "user_following",
            joinColumns = {@JoinColumn(name = "from_id")},
            inverseJoinColumns = {@JoinColumn(name = "to_id")}
    )
    public List<MyUser> following;

    @ManyToMany(mappedBy = "following", fetch = FetchType.EAGER)
    public List<MyUser> followers;


    public void setFollowers(MyUser myUser) {
        this.followers.add(myUser);
    }

    public void follow(MyUser myUser) {
        this.following.add(myUser);
    }

    public void unfollow(MyUser myUser) {
        following.remove(myUser);
    }


    public List<MyUser> getFollowing() {
        return following;
    }

    public List<MyUser> getFollowers() {
        return followers;
    }

//Setters and getters

    public long getId() {
        return id;
    }


    @Override
    public String getUsername() {
        return username;
    }


    @Override
    public String getPassword() {
        return password;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public List<Post> getPosts() {
        return posts;
    }

    //Spring security override methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //toString
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

}