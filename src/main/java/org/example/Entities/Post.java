package org.example.Entities;

import java.util.HashSet;
import java.util.Set;

public class Post {
    private String id;
    private String content;
    private String creatorId;
    private String boardId;
    private Set<String> commentIds = new HashSet<>();

    public Post() {}

    public Post(String content, String creatorId, String boardId) {
        this.content = content;
        this.creatorId = creatorId;
        this.boardId = boardId;
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

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public Set<String> getCommentIds() {
        return commentIds;
    }

    public void setCommentIds(Set<String> commentIds) {
        this.commentIds = commentIds;
    }
}
