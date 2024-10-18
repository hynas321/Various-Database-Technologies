package org.example.Repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.Entities.Comment;
import org.example.Mappers.EntityMapper;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class CommentRepository implements EntityRepository<Comment> {
    private final MongoCollection<Document> collection;
    private final EntityMapper<Comment> commentMapper;

    public CommentRepository(MongoDatabase database, EntityMapper<Comment> commentMapper) {
        this.collection = database.getCollection("comments");
        this.commentMapper = commentMapper;
    }

    @Override
    public void create(Comment comment) {
        Document commentDocument = commentMapper.toDocument(comment);
        collection.insertOne(commentDocument);
    }

    @Override
    public Comment getById(String id) {
        Document document = collection.find(eq("_id", id)).first();

        if (document != null) {
            return commentMapper.fromDocument(document);
        }

        return null;
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> comments = new ArrayList<>();

        for (Document document : collection.find()) {
            comments.add(commentMapper.fromDocument(document));
        }

        return comments;
    }

    @Override
    public void update(Comment comment) {
        Document commentDocument = commentMapper.toDocument(comment);
        collection.replaceOne(eq("_id", comment.getId()), commentDocument);
    }

    @Override
    public void delete(Comment comment) {
        collection.deleteOne(eq("_id", comment.getId()));
    }
}
