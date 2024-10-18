package org.example.Mappers;

import org.bson.Document;
import org.example.Entities.Board;

import java.util.HashSet;

public class BoardMapper implements EntityMapper<Board> {

    public Document toDocument(Board board) {
        return new Document("_id", board.getId())
                .append("name", board.getName())
                .append("postIds", board.getPostIds())
                .append("memberIds", board.getMemberIds());
    }

    public Board fromDocument(Document document) {
        Board board = new Board();
        board.setId(document.getString("_id"));
        board.setName(document.getString("name"));
        board.setPostIds(new HashSet<>(document.getList("postIds", String.class)));
        board.setMemberIds(new HashSet<>(document.getList("memberIds", String.class)));
        return board;
    }
}