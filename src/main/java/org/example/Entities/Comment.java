package org.example.Entities;

public class Comment {
    private String id;
    private String content;
    private String postId;
    private String creatorId;

    public Comment() {}

    public Comment(String content, String postId, String creatorId) {
        this.content = content;
        this.postId = postId;
        this.creatorId = creatorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
}
