package org.example.Entities;

import com.datastax.oss.driver.api.mapper.annotations.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(defaultKeyspace = "site")
@CqlName("accounts")
public class Account {

    public enum AccountType {
        USER, ADMIN
    }

    @PartitionKey
    private UUID id;

    @CqlName("email")
    private String email;

    private String password;

    @ClusteringColumn
    @CqlName("type")
    private String type;

    @CqlName("post_ids")
    private Set<UUID> postIds = new HashSet<>();

    @CqlName("board_ids")
    private Set<UUID> boardIds = new HashSet<>();

    public Account() {

    }

    public Account(String email, String password, AccountType type) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.password = password;
        this.type = type.name();
    }

    public Account(UUID id, String email, String password, String type,
                   Set<UUID> postIds, Set<UUID> boardIds) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.type = type;
        this.postIds = postIds;
        this.boardIds = boardIds;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type.name();
    }

    public Set<UUID> getPostIds() {
        return postIds;
    }

    public void setPostIds(Set<UUID> postIds) {
        this.postIds = postIds;
    }

    public Set<UUID> getBoardIds() {
        return boardIds;
    }

    public void setBoardIds(Set<UUID> boardIds) {
        this.boardIds = boardIds;
    }
}
