package org.example.Redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.lang.reflect.Type;

public class RedisCache {
    private final Jedis jedis;
    private final ObjectMapper objectMapper;

    public RedisCache(String host, int port, String password) {
        this.jedis = new Jedis(host, port);
        this.jedis.auth(password);
        this.objectMapper = new ObjectMapper();
    }

    public <T> void cacheData(String key, T data, int expirationTimeInSeconds) {
        try {
            String jsonData = objectMapper.writeValueAsString(data);
            jedis.setex(key, expirationTimeInSeconds, jsonData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public <T> T getCachedData(String key, Type type) {
        String jsonData = jedis.get(key);
        if (jsonData == null) return null;
        try {
            return objectMapper.readValue(jsonData, objectMapper.constructType(type));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void invalidateCache(String key) {
        jedis.del(key);
    }

    public void close() {
        try {
            jedis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
