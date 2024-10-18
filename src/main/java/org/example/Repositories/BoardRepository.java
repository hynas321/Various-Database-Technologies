package org.example.Repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.Entities.Board;
import org.example.Mappers.EntityMapper;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class BoardRepository implements EntityRepository<Board> {
    private final MongoCollection<Document> collection;
    private final EntityMapper<Board> boardMapper;

    public BoardRepository(MongoDatabase database, EntityMapper<Board> boardMapper) {
        this.collection = database.getCollection("boards");
        this.boardMapper = boardMapper;
    }

    @Override
    public void create(Board board) {
        Document boardDoc = boardMapper.toDocument(board);
        collection.insertOne(boardDoc);
    }

    @Override
    public Board getById(String id) {
        Document document = collection.find(eq("_id", id)).first();

        if (document != null) {
            return boardMapper.fromDocument(document);
        }

        return null;
    }

    @Override
    public List<Board> getAll() {
        List<Board> boards = new ArrayList<>();

        for (Document document : collection.find()) {
            boards.add(boardMapper.fromDocument(document));
        }

        return boards;
    }

    @Override
    public void update(Board board) {
        Document boardDocument = boardMapper.toDocument(board);
        collection.replaceOne(eq("_id", board.getId()), boardDocument);
    }

    @Override
    public void delete(Board board) {
        collection.deleteOne(eq("_id", board.getId()));
    }
}
