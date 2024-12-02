package org.example.Repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.example.Entities.Board;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class BoardRepository implements EntityRepository<Board> {
    private final MongoCollection<Board> collection;

    public BoardRepository(MongoDatabase database) {
        this.collection = database.getCollection("boards", Board.class);
    }

    @Override
    public void create(Board board) {
        collection.insertOne(board);
    }

    @Override
    public Board getById(ObjectId id) {
        return collection.find(eq("_id", id)).first();
    }

    @Override
    public List<Board> getAll() {
        return collection.find().into(new ArrayList<>());
    }

    @Override
    public void update(Board board) {
        collection.replaceOne(eq("_id", board.getId()), board);
    }

    @Override
    public void delete(Board board) {
        collection.deleteOne(eq("_id", board.getId()));
    }
}