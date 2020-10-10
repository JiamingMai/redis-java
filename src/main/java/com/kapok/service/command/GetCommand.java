package com.kapok.service.command;

import com.kapok.model.RedisClient;
import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;

public class GetCommand implements Command<Object> {

    // the receiver of this command
    private RedisServer redisServer;
    private RedisClient redisClient;

    private String key;

    public GetCommand(RedisServer redisServer, RedisClient redisClient, String key) {
        this.redisServer = redisServer;
        this.redisClient = redisClient;
        this.key = key;
    }

    @Override
    public Object execute() {
        int selectedDbIndex = redisClient.getDatabaseIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        if (database.isExpire(key)) {
            return null;
        }
        return database.get(key);
    }

}
