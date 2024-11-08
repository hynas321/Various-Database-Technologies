package org.example.Entities;

import com.datastax.oss.driver.api.mapper.annotations.*;
import java.util.UUID;

@Entity
@CqlName("comments")
public class Comment {

    @PartitionKey
    private UUID id;

    @CqlName("content")
    private String content;

    @CqlName("post_id")
    private UUID postId;

    @CqlName("creator_id")
    private UUID creatorId;

    public Comment() {
        this.id = UUID.randomUUID();
    }

    public Comment(String content, UUID postId, UUID creatorId) {
        this.id = UUID.randomUUID();
        this.content = content;
        this.postId = postId;
        this.creatorId = creatorId;
    }

    public Comment(UUID id, String content, UUID postId, UUID creatorId) {
        this.id = id;
        this.content = content;
        this.postId = postId;
        this.creatorId = creatorId;
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

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public UUID getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(UUID creatorId) {
        this.creatorId = creatorId;
    }
}