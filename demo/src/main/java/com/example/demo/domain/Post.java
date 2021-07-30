package com.example.demo.domain;

import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    String body;
    @DateTimeFormat(pattern = "mm-dd-yyyy HH:mm:ss")
    Date createdAt;

    @ManyToOne
    MyUser myUser;

    public Post() {
    }

    public Post(String body, MyUser myUser) {
        this.body = body;
        this.myUser = myUser;
        this.createdAt = new Date();
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

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public MyUser getMyUser() {
        return myUser;
    }

    public void My(MyUser myUser) {
        this.myUser = myUser;
    }
}
