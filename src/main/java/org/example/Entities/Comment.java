package org.example.Entities;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Comment {

    @BsonId
    private ObjectId id;

    @BsonProperty("content")
    private String content;

    @BsonProperty("postId")
    private ObjectId postId;

    @BsonProperty("creatorId")
    private ObjectId creatorId;

    @BsonCreator
    public Comment(
            @BsonProperty("content") String content,
            @BsonProperty("postId") ObjectId postId,
            @BsonProperty("creatorId") ObjectId creatorId) {
        this.content = content;
        this.postId = postId;
        this.creatorId = creatorId;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ObjectId getPostId() {
        return postId;
    }

    public void setPostId(ObjectId postId) {
        this.postId = postId;
    }

    public ObjectId getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(ObjectId creatorId) {
        this.creatorId = creatorId;
    }
}
