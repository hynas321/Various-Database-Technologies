package org.example.Mappers;

import org.bson.Document;
import org.example.Entities.Post;

import java.util.HashSet;

public class PostMapper implements EntityMapper<Post> {

    public Document toDocument(Post post) {
        return new Document("_id", post.getId())
                .append("content", post.getContent())
                .append("creatorId", post.getCreatorId())
                .append("boardId", post.getBoardId())
                .append("commentIds", post.getCommentIds());
    }

    public Post fromDocument(Document document) {
        Post post = new Post();
        post.setId(document.getString("_id"));
        post.setContent(document.getString("content"));
        post.setCreatorId(document.getString("creatorId"));
        post.setBoardId(document.getString("boardId"));
        post.setCommentIds(new HashSet<>(document.getList("commentIds", String.class)));
        return post;
    }
}
