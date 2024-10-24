package org.example.Entities;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "type", value = "User")
public class User extends Account {

    @BsonCreator
    public User(
            @BsonProperty("email") String email,
            @BsonProperty("accountPassword") String accountPassword) {
        super(email, accountPassword);
    }
}
