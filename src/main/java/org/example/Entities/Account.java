package org.example.Entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.example.Redis.ObjectIdDeserializer;
import org.example.Redis.ObjectIdSerializer;

import java.util.HashSet;
import java.util.Set;

@BsonDiscriminator(key = "type", value = "Account")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Admin.class, name = "Admin"),
        @JsonSubTypes.Type(value = User.class, name = "User")
})
public abstract class Account {
    @BsonId
    @JsonSerialize(using = ObjectIdSerializer.class)
    @JsonDeserialize(using = ObjectIdDeserializer.class)
    private ObjectId id;

    @BsonProperty("email")
    private String email;

    @BsonProperty("accountPassword")
    private String accountPassword;

    @BsonProperty("postIds")
    private Set<ObjectId> postIds = new HashSet<>();

    @BsonProperty("boardIds")
    private Set<ObjectId> boardIds = new HashSet<>();

    public Account() {}

    @BsonCreator
    public Account(
            @BsonProperty("email") String email,
            @BsonProperty("accountPassword") String accountPassword) {
        this.email = email;
        this.accountPassword = accountPassword;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public Set<ObjectId> getPostIds() {
        return postIds;
    }

    public void setPostIds(Set<ObjectId> postIds) {
        this.postIds = postIds;
    }

    public Set<ObjectId> getBoardIds() {
        return boardIds;
    }

    public void setBoardIds(Set<ObjectId> boardIds) {
        this.boardIds = boardIds;
    }
}
