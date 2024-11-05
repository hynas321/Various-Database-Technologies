package org.example.Entities;

import com.datastax.oss.driver.api.mapper.annotations.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@CqlName("accounts")
public class Account {

    public enum UserType {
        USER, ADMIN
    }

    @PartitionKey
    private UUID userId;

    @CqlName("email")
    private String email;

    private String password;

    @ClusteringColumn
    @CqlName("type")
    private String userType;

    @CqlName("post_ids")
    private Set<UUID> postIds = new HashSet<>();

    @CqlName("board_ids")
    private Set<UUID> boardIds = new HashSet<>();

    public Account() {

    }

    public Account(String email, String password, UserType userType) {
        this.email = email;
        this.password = password;
        this.userType = userType.name();
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType.name();
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
