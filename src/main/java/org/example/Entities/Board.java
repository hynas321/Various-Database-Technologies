package org.example.Entities;

import java.util.HashSet;
import java.util.Set;

public class Board {
    private String id;
    private String name;
    private Set<String> postIds = new HashSet<>();
    private Set<String> memberIds = new HashSet<>();

    public Board() {}

    public Board(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getPostIds() {
        return postIds;
    }

    public void setPostIds(Set<String> postIds) {
        this.postIds = postIds;
    }

    public Set<String> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(Set<String> memberIds) {
        this.memberIds = memberIds;
    }
}
