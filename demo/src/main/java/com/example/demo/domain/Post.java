package com.example.demo.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    String body;

    //    @DateTimeFormat(pattern = "mm-dd-yyyy HH:mm:ss")
    Timestamp createdAt;
    @ManyToOne
    MyUser loggedInUser; //the entity that does the mapping

    public Post() {
    }

    public Post(String body, Timestamp createdAt, MyUser loggedInUser) {
        this.body = body;
        this.createdAt = createdAt;
        this.loggedInUser = loggedInUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public MyUser getLoggedInUser() {
        return loggedInUser;
    }

    @Override
    public String toString() {
        return "Post{" +
                "body='" + body + '\'' +
                ", createdAt=" + createdAt +
                ", loggedInUser=" + loggedInUser.getUsername() +
                '}';
    }
}
