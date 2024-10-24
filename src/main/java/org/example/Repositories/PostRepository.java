package org.example.Repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.example.Entities.Post;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class PostRepository implements EntityRepository<Post> {
    private final MongoCollection<Post> collection;

    public PostRepository(MongoDatabase database) {
        this.collection = database.getCollection("posts", Post.class);
    }

    @Override
    public void create(Post post) {
        collection.insertOne(post);
    }

    @Override
    public Post getById(ObjectId id) {
        return collection.find(eq("_id", id)).first();
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        collection.find().into(posts);
        return posts;
    }

    @Override
    public void update(Post post) {
        collection.replaceOne(eq("_id", post.getId()), post);
    }

    @Override
    public void delete(Post post) {
        collection.deleteOne(eq("_id", post.getId()));
    }
}
