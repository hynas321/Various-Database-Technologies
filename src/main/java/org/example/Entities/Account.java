package org.example.Entities;

import java.util.HashSet;
import java.util.Set;

public abstract class Account {
    private String id;
    private String email;
    private String password;
    private Set<String> postIds = new HashSet<>();
    private Set<String> boardIds = new HashSet<>();

    public Account() {}

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getPostIds() {
        return postIds;
    }

    public void setPostIds(Set<String> postIds) {
        this.postIds = postIds;
    }

    public Set<String> getBoardIds() {
        return boardIds;
    }

    public void setBoardIds(Set<String> boardIds) {
        this.boardIds = boardIds;
    }
}
