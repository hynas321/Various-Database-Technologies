package org.example.Mappers;

import org.example.Entities.Comment;
import org.bson.Document;

public class CommentMapper implements EntityMapper<Comment> {

    public Document toDocument(Comment comment) {
        return new Document("_id", comment.getId())
                .append("content", comment.getContent())
                .append("postId", comment.getPostId())
                .append("creatorId", comment.getCreatorId());
    }

    public Comment fromDocument(Document document) {
        Comment comment = new Comment();
        comment.setId(document.getString("_id"));
        comment.setContent(document.getString("content"));
        comment.setPostId(document.getString("postId"));
        comment.setCreatorId(document.getString("creatorId"));
        return comment;
    }
}
