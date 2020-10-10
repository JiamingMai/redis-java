package com.kapok.service.command;

import com.kapok.model.RedisClient;
import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;

public class PttlCommand implements Command<Object> {

    // the receiver of this command
    private RedisServer redisServer;
    private RedisClient redisClient;

    private String key;

    public PttlCommand(RedisServer redisServer, RedisClient redisClient, String key) {
        this.redisServer = redisServer;
        this.redisClient = redisClient;
        this.key = key;
    }

    @Override
    public Object execute() {
        int selectedDbIndex = redisClient.getDatabaseIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        return database.pttl(key);
    }

}
