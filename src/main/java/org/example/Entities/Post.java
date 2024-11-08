package org.example.Entities;

import com.datastax.oss.driver.api.mapper.annotations.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@CqlName("posts")
public class Post {

    @PartitionKey
    private UUID id;

    @CqlName("content")
    private String content;

    @CqlName("creator_id")
    private UUID creatorId;

    @CqlName("board_id")
    private UUID boardId;

    @CqlName("comment_ids")
    private Set<UUID> commentIds = new HashSet<>();

    public Post() {
        this.id = UUID.randomUUID();
    }

    public Post(String content, UUID creatorId, UUID boardId) {
        this.id = UUID.randomUUID();
        this.content = content;
        this.creatorId = creatorId;
        this.boardId = boardId;
    }

    public Post(UUID id, String content, UUID creatorId, UUID boardId, Set<UUID> commentIds) {
        this.id = id;
        this.content = content;
        this.creatorId = creatorId;
        this.boardId = boardId;
        this.commentIds = commentIds;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(UUID creatorId) {
        this.creatorId = creatorId;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public Set<UUID> getCommentIds() {
        return commentIds;
    }

    public void setCommentIds(Set<UUID> commentIds) {
        this.commentIds = commentIds;
    }
}