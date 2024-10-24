package org.example.Entities;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.HashSet;
import java.util.Set;

public class Post {

    @BsonId
    private ObjectId id;

    @BsonProperty("content")
    private String content;

    @BsonProperty("creatorId")
    private ObjectId creatorId;

    @BsonProperty("boardId")
    private ObjectId boardId;

    @BsonProperty("commentIds")
    private Set<ObjectId> commentIds = new HashSet<>();

    @BsonCreator
    public Post(
            @BsonProperty("content") String content,
            @BsonProperty("creatorId") ObjectId creatorId,
            @BsonProperty("boardId") ObjectId boardId) {
        this.content = content;
        this.creatorId = creatorId;
        this.boardId = boardId;
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

    public ObjectId getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(ObjectId creatorId) {
        this.creatorId = creatorId;
    }

    public ObjectId getBoardId() {
        return boardId;
    }

    public void setBoardId(ObjectId boardId) {
        this.boardId = boardId;
    }

    public Set<ObjectId> getCommentIds() {
        return commentIds;
    }

    public void setCommentIds(Set<ObjectId> commentIds) {
        this.commentIds = commentIds;
    }
}
