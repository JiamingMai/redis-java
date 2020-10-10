package com.kapok.service.command;

import com.kapok.model.RedisClient;
import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;

public class PersistCommand implements Command<String> {

    // the receiver of this command
    private RedisServer redisServer;
    private RedisClient redisClient;

    private String key;

    public PersistCommand(RedisServer redisServer, RedisClient redisClient, String key) {
        this.redisServer = redisServer;
        this.redisClient = redisClient;
        this.key = key;
    }

    @Override
    public String execute() {
        int selectedDbIndex = redisClient.getDatabaseIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.persist(key);
        return "OK";
    }

}
