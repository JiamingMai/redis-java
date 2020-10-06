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

    public Object get(String key) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        return database.get(key);
    }

}
