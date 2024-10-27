package org.example.Redis;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.bson.types.ObjectId;
import org.example.Entities.Admin;
import org.example.Entities.User;
import redis.clients.jedis.Jedis;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class RedisCache {
    private final Jedis jedis;
    private final ObjectMapper objectMapper;

    public RedisCache() {
        Properties properties = loadProperties();
        String host = properties.getProperty("redis.host");
        int port = Integer.parseInt(properties.getProperty("redis.port"));
        String password = properties.getProperty("redis.password");

        this.jedis = new Jedis(host, port);
        this.jedis.auth(password);
        this.objectMapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();

        module.addSerializer(ObjectId.class, new ObjectIdSerializer());
        module.addDeserializer(ObjectId.class, new ObjectIdDeserializer());
        objectMapper.registerModule(module);
        objectMapper.registerSubtypes(
                new NamedType(User.class, "User"),
                new NamedType(Admin.class, "Admin")
        );
    }

    public void cacheData(String key, Object data, int expiration) {
        try {
            String jsonData = objectMapper.writeValueAsString(data);
            jedis.setex(key, expiration, jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteCache(String key) {
        jedis.del(key);
    }

    public <T> T getCachedData(String key, Class<T> type) {
        try {
            String jsonData = jedis.get(key);
            if (jsonData == null) {
                return null;
            }

            return objectMapper.readValue(jsonData, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> List<T> getCachedDataAsList(String key, Class<T> type) {
        try {
            String jsonData = jedis.get(key);
            if (jsonData == null) {
                return null;
            }

            JavaType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, type);
            return objectMapper.readValue(jsonData, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isConnected() {
        return jedis.isConnected();
    }

    public void close() {
        if (jedis != null && jedis.isConnected()) {
            jedis.close();
        }
    }

    private Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("redis.properties")) {
            if (input == null) {
                throw new IOException("Could ot find redis.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
