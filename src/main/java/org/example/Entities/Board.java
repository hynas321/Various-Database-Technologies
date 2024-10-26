package org.example.Entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.example.Redis.ObjectIdDeserializer;
import org.example.Redis.ObjectIdSerializer;

import java.util.HashSet;
import java.util.Set;

public class Board {

    @BsonId
    @JsonSerialize(using = ObjectIdSerializer.class)
    @JsonDeserialize(using = ObjectIdDeserializer.class)
    private ObjectId id;

    @BsonProperty("name")
    private String name;

    @BsonProperty("postIds")
    private Set<ObjectId> postIds = new HashSet<>();

    @BsonProperty("memberIds")
    private Set<ObjectId> memberIds = new HashSet<>();

    public Board() {}

    @BsonCreator
    public Board(@BsonProperty("name") String name) {
        this.name = name;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ObjectId> getPostIds() {
        return postIds;
    }

    public void setPostIds(Set<ObjectId> postIds) {
        this.postIds = postIds;
    }

    public Set<ObjectId> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(Set<ObjectId> memberIds) {
        this.memberIds = memberIds;
    }
}
