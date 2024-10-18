package org.example.Repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.Entities.Post;
import org.example.Mappers.EntityMapper;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class PostRepository implements EntityRepository<Post> {
    private final MongoCollection<Document> collection;
    private final EntityMapper<Post> postMapper;

    public PostRepository(MongoDatabase database, EntityMapper<Post> postMapper) {
        this.collection = database.getCollection("posts");
        this.postMapper = postMapper;
    }

    @Override
    public void create(Post post) {
        Document postDocument = postMapper.toDocument(post);
        collection.insertOne(postDocument);
    }

    @Override
    public Post getById(String id) {
        Document document = collection.find(eq("_id", id)).first();

        if (document != null) {
            return postMapper.fromDocument(document);
        }

        return null;
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();

        for (Document document : collection.find()) {
            posts.add(postMapper.fromDocument(document));
        }

        return posts;
    }

    @Override
    public void update(Post post) {
        Document postDoc = postMapper.toDocument(post);
        collection.replaceOne(eq("_id", post.getId()), postDoc);
    }

    @Override
    public void delete(Post post) {
        collection.deleteOne(eq("_id", post.getId()));
    }
}
