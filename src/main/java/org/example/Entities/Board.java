package org.example.Entities;

import com.datastax.oss.driver.api.mapper.annotations.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(defaultKeyspace = "site")
@CqlName("boards")
public class Board {

    @PartitionKey
    private UUID id;

    @CqlName("name")
    private String name;

    @CqlName("post_ids")
    private Set<UUID> postIds = new HashSet<>();

    @CqlName("member_ids")
    private Set<UUID> memberIds = new HashSet<>();

    public Board() {
        this.id = UUID.randomUUID();
    }

    public Board(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public Board(UUID id, String name, Set<UUID> postIds, Set<UUID> memberIds) {
        this.id = id;
        this.name = name;
        this.postIds = postIds;
        this.memberIds = memberIds;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UUID> getPostIds() {
        return postIds;
    }

    public void setPostIds(Set<UUID> postIds) {
        this.postIds = postIds;
    }

    public Set<UUID> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(Set<UUID> memberIds) {
        this.memberIds = memberIds;
    }
}
