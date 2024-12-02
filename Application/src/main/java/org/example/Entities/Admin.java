package org.example.Entities;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "type", value = "Admin")
@JsonTypeName("Admin")
public class Admin extends Account {

    public Admin() {}

    @BsonCreator
    public Admin(
            @BsonProperty("email") String email,
            @BsonProperty("accountPassword") String accountPassword) {
        super(email, accountPassword);
    }
}
