package org.example.Repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.example.Entities.Comment;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class CommentRepository implements EntityRepository<Comment> {
    private final MongoCollection<Comment> collection;

    public CommentRepository(MongoDatabase database) {
        this.collection = database.getCollection("comments", Comment.class);
    }

    @Override
    public void create(Comment comment) {
        collection.insertOne(comment);
    }

    @Override
    public Comment getById(ObjectId id) {
        return collection.find(eq("_id", id)).first();
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> comments = new ArrayList<>();
        collection.find().into(comments);
        return comments;
    }

    @Override
    public void update(Comment comment) {
        collection.replaceOne(eq("_id", comment.getId()), comment);
    }

    @Override
    public void delete(Comment comment) {
        collection.deleteOne(eq("_id", comment.getId()));
    }
}
