package com.kapok.service;

import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandService {

    @Autowired
    private RedisServer redisServer;

    public void select(int databaseIndex) {
        redisServer.setSelectedDbIndex(databaseIndex);
    }

    public void set(String key, String value) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.set(key, value);
    }

    public void hset(String key, String...values) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.hset(key, values);
    }

    public void rpush(String key, String...values) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.rpush(key, values);
    }

    public Integer exists(String key) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        return database.exists(key);
    }

    public Object get(String key) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        return database.get(key);
    }

    public void delete(String key) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.delete(key);
    }

    public void pexpire(String key, int milliseconds) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.pexpire(key, milliseconds);
    }

    public void expire(String key, int seconds) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.expire(key, seconds);
    }

    public void pexpireat(String key, long msTimestamp) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.pexpireat(key, msTimestamp);
    }

    public void expireat(String key, long timestamp) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.expireat(key, timestamp);
    }

}
